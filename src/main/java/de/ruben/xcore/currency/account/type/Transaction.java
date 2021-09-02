package de.ruben.xcore.currency.account.type;

import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.util.StringUtil;
import org.bukkit.Bukkit;

import java.util.UUID;

public class Transaction {
    private boolean positive;
    private double amount;
    private UUID transactionMadeBy;

    public Transaction(boolean positive, double amount, UUID transactionMadeBy) {
        this.positive = positive;
        this.amount = amount;
        this.transactionMadeBy = transactionMadeBy;
    }

    public Transaction() {
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getTransactionMadeBy() {
        return transactionMadeBy;
    }

    public void setTransactionMadeBy(UUID transactionMadeBy) {
        this.transactionMadeBy = transactionMadeBy;
    }

    public String getTransactionString(UUID displayed){
        String transactionMadeBy = displayed == getTransactionMadeBy() ? "Du" : Bukkit.getOfflinePlayer(displayed).getName();
        String amountString = positive ? "&2+"+XDevApi.getInstance().getxUtil().getStringUtil().moneyFormat(amount) : "&c-"+XDevApi.getInstance().getxUtil().getStringUtil().moneyFormat(amount);

        return amountString+" &8("+transactionMadeBy+")";
    }
}
