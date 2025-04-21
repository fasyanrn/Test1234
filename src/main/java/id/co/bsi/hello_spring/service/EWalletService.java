package id.co.bsi.hello_spring.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EWalletService {

    private int syncedBalance = 250000; // saldo simulasi dari e-wallet

    public int syncBalance(String accountnum) {
        return syncedBalance;
    }

    public List<Map<String, Object>> importTransactions(String accountnum) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> t1 = new HashMap<>();
        t1.put("date", "2025-04-17");
        t1.put("type", "expense");
        t1.put("desc", "Tokopedia");
        t1.put("amount", -100000);
        list.add(t1);

        Map<String, Object> t2 = new HashMap<>();
        t2.put("date", "2025-04-18");
        t2.put("type", "income");
        t2.put("desc", "Bonus ShopeePay");
        t2.put("amount", 75000);
        list.add(t2);

        return list;
    }

    public String transferToEWallet(String accountnum, int amount) {
        syncedBalance += amount;
        return "Transfer berhasil sebesar " + amount;
    }
}