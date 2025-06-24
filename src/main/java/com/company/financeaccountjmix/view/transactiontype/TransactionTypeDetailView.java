package com.company.financeaccountjmix.view.transactiontype;

import com.company.financeaccountjmix.entity.TransactionType;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "transaction-types/:id", layout = MainView.class)
@ViewController(id = "TransactionType.detail")
@ViewDescriptor(path = "transaction-type-detail-view.xml")
@EditedEntityContainer("transactionTypeDc")
public class TransactionTypeDetailView extends StandardDetailView<TransactionType> {
}