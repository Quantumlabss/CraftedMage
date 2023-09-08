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
            CraftedMage plugin = CraftedMage.getInstance();
            HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
            Player player = ((Player) sender).getPlayer();

            PermissionAttachment attachment = player.addAttachment(plugin);
            perms.put(player.getUniqueId(), attachment);
            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission("element.water", true);







        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }


}
