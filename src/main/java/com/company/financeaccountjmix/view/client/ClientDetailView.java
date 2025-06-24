package com.company.financeaccountjmix.view.client;

import com.company.financeaccountjmix.entity.Client;

import com.company.financeaccountjmix.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "clients/:id", layout = MainView.class)
@ViewController(id = "finance_Client.detail")
@ViewDescriptor(path = "client-detail-view.xml")
@EditedEntityContainer("clientDc")
public class ClientDetailView extends StandardDetailView<Client> {
}