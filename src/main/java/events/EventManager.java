package events;


import items.magic.ItemManager;
import items.magic.MasterWand;
import items.magic.fire.FireWand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.crafted.craftedmage.craftedmage.CraftedMage;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.util.Vector;
import io.papermc.paper.event.entity.EntityMoveEvent;
import items.magic.MasterWand;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EventManager implements Listener {
private Map<Player, Long> wandCooldowns = new HashMap<>();
@EventHandler
public void ballFiring(PlayerInteractEvent e) {
    if (e.getAction() == Action.LEFT_CLICK_BLOCK || (e.getAction() == Action.LEFT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_AIR)) {
        Player player = e.getPlayer();
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection();
        if (e.getItem() != null && e.getItem().getItemMeta() != null) {
            if (e.getItem().getItemMeta().equals(FireWand.fireWand.getItemMeta())) {
                e.setCancelled(true);

                long currentTime = System.currentTimeMillis();
                if (wandCooldowns.containsKey(player) && currentTime - wandCooldowns.get(player) < 1000) {
                    long timeLeft = (1000 - (currentTime - wandCooldowns.get(player))) / 1000;
                    player.sendMessage("Wand is on cooldown. Please wait " + timeLeft + " seconds.");
                    return;
                }
                if (CraftedMage.getInstance().getManaManager().consumeMana(player, 10)) {
                    wandCooldowns.put(player, currentTime);

                    // Fireball logic here
                    Fireball s = player.launchProjectile(Fireball.class);
                    s.setIsIncendiary(false);
                    s.setYield(0.0F);
                    // Add particles when someone shoots from the wand
                    for (int i = 0; i < 100; i++) {
                        Location particleLoc = s.getLocation().add(direction.multiply(i));
                        particleLoc.getWorld().spawnParticle(Particle.FLAME, particleLoc, 1, 0.5, 0.5, 0.5, 0.5);
                    }
                    // fireball trail particles
                    new BukkitRunnable() {
                        int iterations = 0;
    
                        @Override
                        public void run() {
                            if (iterations >= 100 || s.isDead()) {
                                this.cancel();
                                return;
                            }
                            Location particleLoc = s.getLocation().add(direction.clone().multiply(iterations));
                            particleLoc.getWorld().spawnParticle(Particle.FLAME, particleLoc, 1, 0.2, 0.2, 0.2, 0.2);
                            iterations++;
                        }
                    }.runTaskTimer(CraftedMage.getInstance(), 0, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            CraftedMage.getInstance().getManaManager().setInitialMana(player, 100);
                        }
                    }.runTaskLater(CraftedMage.getInstance(), 300L);
                } else {
                    player.sendMessage("Not enough mana to cast!");
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