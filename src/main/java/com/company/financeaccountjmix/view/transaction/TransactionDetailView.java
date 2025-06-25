package com.company.financeaccountjmix.view.transaction;

import com.company.financeaccountjmix.app.service.TransactionService;
import com.company.financeaccountjmix.entity.Transaction;
import com.company.financeaccountjmix.entity.User;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.view.DetailSaveCloseAction;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Route(value = "transactions/:id", layout = MainView.class)
@ViewController("Transaction.detail")
@ViewDescriptor("transaction-detail-view.xml")
@EditedEntityContainer("transactionDc")
public class TransactionDetailView extends StandardDetailView<Transaction> {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private Notifications notifications;

    @Subscribe("saveButton")
    public void onSaveButton(ClickEvent<Button> event) {
        Transaction tx = getEditedEntity();

        if (tx.getTransferAmount() == null || tx.getTransferAmount().signum() <= 0) {
            notifications.create("Amount must be positive").show();
            return;
        }
        if (tx.getTypes() == null || tx.getTypes().isEmpty()) {
            notifications.create("Select a transaction type").show();
            return;
        }

        try {
            transactionService.processTransaction(tx);
        } catch (IllegalArgumentException e) {
            notifications.create(e.getMessage()).show();
            return;
        }

        closeWithSave();
    }

    @Subscribe("cancelButton")
    public void onCancelButton(ClickEvent<Button> event) {
        closeWithDiscard();
    }
}
