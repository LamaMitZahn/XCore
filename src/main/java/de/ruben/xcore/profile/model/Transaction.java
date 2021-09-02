package de.ruben.xcore.profile.model;

public class Transaction {
    private int transactionNumber;
    private double moneyTransacted;

    public Transaction(int transactionNumber, double moneyTransacted) {
        this.transactionNumber = transactionNumber;
        this.moneyTransacted = moneyTransacted;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public double getMoneyTransacted() {
        return moneyTransacted;
    }

    public void setMoneyTransacted(double moneyTransacted) {
        this.moneyTransacted = moneyTransacted;
    }
}
