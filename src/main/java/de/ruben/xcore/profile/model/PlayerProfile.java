package de.ruben.xcore.profile.model;

public class PlayerProfile {
    private long firstJoin, lastJoin, messages, commands;
    private Transaction transaction;
    private int playerKills, monsterKills, died, skyDrops;

    public PlayerProfile(long firstJoin, long lastJoin, long messages, long commands, Transaction transaction, int playerKills, int monsterKills, int died, int skyDrops) {
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.messages = messages;
        this.commands = commands;
        this.transaction = transaction;
        this.playerKills = playerKills;
        this.monsterKills = monsterKills;
        this.died = died;
        this.skyDrops = skyDrops;
    }

    public PlayerProfile() {
    }

    public long getFirstJoin() {
        return firstJoin;
    }

    public void setFirstJoin(long firstJoin) {
        this.firstJoin = firstJoin;
    }

    public long getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(long lastJoin) {
        this.lastJoin = lastJoin;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public long getCommands() {
        return commands;
    }

    public void setCommands(long commands) {
        this.commands = commands;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public void setPlayerKills(int playerKills) {
        this.playerKills = playerKills;
    }

    public int getMonsterKills() {
        return monsterKills;
    }

    public void setMonsterKills(int monsterKills) {
        this.monsterKills = monsterKills;
    }

    public int getDied() {
        return died;
    }

    public void setDied(int died) {
        this.died = died;
    }

    public int getSkyDrops() {
        return skyDrops;
    }

    public void setSkyDrops(int skyDrops) {
        this.skyDrops = skyDrops;
    }
}
