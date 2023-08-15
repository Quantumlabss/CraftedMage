package events;


import items.magic.ItemManager;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;

public class EventManager implements Listener {
    public static boolean fireMode = true;
    public static boolean lightningMode = false;




    @EventHandler
public void changeMode(PlayerInteractEvent c) {
    if (c.getAction() == Action.RIGHT_CLICK_AIR) {
        Player p = c.getPlayer();
        if (fireMode == true) {
            fireMode = false;
            lightningMode = true;
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.BLUE + "Lighting Mode Activated"));
        }else
            if (lightningMode == true){
                fireMode = true;
                lightningMode = false;
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Fire Mode Activated"));

        }
        }




    }

    //fireball test
    @EventHandler
    public void ballFiring(PlayerInteractEvent e) {

        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_AIR && fireMode == true ) {
            Player p = e.getPlayer();
            if (e.getItem() != null) {
                if (e.getItem().getItemMeta().equals(ItemManager.devWand.getItemMeta())) {
                    e.setCancelled(true);
                    Fireball s = e.getPlayer().launchProjectile(Fireball.class);


                }
            }
        }
    }

    @EventHandler
    public void Lightning(PlayerInteractEvent l) {
        if (l.getAction() == Action.LEFT_CLICK_AIR || l.getAction() == Action.LEFT_CLICK_AIR && l.getAction() != Action.RIGHT_CLICK_AIR && lightningMode == true) {
            Player p = l.getPlayer();
            if (l.getItem() != null) {
                if (l.getItem().getItemMeta().equals(ItemManager.devWand.getItemMeta())) {
                    l.setCancelled(true);
                    p.getWorld().strikeLightning(p.getTargetBlock(null, 50).getLocation());


                }
            }
        }
    }

}
