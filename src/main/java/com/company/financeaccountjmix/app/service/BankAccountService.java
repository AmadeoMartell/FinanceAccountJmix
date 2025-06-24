package com.company.financeaccountjmix.app.service;

import com.company.financeaccountjmix.entity.BankAccount;
import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class BankAccountService {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuth;

    public List<BankAccount> getMyAccounts() {
        UUID userId = getCurrentUserId();
        return dataManager.load(BankAccount.class)
                .query("select a from BankAccount a where a.client.id = :uid")
                .parameter("uid", userId)
                .list();
    }

    public BankAccount createAccount(String name) {
        UUID userId = getCurrentUserId();
        Client me = dataManager.load(Client.class)
                .id(userId)
                .one();

        BankAccount a = dataManager.create(BankAccount.class);
        a.setName(name);
        a.setAmount(BigInteger.ZERO);
        a.setClient(me);

        return dataManager.save(a);
    }


    public BigInteger getBalance(UUID accountId) {
        BankAccount a = dataManager.load(BankAccount.class)
                .id(accountId)
                .fetchPlan("_minimal")
                .one();
        if (!a.getClient().getId().equals(getCurrentUserId())) {
            throw new IllegalArgumentException("No access to this account");
        }
        return a.getAmount();
    }

    private UUID getCurrentUserId() {
        User u = (User) currentAuth.getAuthentication().getPrincipal();
        return u.getId();
    }
}