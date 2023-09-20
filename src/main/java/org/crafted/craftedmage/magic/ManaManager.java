package org.crafted.craftedmage.magic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class ManaManager {

    private Map<Player, Integer> manaMap = new HashMap<>();
    private Map<Player, Integer> rechargeCooldowns = new HashMap<>();
    private ManaUpdateTask manaUpdateTask;
    private JavaPlugin plugin;

    public ManaManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.manaUpdateTask = new ManaUpdateTask();
        this.manaUpdateTask.runTaskTimer(plugin, 0, 1);
    }
    public void setInitialMana(Player player, int mana) {
        manaMap.put(player, mana);
    }
    public boolean consumeMana(Player player, int amount) {
        int currentMana = manaMap.getOrDefault(player, 100);
        if (currentMana >= amount) {
            manaMap.put(player, currentMana - amount);
            startRechargeCooldown(player);
            return true;
        }
        return false;
    }
    private void startRechargeCooldown(Player player) {
        rechargeCooldowns.put(player, 300);
    }
    private class ManaUpdateTask extends BukkitRunnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateScoreboard(player);
                updateRecharge(player);
            }
        }
    }
    private void updateScoreboard(Player player) {
        int mana = manaMap.getOrDefault(player, 0);
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (isWand(heldItem)) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("mana", "dummy", "Mana");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score = objective.getScore("Mana: " + mana);
            score.setScore(0);

            player.setScoreboard(scoreboard);
        } else {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
    private boolean isWand(ItemStack item) {
        return item != null && item.getType() == Material.STICK;
    }
    private void updateRecharge(Player player) {
        if (rechargeCooldowns.containsKey(player)) {
            int cooldown = rechargeCooldowns.get(player);
            if (cooldown > 0) {
                rechargeCooldowns.put(player, cooldown - 1);
            } else {
                rechargeCooldowns.remove(player);
                int currentMana = manaMap.getOrDefault(player, 0);
                if (currentMana < 100) {
                    manaMap.put(player, currentMana + 10);
                }
            }
        }
    }
}