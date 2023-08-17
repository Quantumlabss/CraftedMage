package org.crafted.craftedmage.craftedmage;

import events.EventManager;
import items.magic.ItemManager;
import items.magic.air.AirWand;
import items.magic.fire.FireWand;
import items.magic.water.WaterWand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CraftedMage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ItemManager.ItemInit();
        getLogger().info("loaded");
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getLogger().info("events lowaded");
        getLogger().info("Loading Kumbhak's Items");
        AirWand.init();
        FireWand.init();
        WaterWand.init();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("failed");
    }
}
