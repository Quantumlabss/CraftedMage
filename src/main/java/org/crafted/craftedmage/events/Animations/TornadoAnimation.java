package org.crafted.craftedmage.events.Animations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TornadoAnimation extends BukkitRunnable {
    private Player player;
    private double radius;
    private double height;
    private double strength;
    private int durationTicks;
    private int ticksElapsed;
    private Vector tornadoDirection;
    private Vector currentDirection;

    public TornadoAnimation(Player player, double radius, double height, double strength, int durationTicks, Vector tornadoDirection) {
        this.player = player;
        this.radius = radius;
        this.height = height;
        this.strength = strength;
        this.durationTicks = durationTicks;
        this.ticksElapsed = 0;
        this.tornadoDirection = tornadoDirection.normalize();
        this.currentDirection = tornadoDirection;
    }
    @Override
    public void run() {
        if (ticksElapsed >= durationTicks) {
            cancel();
            return;
        }
    
        double angle = (2 * Math.PI * ticksElapsed) / durationTicks;
        int entityCount = 0;
        for (Entity entity : player.getNearbyEntities(radius, height, radius)) {
            if (entity instanceof Player && entity.equals(player)) {
                continue;
            }
            Location entityLocation = entity.getLocation();
            Vector toEntity = entityLocation.toVector().subtract(player.getLocation().toVector());
            double entityAngle = tornadoDirection.angle(toEntity.clone().setY(0)); 
            double entityDistance = toEntity.length();
    
            if (entityDistance < radius) {
                double yForce = (height / durationTicks) * ticksElapsed;
                double xForce = radius * Math.cos(entityAngle);
                double zForce = radius * Math.sin(entityAngle);
                Vector forceDirection = new Vector(xForce, yForce, zForce).normalize();
                double forceMagnitude = strength * (1.0 - ticksElapsed / (double) durationTicks);
                entity.setVelocity(forceDirection.multiply(forceMagnitude));
            }
            entityCount++;
        }
        int layers = 5; // Amount of layers of the tornado
        double layerHeight = height / layers;
        double layerDistance = radius * 1.3; // Tornado distance from player
    
        for (int layer = 1; layer <= layers; layer++) {
            double layerAngle = angle * (layer / (double) layers);
            double x = layerDistance * Math.cos(layerAngle);
            double z = layerDistance * Math.sin(layerAngle);
    
            for (int particleLayer = 0; particleLayer < 5; particleLayer++) {
                double y = (layerHeight / durationTicks) * ticksElapsed + layerHeight * (layer - 1);
                Vector offset = new Vector(x, y, z);
                Vector particleLocation = player.getLocation().toVector().add(offset).add(tornadoDirection);
                Location particleLocationWorld = particleLocation.toLocation(player.getWorld());
                player.getWorld().spawnParticle(Particle.CLOUD, particleLocationWorld, 3, 0.13, 0.13, 0.13, 0.13);
            }
        }
        ticksElapsed++;
    }
}