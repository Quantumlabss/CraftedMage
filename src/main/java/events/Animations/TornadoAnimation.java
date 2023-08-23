package events.Animations;

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

    public TornadoAnimation(Player player, double radius, double height, double strength, int durationTicks, Vector tornadoDirection) {
        this.player = player;
        this.radius = radius;
        this.height = height;
        this.strength = strength;
        this.durationTicks = durationTicks;
        this.ticksElapsed = 0;
        this.tornadoDirection = tornadoDirection.normalize();
    }

    @Override
    public void run() {
        if (ticksElapsed >= durationTicks) {
            cancel();
            return;
        }

        double angle = (2 * Math.PI * ticksElapsed) / durationTicks;

        for (int layer = 1; layer <= 5; layer++) {
            double x = radius * Math.cos(angle) * (layer / 5.0);
            double y = (height / durationTicks) * ticksElapsed;
            double z = radius * Math.sin(angle) * (layer / 5.0);

            Vector offset = new Vector(x, y, z);
            Vector particleLocation = player.getLocation().toVector().add(offset).add(tornadoDirection);

            Location particleLocationWorld = particleLocation.toLocation(player.getWorld());
            player.getWorld().spawnParticle(Particle.CLOUD, particleLocationWorld, 10, 0.1, 0.1, 0.1, 0.1);
        }

        for (Entity entity : player.getNearbyEntities(radius, height, radius)) {
            if (entity instanceof Player && entity.equals(player)) {
                continue;
            }

            double entityAngle = angle * (radius - entity.getLocation().distance(player.getLocation())) / radius;
            double yForce = (height / durationTicks) * ticksElapsed;
            double xForce = radius * Math.cos(entityAngle);
            double zForce = radius * Math.sin(entityAngle);

            Vector forceDirection = new Vector(xForce, yForce, zForce).normalize();
            double forceMagnitude = strength * (1.0 - ticksElapsed / (double) durationTicks);

            entity.setVelocity(forceDirection.multiply(forceMagnitude));
        }

        ticksElapsed++;
    }
}