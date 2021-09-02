package de.ruben.xcore;

import de.ruben.xcore.currency.Currency;
import de.ruben.xcore.thread.ScoreboardUpdateThread;
import org.bukkit.plugin.java.JavaPlugin;

public final class XCore extends JavaPlugin {

    private static XCore instance;

    private Currency currency;

    private Thread scoreboardThread;

    @Override
    public void onEnable() {
        this.instance = this;
        this.currency = new Currency();

        currency.onEnable();

        this.scoreboardThread = new ScoreboardUpdateThread();
        scoreboardThread.start();

    }

    @Override
    public void onDisable() {
        currency.onDisable();
        scoreboardThread.interrupt();
    }

    public static XCore getInstance() {
        return instance;
    }

    public Currency getCurrency() {
        return currency;
    }
}
