package org.crafted.craftedmage.craftedmage;

import Commands.CommandWandClass;
import events.EventManager;
import items.magic.ItemManager;
import items.magic.air.AirWand;
import items.magic.fire.FireWand;
import items.magic.water.WaterWand;
import org.bukkit.plugin.java.JavaPlugin;
import items.magic.MasterWand;

public final class CraftedMage extends JavaPlugin {
    private static CraftedMage instance;


    @Override
    public void onEnable() {
        instance = this;

        // Plugin startup logic
        ItemManager.ItemInit();
        getLogger().info("loaded");
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        this.getCommand("WandGive").setExecutor(new CommandWandClass());
        getLogger().info("events lowaded");
        getLogger().info("Loading Kumbhak's Items");
        AirWand.init();
        FireWand.init();
        WaterWand.init();
        MasterWand.init();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("failed");
    }
    public static CraftedMage getInstance() {
        return instance;
    }
}
