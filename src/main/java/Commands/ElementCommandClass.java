package Commands;

import items.magic.MasterWand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.crafted.craftedmage.craftedmage.CraftedMage;

import java.util.HashMap;
import java.util.UUID;


public class ElementCommandClass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Plugin plugin = CraftedMage.getPlugin(CraftedMage.class);
            Player player = (Player) sender;
            // give player permission to use the water powers
            Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
            Team blueteam = sb.registerNewTeam("BlueNameTeam");
            blueteam.setPrefix(ChatColor.DARK_BLUE.toString());
            blueteam.addPlayer(player);


        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
