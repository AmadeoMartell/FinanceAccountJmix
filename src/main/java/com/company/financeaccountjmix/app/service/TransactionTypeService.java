package com.company.financeaccountjmix.app.service;

import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.TransactionType;
import com.company.financeaccountjmix.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
public class TransactionTypeService {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuth;

    public List<TransactionType> getMyTypes() {
        UUID uid = getCurrentUserId();
        return dataManager.load(TransactionType.class)
                .query("select t from TransactionType t where t.client.id = :uid")
                .parameter("uid", uid)
                .list();
    }

    public TransactionType createType(String name) {
        Client me = dataManager.load(Client.class)
                .id(getCurrentUserId())
                .one();
        TransactionType t = dataManager.create(TransactionType.class);
        t.setName(name);
        t.setClient(me);
        return dataManager.save(t);
    }

    private UUID getCurrentUserId() {
        User u = (User) currentAuth.getAuthentication().getPrincipal();
        return u.getId();
    }
}