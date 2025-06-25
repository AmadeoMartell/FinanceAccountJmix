package com.company.financeaccountjmix.app.service;

import com.company.financeaccountjmix.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class TransactionService {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuth;

    @Transactional
    public Transaction createTransaction(Integer fromId,
                                         Integer toId,
                                         @Positive(message = "Amount must be >0") BigInteger amount,
                                         List<Integer> typeIds) {
        UUID uid = getCurrentUserId();

        BankAccount from = null, to = null;
        if (fromId != null) {
            from = dataManager.load(BankAccount.class).id(fromId).one();
            checkOwnership(from.getClient().getId(), uid);
        }
        if (toId != null) {
            to = dataManager.load(BankAccount.class).id(toId).one();
            checkOwnership(to.getClient().getId(), uid);
        }

        if (from != null && from.getAmount().compareTo(amount) < 0) {
            throw new IllegalArgumentException("No enough money on the account " + from.getName() + " to transfer " + amount);
        }

        Transaction tx = dataManager.create(Transaction.class);
        tx.setFromAccount(from);
        tx.setToAccount(to);
        tx.setTransferAmount(amount);
        tx.setCreateDate(new Date());
        if (typeIds != null && !typeIds.isEmpty()) {
            List<TransactionType> types = dataManager.load(TransactionType.class)
                    .query("select t from TransactionType t where t.id in :ids")
                    .parameter("ids", typeIds)
                    .list();
            tx.setTypes(types);
        }
        dataManager.save(tx);

        if (from != null) {
            from.setAmount(from.getAmount().subtract(amount));
            dataManager.save(from);
        }
        if (to != null) {
            to.setAmount(to.getAmount().add(amount));
            dataManager.save(to);
        }

        return tx;
    }

    public List<Transaction> getMyTransactions() {
        UUID uid = getCurrentUserId();
        return dataManager.load(Transaction.class)
                .query("""
                select distinct t
                  from Transaction t
             left join t.fromAccount fa
             left join t.toAccount   ta
                 where fa.client.id = :uid
                    or ta.client.id = :uid
              order by t.createDate desc
            """)
                .parameter("uid", uid)
                .list();
    }

    @Transactional
    public void processTransaction(Transaction tx) {
        UUID uid = getCurrentUserId();
        BigInteger amount = tx.getTransferAmount();

        if (tx.getFromAccount() != null) {
            checkOwnership(tx.getFromAccount().getClient().getId(), uid);
        }
        if (tx.getToAccount() != null) {
            checkOwnership(tx.getToAccount().getClient().getId(), uid);
        }

        if (tx.getFromAccount() != null &&
                tx.getFromAccount().getAmount().compareTo(amount) < 0) {
            throw new IllegalArgumentException("No enough money on the account " + tx.getFromAccount().getName() + " to transfer " + amount);
        }

        if (tx.getFromAccount() != null) {
            tx.getFromAccount().setAmount(
                    tx.getFromAccount().getAmount().subtract(amount)
            );
            dataManager.save(tx.getFromAccount());
        }
        if (tx.getToAccount() != null) {
            tx.getToAccount().setAmount(
                    tx.getToAccount().getAmount().add(amount)
            );
            dataManager.save(tx.getToAccount());
        }

        if (tx.getCreateDate() == null) {
            tx.setCreateDate(new Date());
        }
    }

    private UUID getCurrentUserId() {
        User u = (User) currentAuth.getAuthentication().getPrincipal();
        return u.getId();
    }

    private void checkOwnership(UUID ownerId, UUID currentId) {
        if (!ownerId.equals(currentId)) {
            throw new AccessDeniedException("Cannot access account of another user");
        }
    }
}