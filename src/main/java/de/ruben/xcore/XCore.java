package de.ruben.xcore;

import de.ruben.xcore.currency.XCurrency;
import de.ruben.xcore.customenchantment.XEnchantment;
import de.ruben.xcore.itemstorage.XItemStorage;
import de.ruben.xcore.profile.XProfile;
import de.ruben.xcore.subsystem.SubSystem;
import de.ruben.xcore.thread.RecentDataUpdateThread;
import de.ruben.xcore.thread.ScoreboardUpdateThread;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class XCore extends JavaPlugin {

    private static XCore instance;

    private Thread scoreboardThread;

    private RecentDataUpdateThread recentDataUpdateThread;

    private List<SubSystem> subSystems;

    @Override
    public void onEnable() {
        this.instance = this;
        this.subSystems = setSubSystems();

        subSystems.forEach(subSystem -> {
            subSystem.onEnable();
        });

        this.scoreboardThread = new ScoreboardUpdateThread();
        scoreboardThread.start();

        this.recentDataUpdateThread = new RecentDataUpdateThread();
        recentDataUpdateThread.start();

    }

    @Override
    public void onDisable() {
        subSystems.forEach(subSystem -> {
            subSystem.onDisable();
        });

        scoreboardThread.interrupt();
        recentDataUpdateThread.interrupt();
    }

    public static XCore getInstance() {
        return instance;
    }

    public RecentDataUpdateThread getRecentDataUpdateThread() {
        return recentDataUpdateThread;
    }

    public Thread getScoreboardThread() {
        return scoreboardThread;
    }

    public List<SubSystem> setSubSystems(){
        List<SubSystem> subSystems = List.of(new XItemStorage(), new XCurrency(), new XProfile(), new XEnchantment());
        return subSystems;
    }
}
