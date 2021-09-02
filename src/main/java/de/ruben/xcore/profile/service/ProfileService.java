package de.ruben.xcore.profile.service;

import de.ruben.xcore.profile.Profile;
import de.ruben.xdevapi.storage.MongoDBStorage;

public class ProfileService {

    private MongoDBStorage mongoDBStorage;
    private Profile profile;

    private String collection = "ProfileData";

    public ProfileService(Profile profile) {
        this.profile = profile;
        this.mongoDBStorage = profile.getMongoDBStorage();
    }
    
}
