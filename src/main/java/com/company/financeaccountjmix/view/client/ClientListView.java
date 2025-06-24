package com.company.financeaccountjmix.view.client;

import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "clients", layout = MainView.class)
@ViewController(id = "finance_Client.list")
@ViewDescriptor(path = "client-list-view.xml")
@LookupComponent("clientsDataGrid")
@DialogMode(width = "64em")
public class ClientListView extends StandardListView<Client> {
}