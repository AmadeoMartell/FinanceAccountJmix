package com.company.financeaccountjmix.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;

@JmixEntity
@Entity(name = "finance_Client")
@Table(name = "CLIENT")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "ID")
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private List<BankAccount> bankAccounts;

    @OneToMany(mappedBy = "client")
    private List<TransactionType> transactionTypes;

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }
    public void setTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }
}