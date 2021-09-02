package de.ruben.xcore.thread;

import de.ruben.xcore.XCore;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.labymod.display.EconomyDisplay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardUpdateThread extends Thread{

    int interval = 20;

    @Override
    public void run() {
        while (true){
            try {
                sleep(interval*50);
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(XDevApi.getInstance().getLabyUsers().isLabyUser(player.getUniqueId())){
                        XDevApi.getInstance().getLabyModDisplay().getEconomyDisplay().updateBalanceDisplay(player, EconomyDisplay.EnumBalanceType.CASH, XCore.getInstance().getCurrency().getCashService().getValue(player.getUniqueId()));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
