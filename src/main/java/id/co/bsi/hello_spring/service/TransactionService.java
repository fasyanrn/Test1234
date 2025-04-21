
package id.co.bsi.hello_spring.service;

import id.co.bsi.hello_spring.model.TransactionModel;
import id.co.bsi.hello_spring.model.User;
import id.co.bsi.hello_spring.repository.TransactionRepository;
import id.co.bsi.hello_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    public void save(TransactionModel txn) {
        transactionRepository.save(txn);
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<TransactionModel> getFilteredTransactions(String token, String accountnum,
                                                           String keyword, int page, int size,
                                                           String sortBy, String direction) {
        Optional<User> userOpt = userRepository.findByToken(token);
        Sort sort = Sort.by(Sort.Direction.fromOptionalString(direction.toUpperCase()).orElse(Sort.Direction.DESC), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (userOpt.isEmpty() || !userOpt.get().getAccountnum().equals(accountnum)) {
            return Page.empty(pageable);
        }

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

    public TransactionModel saveTransaction(String token, TransactionModel transaction) {
        Optional<User> userOpt = userRepository.findByToken(token);
        if (userOpt.isEmpty() || !userOpt.get().getAccountnum().equals(transaction.getAccountnum())) {
            return null;
        }
        return transactionRepository.save(transaction);
    }
}
