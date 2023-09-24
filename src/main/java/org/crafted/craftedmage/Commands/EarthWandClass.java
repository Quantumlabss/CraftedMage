package org.crafted.craftedmage.Commands;

import org.crafted.craftedmage.magic.earth.EarthWand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EarthWandClass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            EarthWand.earthWand.setAmount(1);
            player.getInventory().addItem(EarthWand.earthWand);
            player.chat(ChatColor.DARK_RED + "Earth Wand Testing Wand Recieved");
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
