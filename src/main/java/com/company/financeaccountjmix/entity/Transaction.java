package com.company.financeaccountjmix.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@JmixEntity
@Table(name = "TRANSACTION", indexes = {
        @Index(name = "IDX_TRANSACTION__FROM_ACC", columnList = "FROM_ACC_ID"),
        @Index(name = "IDX_TRANSACTION__TO_ACC", columnList = "TO_ACC_ID")
})
@Entity(name = "Transaction")
public class Transaction {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @JoinColumn(name = "FROM_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount fromAccount;

    @JoinColumn(name = "TO_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount toAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    @NotNull
    private Date createDate;

    @Column(name = "TRANSFER_AMOUNT", precision = 19, nullable = false)
    @Positive(message = "Amount should be positive!")
    private BigInteger transferAmount;

    @JoinTable(name = "TRANSACTION_TO_TYPE",
            joinColumns = @JoinColumn(name = "TRANSACTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "TYPE_ID"))
    @ManyToMany
    private List<TransactionType> types;

    public List<TransactionType> getTypes() {
        return types;
    }

    public void setTypes(List<TransactionType> types) {
        this.types = types;
    }

    public BigInteger getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigInteger transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BankAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
    }

    public BankAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @AssertTrue(message = "Invalid transaction direction")
    public boolean isValidDirection() {
        return fromAccount != null || toAccount != null;
    }

}