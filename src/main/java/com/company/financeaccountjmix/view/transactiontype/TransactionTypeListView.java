package com.company.financeaccountjmix.view.transactiontype;

import com.company.financeaccountjmix.app.service.TransactionTypeService;
import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.TransactionType;
import com.company.financeaccountjmix.entity.User;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;

import java.util.List;


@Route(value = "transaction-types", layout = MainView.class)
@ViewController("TransactionType.list")
@ViewDescriptor(path = "transaction-type-list-view.xml")
@LookupComponent("transactionTypesDataGrid")
@DialogMode(width = "64em")
public class TransactionTypeListView extends StandardListView<TransactionType> {

    @ViewComponent("transactionTypesDc")
    private CollectionContainer<TransactionType> transactionTypesDc;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        List<TransactionType> list = transactionTypeService.getMyTypes();
        transactionTypesDc.setItems(list);
    }
}