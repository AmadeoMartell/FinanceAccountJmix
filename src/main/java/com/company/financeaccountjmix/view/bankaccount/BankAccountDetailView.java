package com.company.financeaccountjmix.view.bankaccount;

import com.company.financeaccountjmix.entity.BankAccount;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "bank-accounts/:id", layout = MainView.class)
@ViewController(id = "BankAccount.detail")
@ViewDescriptor(path = "bank-account-detail-view.xml")
@EditedEntityContainer("bankAccountDc")
public class BankAccountDetailView extends StandardDetailView<BankAccount> {
}