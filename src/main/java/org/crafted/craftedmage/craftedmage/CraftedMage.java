package org.crafted.craftedmage.craftedmage;

import events.EventManager;
import items.magic.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CraftedMage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ItemManager.ItemInit();
        getLogger().info("loaded");
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getLogger().info("events lowaded");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("failed");
    }
}
