package de.ruben.xcore.currency.listener;

import de.ruben.xcore.currency.Currency;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Currency.getInstance().getCashService().getAccount(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Currency.getInstance().getCashService().removeCacheEntry(event.getPlayer().getUniqueId());
    }
}
