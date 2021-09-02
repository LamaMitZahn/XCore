package de.ruben.xcore.currency.service;

import com.mongodb.client.model.Filters;
import de.ruben.xcore.currency.Currency;
import de.ruben.xcore.currency.account.CashAccount;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.storage.MongoDBStorage;
import org.bson.Document;
import org.ehcache.Cache;

import java.util.UUID;
import java.util.function.Consumer;

public class CashService{

    public CashAccount resetValue(UUID uuid){
        CashAccount cashAccount = getAccount(uuid);
        cashAccount.setValue(0.0);

        return updateCashAccount(uuid, cashAccount);
    }

    public CashAccount setValue(UUID uuid, double amount){
        CashAccount cashAccount = getAccount(uuid);
        cashAccount.setValue(amount);

        return updateCashAccount(uuid, cashAccount);
    }

    public CashAccount removeValue(UUID uuid, double amount){
        CashAccount cashAccount = getAccount(uuid);
        cashAccount.setValue(cashAccount.getValue()-amount);

        return updateCashAccount(uuid, cashAccount);
    }

    public CashAccount addValue(UUID uuid, double amount){
        CashAccount cashAccount = getAccount(uuid);
        cashAccount.setValue(cashAccount.getValue()+amount);

        return updateCashAccount(uuid, cashAccount);
    }

    public CashAccount updateCashAccount(UUID uuid, CashAccount cashAccount){
        if(!getCache().containsKey(uuid)) getAccount(uuid);

        getCache().replace(uuid, cashAccount);

        XDevApi.getInstance().getxScheduler().async(() -> {
            Document document = new Document();
            document.append("value", cashAccount.getValue());
            System.out.println("Found Document: "+getMongoDBStorage().getMongoDatabase().getCollection("Data_Cash").find(Filters.eq("_id", uuid)).first().toJson());

            getMongoDBStorage().getMongoDatabase().getCollection("Data_Cash").findAnd
            System.out.println("Updated Document: "+getMongoDBStorage().getMongoDatabase().getCollection("Data_Cash").findOneAndUpdate(Filters.eq("_id", uuid), document).toJson());
        });

        return cashAccount;
    }

    public Double getValue(UUID uuid) {
        return getAccount(uuid).getValue();
    }

    public CashAccount getAccount(UUID uuid) {
        if(getCache().containsKey(uuid)){
            return getCache().get(uuid);
        }else{
            Document document = getMongoDBStorage().getMongoDatabase().getCollection("Data_Cash").find(new Document("_id", uuid)).first();
            CashAccount cashAccount;

            if(document != null){
                cashAccount = new CashAccount(document.getDouble("value"));
            }else{
                cashAccount = createAccount(uuid);
            }

            getCache().putIfAbsent(uuid, cashAccount);
            return cashAccount;
        }
    }

    public void getValueAsync(UUID uuid, Consumer<Double> callback) {
            getAccountAsync(uuid, cashAccount -> {
                callback.accept(cashAccount.getValue());
            });
    }

    public void getAccountAsync(UUID uuid, Consumer<CashAccount> callback) {
        if(getCache().containsKey(uuid)){
            callback.accept(getCache().get(uuid));
        }else{
            getMongoDBStorage().getDocumentByBson("Data_Cash", new Document("_id", uuid)).thenAccept(document -> {
                CashAccount cashAccount;

                if(document != null){
                    cashAccount = new CashAccount(document.getDouble("value"));
                }else{
                    cashAccount = createAccount(uuid);
                }

                getCache().putIfAbsent(uuid, cashAccount);
                callback.accept(cashAccount);
            });
        }
    }

    public CashAccount createAccount(UUID uuid){
        CashAccount cashAccount = new CashAccount(1000.0);
        Document document = new Document("_id", uuid);
        document.append("value", cashAccount.getValue());

        getMongoDBStorage().insertOneDocument("Data_Cash", document);

        return cashAccount;
    }

    public void removeCacheEntry(UUID uuid){
        if(getCache().containsKey(uuid)){
            getCache().remove(uuid);
        }
    }


    public MongoDBStorage getMongoDBStorage(){
        return Currency.getInstance().getMongoDBStorage();
    }

    public Cache<UUID, CashAccount> getCache(){
        return Currency.getInstance().getCacheManager().getCache("cashCache", UUID.class, CashAccount.class);
    }
}
