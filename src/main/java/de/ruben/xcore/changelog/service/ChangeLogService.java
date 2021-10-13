package de.ruben.xcore.changelog.service;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.ruben.xcore.changelog.XChangelog;
import de.ruben.xcore.changelog.model.ChangeLogType;
import de.ruben.xcore.changelog.model.Changelog;
import de.ruben.xdevapi.storage.MongoDBStorage;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChangeLogService {


    public Changelog saveChangeLog(UUID author, String title, String content, ChangeLogType changeLogType){
        Changelog changelog = new Changelog(author, title, content, new Date(System.currentTimeMillis()), changeLogType);
        getCollection().insertOne(changelog.toDocument());
        return changelog;
    }

    public Changelog saveChangeLog(Changelog changelog){
        getCollection().insertOne(changelog.toDocument());
        return changelog;
    }

    public Changelog getChangeLog(UUID id){
        return new Changelog().fromDocument(getCollection().find(Filters.eq("_id", id)).first());
    }

    public List<Changelog> getChangeLogs(){
        List<Changelog> changelogs = new ArrayList<>();
        getCollection().find().forEach((Block<? super Document>) document -> changelogs.add(new Changelog().fromDocument(document)));
        return changelogs;
    }

    public List<Changelog> getChangeLogsNewestFirst(){
        return getChangeLogs().stream().sorted((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate())).collect(Collectors.toList());
    }

    public List<Changelog> getChangeLogs(UUID author){
        List<Changelog> changelogs = new ArrayList<>();
        getCollection().find().filter(Filters.eq("author", author)).forEach((Block<? super Document>) document -> changelogs.add(new Changelog().fromDocument(document)));
        return changelogs;
    }

    public long deleteChangeLog(UUID id){
        return getCollection().deleteOne(Filters.eq("_id", id)).getDeletedCount();
    }

    private MongoDBStorage getMongoDBStorage(){
        return XChangelog.getInstance().getMongoDBStorage();
    }

    private MongoCollection<Document> getCollection(){
        return getMongoDBStorage().getMongoDatabase().getCollection("Data_Changelogs");
    }
}
