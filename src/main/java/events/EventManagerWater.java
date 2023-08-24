package events;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import org.crafted.craftedmage.craftedmage.CraftedMage;
import items.magic.water.WaterWand;

public class EventManagerWater implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
       Action action = event.getAction();
    if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
        if (player.getInventory().getItemInMainHand().isSimilar(WaterWand.waterWand)) {
            event.setCancelled(true);
            activateWaterBubbleShield(player);
        }
    }
}
public void activateWaterBubbleShield(Player player) {
    CraftedMage plugin = CraftedMage.getInstance(); 
    player.setCollidable(false);
    player.playSound(player.getLocation(), Sound.ITEM_BUCKET_FILL, 1.0f, 1.0f);

    double shieldRadius = 4.0; // shield radius 
    double shieldYOffset = 1.0;

    new BukkitRunnable() {
        private int ticks = 0;

        @Override
        public void run() {
            if (ticks >= 100) { // set duration of the shield
                player.setCollidable(true);
                cancel();
                return;
            }
            Location playerLocation = player.getLocation();
            for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 16) {
                for (double phi = 0; phi <= Math.PI; phi += Math.PI / 8) {
                    double x = shieldRadius * Math.sin(phi) * Math.cos(theta);
                    double y = shieldYOffset + shieldRadius * Math.cos(phi);
                    double z = shieldRadius * Math.sin(phi) * Math.sin(theta);
                    Location particleLocation = playerLocation.clone().add(x, y, z);
                    playerLocation.getWorld().spawnParticle(Particle.WATER_BUBBLE, particleLocation, 1, 0, 0, 0, 0);
                }
            }
            for (Entity entity : player.getNearbyEntities(shieldRadius, shieldRadius, shieldRadius)) {
                if (entity instanceof Player || entity.equals(player)) {
                    continue;
                }
                Location entityLocation = entity.getLocation();
                if (isInsideWaterBubbleShield(entityLocation)) {
                    moveEntityAwayFromShield(entity, playerLocation);
                }
            }
            ticks++;
        }
    }.runTaskTimer(plugin, 0L, 1L);
}
private boolean isInsideWaterBubbleShield(Location location) {
    double bubbleRadius = 4.0; // set radius of the bubble
    double bubbleHeight = 2.0; // set height of the bubble
    double bubbleYOffset = 1.0;
    Location center = location.clone().add(0, bubbleYOffset, 0);
    return center.distance(location) <= bubbleRadius && location.getY() <= center.getY() + bubbleHeight;
}
private void moveEntityAwayFromShield(Entity entity, Location playerLocation) {
    double pushStrength = 0.5; // set push strength out of the bubble
    Vector entityToPlayer = playerLocation.toVector().subtract(entity.getLocation().toVector());
    Vector awayFromPlayer = entityToPlayer.clone().normalize().multiply(-1).multiply(pushStrength);
    entity.setVelocity(awayFromPlayer);
}
}
