package org.crafted.craftedmage.events;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.crafted.craftedmage.CraftedMage;
import org.crafted.craftedmage.magic.air.AirWand;
import org.crafted.craftedmage.magic.earth.EarthWand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventManagerEarth implements Listener {

    private final Map<Player, Long> cooldownMap = new HashMap<>();
    private final int cooldownDurationTicks = 100;
    private final Material[] blockTypes = {
            Material.DIRT,
            Material.STONE,
            Material.SAND,
    };
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().isSimilar(EarthWand.earthWand)) {
            if (canCastSpell(player)) {
                int manaCost = 30;
                if (CraftedMage.getInstance().getManaManager().consumeMana(player, manaCost)) {
                    event.setCancelled(true);
                    performBlockAnimation(player);
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
    private void performBlockAnimation(Player player) {
        Random random = new Random();
        int blockCount = 10; // Amount of blocks to throw
        int blockDespawnDelay = 500; // in ticks
    
        for (int i = 0; i < blockCount; i++) {
            Material blockType = getRandomBlockType();
            Vector blockDirection = getRandomDirection();
            FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0, 1.0, 0.0), blockType.createBlockData());
            fallingBlock.setVelocity(blockDirection);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!fallingBlock.isValid() || fallingBlock.isOnGround()) {
                        return;
                    }
                    player.getWorld().spawnParticle(Particle.CRIT, fallingBlock.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
                }
            }.runTaskTimer(CraftedMage.getInstance(), 0, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (fallingBlock.isOnGround()) {
                        this.cancel();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                fallingBlock.remove();
                            }
                        }.runTaskLater(CraftedMage.getInstance(), blockDespawnDelay);
                        return;
                    }
                    for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
                        if (targetPlayer.equals(player)) {
                            continue; 
                        }
                        if (fallingBlock.getLocation().distance(targetPlayer.getLocation()) < 1.0) {
                            double damage = 5.0; // amount damage after the block hit entity
                            targetPlayer.damage(damage);
                        }
                    }
                }
            }.runTaskTimer(CraftedMage.getInstance(), 0, 1);
        }
    }

    private Material getRandomBlockType() {
        Random random = new Random();
        int randomIndex = random.nextInt(blockTypes.length);
        return blockTypes[randomIndex];
    }

    private Vector getRandomDirection() {
        Random random = new Random();
        double x = random.nextDouble() * 2 - 1;
        double z = random.nextDouble() * 2 - 1;
        return new Vector(x, 1.5, z).normalize();
    }
    private void startManaRecharge(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                CraftedMage.getInstance().getManaManager().setInitialMana(player, 100);
            }
        }.runTaskLater(CraftedMage.getInstance(), 300);
    }
}
