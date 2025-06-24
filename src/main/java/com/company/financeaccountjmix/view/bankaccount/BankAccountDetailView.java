package com.company.financeaccountjmix.view.bankaccount;

import com.company.financeaccountjmix.entity.BankAccount;
import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.User;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

@Route(value = "bank-accounts/:id", layout = MainView.class)
@ViewController(id = "BankAccount.detail")
@ViewDescriptor(path = "bank-account-detail-view.xml")
@EditedEntityContainer("bankAccountDc")
public class BankAccountDetailView extends StandardDetailView<BankAccount> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @ViewComponent("clientField")
    private EntityPicker<Client> clientField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<BankAccount> event) {
        User user = (User) currentAuthentication.getAuthentication().getPrincipal();

        event.getEntity().setClient((Client) user);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        clientField.setEnabled(false);

        BankAccount acct = getEditedEntity();
        if (acct.getId() != null) {
            UUID userId = ((User) currentAuthentication
                    .getAuthentication().getPrincipal()).getId();
            if (!acct.getClient().getId().equals(userId)) {
                throw new AccessDeniedException("No access to this account");
            }
        }
    }
}