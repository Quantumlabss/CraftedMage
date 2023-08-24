package events;

import items.magic.air.AirWand;
import events.Animations.TornadoAnimation;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.crafted.craftedmage.craftedmage.CraftedMage;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class EventManagerAir implements Listener {

    private final Map<Player, Long> cooldownMap = new HashMap<>();
    private final int cooldownDurationTicks = 100;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Vector tornadoDirection = player.getLocation().getDirection();
        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().isSimilar(AirWand.airWand)) {
                if (canCastSpell(player)) {
                    int manaCost = 20;
                    if (CraftedMage.getInstance().getManaManager().consumeMana(player, manaCost)) {
                        event.setCancelled(true);
                        triggerTornado(player, tornadoDirection);
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
    private void triggerTornado(Player player, Vector tornadoDirection) {
        double tornadoRadius = 3.0; // set tornado radius
        double tornadoHeight = 10.0; // set tornado height
        double tornadoStrength = 0.5; // set tornado strength
        int animationDurationTicks = 100;
        TornadoAnimation tornadoAnimation = new TornadoAnimation(player, tornadoRadius, tornadoHeight, tornadoStrength, animationDurationTicks, tornadoDirection);
        tornadoAnimation.runTaskTimer(CraftedMage.getInstance(), 0L, 1L);
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