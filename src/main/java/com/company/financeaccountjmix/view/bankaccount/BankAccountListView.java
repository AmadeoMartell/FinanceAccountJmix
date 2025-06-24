package com.company.financeaccountjmix.view.bankaccount;

import com.company.financeaccountjmix.entity.BankAccount;
import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.User;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;


@Route(value = "bank-accounts", layout = MainView.class)
@ViewController(id = "BankAccount.list")
@ViewDescriptor(path = "bank-account-list-view.xml")
@LookupComponent("bankAccountsDataGrid")
@DialogMode(width = "64em")
public class BankAccountListView extends StandardListView<BankAccount> {

    @ViewComponent
    private CollectionLoader<BankAccount> bankAccountsDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User user = (User) currentAuthentication.getAuthentication().getPrincipal();
        bankAccountsDl.setParameter("userId", user.getId());
        bankAccountsDl.load();
    }

}