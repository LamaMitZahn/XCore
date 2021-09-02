package de.ruben.xcore.currency.command;

import de.ruben.xcore.currency.Currency;
import de.ruben.xdevapi.XDevApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerCashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String commandLabel = command.getLabel();

        Player player = (Player) sender;

        if(commandLabel.equalsIgnoreCase("cash")){
            player.sendMessage("§7Zurzeit befinden sich §b"+ Currency.getInstance().getCashService().getValue(player.getUniqueId()) +"€ §7in deiner Brieftasche!");
        }else if(commandLabel.equals("change")){
            Currency.getInstance().getCashService().setValue(player.getUniqueId(), ThreadLocalRandom.current().nextDouble(10000000));
        }

        return false;
    }
}
