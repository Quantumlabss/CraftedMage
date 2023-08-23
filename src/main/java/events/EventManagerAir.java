package events;

import items.magic.air.AirWand;
import events.Animations.TornadoAnimation;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.crafted.craftedmage.craftedmage.CraftedMage;
import org.bukkit.util.Vector;

public class EventManagerAir implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Vector tornadoDirection = player.getLocation().getDirection();
        Action action = event.getAction();
        
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().isSimilar(AirWand.airWand)) {
                event.setCancelled(true);
                triggerTornado(player, tornadoDirection);
            }
        }
    }

    private void triggerTornado(Player player, Vector tornadoDirection) {
        double tornadoRadius = 3.0; //set tornado radius
        double tornadoHeight = 10.0; //set tornado height
        double tornadoStrength = 0.5; //set tornado strength
        int animationDurationTicks = 100;

        TornadoAnimation tornadoAnimation = new TornadoAnimation(player, tornadoRadius, tornadoHeight, tornadoStrength, animationDurationTicks, tornadoDirection);
        tornadoAnimation.runTaskTimer(CraftedMage.getInstance(), 0L, 1L);
    }
}