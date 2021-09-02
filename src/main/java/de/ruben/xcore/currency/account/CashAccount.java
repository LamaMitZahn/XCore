package de.ruben.xcore.currency.account;

public class CashAccount {
    private Double value;

    public CashAccount(Double value) {
        this.value = value;
    }

    public CashAccount() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
