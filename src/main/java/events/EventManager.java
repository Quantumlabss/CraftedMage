package events;


import items.magic.ItemManager;

import items.magic.fire.FireWand;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.util.Vector;

import java.awt.*;

public class EventManager implements Listener {








    //fireball test
    @EventHandler
    public void ballFiring(PlayerInteractEvent e) {

        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_AIR) {
            Player p = e.getPlayer();
            Location location = p.getEyeLocation();
            Vector direction = location.getDirection();
            if (e.getItem() != null) {

                if (e.getItem().getItemMeta().equals(FireWand.fireWand.getItemMeta())) {
                    e.setCancelled(true);
                    Fireball s = e.getPlayer().launchProjectile(Fireball.class);
                    s.setIsIncendiary(false);
                    s.setYield(0);
                    s.setVisibleByDefault(false);
                    for (int i = 0; i < 1000; i++) {
                        Location loc = s.getLocation();
                        loc.getWorld().spawnParticle(Particle.FLAME, loc.getX(), loc.getY(), loc.getZ() ,0 ,0 ,0 ,0);


                        //p.spawnParticle(Particle.FLAME, p.getLocation(), 0, 2, 0, 2);

                    }
                }
            }
        }
    }

    //@EventHandler
   // public void Lightning(PlayerInteractEvent l) {
     //   if (l.getAction() == Action.LEFT_CLICK_AIR || l.getAction() == Action.LEFT_CLICK_AIR && l.getAction() != Action.RIGHT_CLICK_AIR && lightningMode == true) {
      //      Player p = l.getPlayer();
       //     if (l.getItem() != null) {
           //     if (l.getItem().getItemMeta().equals(ItemManager.devWand.getItemMeta())) {
           //         l.setCancelled(true);
             //       p.getWorld().strikeLightning(p.getTargetBlock(null, 50).getLocation());


                //}
           // }
       // }
   // }


}
