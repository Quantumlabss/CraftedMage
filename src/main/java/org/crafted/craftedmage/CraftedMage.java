package org.crafted.craftedmage;

import org.crafted.craftedmage.Commands.AirWandClass;
import org.crafted.craftedmage.Commands.EarthWandClass;
import org.crafted.craftedmage.Commands.CommandWandClass;
import org.crafted.craftedmage.Commands.ElementCommandClass;
import org.crafted.craftedmage.Commands.WaterWandClass;
import org.crafted.craftedmage.magic.ItemManager;
import org.crafted.craftedmage.magic.ManaManager;
import org.crafted.craftedmage.events.EventManager;
import org.crafted.craftedmage.events.EventManagerAir;
import org.crafted.craftedmage.events.EventManagerEarth;
import org.crafted.craftedmage.events.EventManagerWater;
import org.crafted.craftedmage.magic.air.AirWand;
import org.crafted.craftedmage.magic.earth.EarthWand;
import org.crafted.craftedmage.magic.fire.FireWand;
import org.crafted.craftedmage.magic.water.WaterWand;

import org.bukkit.plugin.java.JavaPlugin;
import org.crafted.craftedmage.magic.MasterWand;

public final class CraftedMage extends JavaPlugin {
    private static CraftedMage instance;
    private ManaManager manaManager;
    private PlayerJoinManager playerJoinManager;


    @Override
    public void onEnable() {
        instance = this;
        manaManager = new ManaManager(this); // Pass the JavaPlugin instance

        // Plugin startup logic
        ItemManager.ItemInit();
        getLogger().info("loaded");

        // Create an instance of PlayerJoinManager and pass the data folder
        this.playerJoinManager = new PlayerJoinManager(getDataFolder());

        // Register the PlayerJoinManager as a listener
        getServer().getPluginManager().registerEvents(playerJoinManager, this);
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getServer().getPluginManager().registerEvents(new EventManagerWater(), this);
        getServer().getPluginManager().registerEvents(new EventManagerAir(), this);
        getServer().getPluginManager().registerEvents(new EventManagerEarth(), this);
        this.getCommand("FireWand").setExecutor(new CommandWandClass());
        this.getCommand("AirWand").setExecutor(new AirWandClass());
        this.getCommand("WaterWand").setExecutor(new WaterWandClass());
        this.getCommand("EarthWand").setExecutor(new EarthWandClass());
        this.getCommand("Element").setExecutor(new ElementCommandClass());
        getLogger().info("events loaded");
        getLogger().info("Loading Kumbhak's Items");
        AirWand.init();
        FireWand.init();
        WaterWand.init();
        EarthWand.init();
        MasterWand.init();


    }



        //randomly give player element



    @Override
    public void onDisable() {
        // Plugin shutdown logic


        getLogger().info("failed");
    }

    public static CraftedMage getInstance() {
        return instance;
    }
    public ManaManager getManaManager() {
        return manaManager;
    }
}
