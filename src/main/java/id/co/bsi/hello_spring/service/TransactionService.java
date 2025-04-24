package id.co.bsi.hello_spring.service;

import id.co.bsi.hello_spring.model.TransactionModel;
import id.co.bsi.hello_spring.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Page<TransactionModel> getFilteredTransactions(String accountnum, String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromOptionalString(direction.toUpperCase()).orElse(Sort.Direction.DESC), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<TransactionModel> allData = transactionRepository.findAllByAccountnum(accountnum);
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            allData = allData.stream()
                    .filter(t ->
                            (t.getDescription() != null && t.getDescription().toLowerCase().contains(kw)) ||
                                    (t.getFromTo() != null && t.getFromTo().toLowerCase().contains(kw)) ||
                                    (t.getType() != null && t.getType().toLowerCase().contains(kw)) ||
                                    String.valueOf(t.getAmount()).contains(kw) ||
                                    (t.getDateTime() != null && t.getDateTime().toLowerCase().contains(kw))
                    )
                    .collect(Collectors.toList());
        }

        int start = Math.min((int) pageable.getOffset(), allData.size());
        int end = Math.min((start + pageable.getPageSize()), allData.size());
        List<TransactionModel> pagedData = allData.subList(start, end);

        return new PageImpl<>(pagedData, pageable, allData.size());
    }

    public TransactionModel saveTransaction(TransactionModel transaction) {
        return transactionRepository.save(transaction);
    }

    public Map<String, Integer> getTransactionSummary(String accountnum) {
        List<TransactionModel> transactions = transactionRepository.findAllByAccountnum(accountnum);

        int totalIncome = transactions.stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToInt(TransactionModel::getAmount)
                .sum();

        int totalExpense = transactions.stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToInt(TransactionModel::getAmount)
                .sum();

        return Map.of(
                "totalIncome", totalIncome,
                "totalExpense", totalExpense
        );
    }

    public Map<String, Integer> getTransactionSummaryByMonth(String accountnum, int monthsAgo) {
        List<TransactionModel> transactions = transactionRepository.findAllByAccountnum(accountnum);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate;
        LocalDateTime endDate;

        if (monthsAgo == 0) { // THIS MONTH
            startDate = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDate = now; // sampai hari ini
        } else if (monthsAgo == 1) { // LAST MONTH
            LocalDate lastMonthStart = now.minusMonths(1).toLocalDate().withDayOfMonth(1);
            LocalDate lastMonthEnd = lastMonthStart.withDayOfMonth(lastMonthStart.lengthOfMonth());
            startDate = lastMonthStart.atStartOfDay();
            endDate = lastMonthEnd.atTime(23, 59, 59, 999999999);
        } else if (monthsAgo == 3) { // THREE MONTH AGO
            LocalDate threeMonthStart = now.minusMonths(3).toLocalDate().withDayOfMonth(1);
            LocalDate lastMonthEnd = now.minusMonths(1).toLocalDate().withDayOfMonth(now.minusMonths(1).toLocalDate().lengthOfMonth());
            startDate = threeMonthStart.atStartOfDay();
            endDate = lastMonthEnd.atTime(23, 59, 59, 999999999);
        } else {
            // Default fallback, should not happen
            startDate = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            endDate = now;
        }

        List<TransactionModel> filtered = transactions.stream()
                .filter(t -> {
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(t.getDateTime());
                        return (dateTime.isEqual(startDate) || dateTime.isAfter(startDate)) &&
                                (dateTime.isBefore(endDate) || dateTime.isEqual(endDate));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());

        int totalIncome = filtered.stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToInt(TransactionModel::getAmount)
                .sum();

        int totalExpense = filtered.stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToInt(TransactionModel::getAmount)
                .sum();

        return Map.of(
                "totalIncome", totalIncome,
                "totalExpense", totalExpense
        );
    }

}

