package events;

import items.magic.MasterWand;
import items.magic.water.WaterWand;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.crafted.craftedmage.craftedmage.CraftedMage;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class EventManagerWater implements Listener {
    private final Map<Player, Long> cooldownMap = new HashMap<>();
    private final int cooldownDurationTicks = 100;
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK && player.hasPermission("element.water")) {
            if (player.getInventory().getItemInMainHand().isSimilar(MasterWand.masterWand)) {
                if (canCastSpell(player)) {
                    int manaCost = 20;
                    if (CraftedMage.getInstance().getManaManager().consumeMana(player, manaCost)) {
                        event.setCancelled(true);
                        activateWaterBubbleShield(player);
                        startCooldown(player);
                        startManaRecharge(player);
                    } else {
                        player.sendMessage("Not enough mana to cast this spell.");
                    }
                } else {
                    long cooldownEnd = cooldownMap.get(player);
                    int secondsLeft = (int) Math.ceil((cooldownEnd - System.currentTimeMillis()) / 1000.0);
                    player.sendMessage("Spell is on cooldown. You can use it again in " + secondsLeft + " seconds.");
                }
            }
        }
    }
    private boolean canCastSpell(Player player) {
        return !cooldownMap.containsKey(player) || cooldownMap.get(player) <= System.currentTimeMillis();
    }
    private void startCooldown(Player player) {
        cooldownMap.put(player, System.currentTimeMillis() + cooldownDurationTicks * 50);
    }
    private void activateWaterBubbleShield(Player player) {
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
    private void startManaRecharge(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                CraftedMage.getInstance().getManaManager().setInitialMana(player, 100);
            }
        }.runTaskLater(CraftedMage.getInstance(), 300);
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
