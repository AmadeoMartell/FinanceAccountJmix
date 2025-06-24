package com.company.financeaccountjmix.view.transaction;

import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.Transaction;
import com.company.financeaccountjmix.entity.User;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;


@Route(value = "transactions", layout = MainView.class)
@ViewController(id = "Transaction.list")
@ViewDescriptor(path = "transaction-list-view.xml")
@LookupComponent("transactionsDataGrid")
@DialogMode(width = "64em")
public class TransactionListView extends StandardListView<Transaction> {

    @ViewComponent
    private CollectionLoader<Transaction> transactionsDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User user = (User) currentAuthentication.getAuthentication().getPrincipal();
        transactionsDl.setParameter("userId", user.getId());
        transactionsDl.load();
    }
}