package events;


import items.magic.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventManager implements Listener {
    @EventHandler
    public void ballFiring(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null) {
            if (e.getItem().getItemMeta().equals(ItemManager.devWand.getItemMeta())) {
                e.setCancelled(true);
                Fireball s = e.getPlayer().launchProjectile(Fireball.class);


            }
        }
    }
}
