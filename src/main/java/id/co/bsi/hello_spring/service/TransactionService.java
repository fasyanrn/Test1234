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

        LocalDate now = LocalDate.now().minusMonths(monthsAgo);
        int year = now.getYear();
        int month = now.getMonthValue();

        List<TransactionModel> filtered = transactions.stream()
                .filter(t -> {
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(t.getDateTime()); // AUTO ISO PARSE
                        return dateTime.getYear() == year && dateTime.getMonthValue() == month;
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

