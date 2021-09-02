package de.ruben.xcore.currency;

import de.ruben.xcore.XCore;
import de.ruben.xcore.currency.account.BankAccount;
import de.ruben.xcore.currency.account.CashAccount;
import de.ruben.xcore.currency.command.PlayerCashCommand;
import de.ruben.xcore.currency.listener.PlayerListener;
import de.ruben.xcore.currency.service.CashService;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.storage.MongoDBStorage;
import org.bukkit.Bukkit;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.UUID;

public class Currency {

    private static Currency instance;

    private MongoDBStorage mongoDBStorage;

    private CacheManager cacheManager;

    private CashService cashService;

    public void onEnable(){
        this.instance = this;
        this.mongoDBStorage = new MongoDBStorage(XDevApi.getInstance(), "localhost", "Currency", 27017);
        mongoDBStorage.connect();
        this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        cacheManager.createCache("bankCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(UUID.class, BankAccount.class, ResourcePoolsBuilder.heap(50)));
        cacheManager.createCache("cashCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(UUID.class, CashAccount.class, ResourcePoolsBuilder.heap(50)));

        this.cashService = new CashService();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), XCore.getInstance());

        XCore.getInstance().getCommand("cash").setExecutor(new PlayerCashCommand());
        XCore.getInstance().getCommand("change").setExecutor(new PlayerCashCommand());
    }

    public void onDisable(){
        getMongoDBStorage().disconnect();
        getCacheManager().close();
    }

    public static Currency getInstance() {
        return instance;
    }

    public MongoDBStorage getMongoDBStorage() {
        return mongoDBStorage;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public CashService getCashService() {
        return cashService;
    }
}
