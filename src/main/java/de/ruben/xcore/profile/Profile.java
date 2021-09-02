package de.ruben.xcore.profile;

import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.storage.MongoDBStorage;

public class Profile {

    private MongoDBStorage mongoDBStorage;

    private static Profile instance;

    public void onEnable(){
        this.instance = this;
        this.mongoDBStorage = new MongoDBStorage(XDevApi.getInstance(), "localhost", "data", 27017);

    }

    public void onmDisable(){

    }

    public MongoDBStorage getMongoDBStorage() {
        return mongoDBStorage;
    }

    public static Profile getInstance() {
        return instance;
    }
}
