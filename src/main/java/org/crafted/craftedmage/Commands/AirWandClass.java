package org.crafted.craftedmage.Commands;

import org.crafted.craftedmage.magic.air.AirWand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AirWandClass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            AirWand.airWand.setAmount(1);
            player.getInventory().addItem(AirWand.airWand);
            player.chat(ChatColor.WHITE + "Air Wand Testing Wand Recieved");
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
