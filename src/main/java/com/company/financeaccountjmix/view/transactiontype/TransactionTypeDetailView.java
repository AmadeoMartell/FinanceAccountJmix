package com.company.financeaccountjmix.view.transactiontype;

import com.company.financeaccountjmix.app.service.TransactionTypeService;
import com.company.financeaccountjmix.entity.Client;
import com.company.financeaccountjmix.entity.TransactionType;
import com.company.financeaccountjmix.entity.User;
import com.company.financeaccountjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

@Route(value = "transaction-types/:id", layout = MainView.class)
@ViewController(id = "TransactionType.detail")
@ViewDescriptor(path = "transaction-type-detail-view.xml")
@EditedEntityContainer("transactionTypeDc")
public class TransactionTypeDetailView extends StandardDetailView<TransactionType> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @ViewComponent("clientField")
    private EntityPicker<Client> clientField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<TransactionType> event) {
        User user = (User) currentAuthentication.getAuthentication().getPrincipal();

        event.getEntity().setClient((Client) user);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        clientField.setEnabled(false);

        TransactionType tt = getEditedEntity();
        if (tt.getId() != null) {
            UUID currentUserId = ((User) currentAuthentication
                    .getAuthentication().getPrincipal()).getId();
            if (!tt.getClient().getId().equals(currentUserId)) {
                throw new AccessDeniedException("Нет доступа к этому типу транзакции");
            }
        }
    }
}