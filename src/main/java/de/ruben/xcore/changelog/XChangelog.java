package de.ruben.xcore.changelog;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import de.ruben.xcore.XCore;
import de.ruben.xcore.changelog.command.ChangeLogCommand;
import de.ruben.xcore.changelog.command.CreateChangelogCommand;
import de.ruben.xcore.changelog.model.ChangeLogType;
import de.ruben.xcore.changelog.service.ChangeLogService;
import de.ruben.xcore.currency.codec.TransactionCodec;
import de.ruben.xcore.subsystem.SubSystem;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.storage.MongoDBStorage;
import org.bson.codecs.configuration.CodecRegistries;

import java.util.UUID;

public class XChangelog implements SubSystem {

    private static XChangelog instance;

    private MongoDBStorage mongoDBStorage;

    @Override
    public void onEnable() {
        this.instance = this;
//        this.mongoDBStorage = new MongoDBStorage(XDevApi.getInstance(), "localhost", "Currency", 27017, MongoClientOptions.builder().codecRegistry(CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry())).build());
        this.mongoDBStorage = new MongoDBStorage(XDevApi.getInstance(), 10, "localhost", "Currency", 27017, "currency", "wrgO4FTbV6UyLwtMzfsp", MongoClientOptions.builder().codecRegistry(CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry())).build());

        mongoDBStorage.connect();

        XCore.getInstance().getCommand("changelog").setExecutor(new ChangeLogCommand());
        XCore.getInstance().getCommand("createchangelog").setExecutor(new CreateChangelogCommand());
    }

    @Override
    public void onDisable() {
        mongoDBStorage.disconnect();
    }

    public MongoDBStorage getMongoDBStorage() {
        return mongoDBStorage;
    }

    public static XChangelog getInstance() {
        return instance;
    }
}
