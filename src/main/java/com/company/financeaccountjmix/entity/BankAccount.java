    package com.company.financeaccountjmix.entity;

    import io.jmix.core.entity.annotation.JmixGeneratedValue;
    import io.jmix.core.metamodel.annotation.InstanceName;
    import io.jmix.core.metamodel.annotation.JmixEntity;
    import jakarta.persistence.*;

    import java.math.BigInteger;

    @JmixEntity
    @Table(name = "BANK_ACCOUNT")
    @Entity
    public class BankAccount {
        @JmixGeneratedValue
        @Column(name = "ID", nullable = false)
        @Id
        private Integer id;

        @Column(name = "AMOUNT")
        private BigInteger amount;

        @InstanceName
        @Column(name = "NAME")
        private String name;

        @JoinColumn(name = "CLIENT_ID")
        @ManyToOne(fetch = FetchType.LAZY)
        private Client client;

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAmount(BigInteger amount) {
            this.amount = amount;
        }

        public BigInteger getAmount() {
            return amount;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }