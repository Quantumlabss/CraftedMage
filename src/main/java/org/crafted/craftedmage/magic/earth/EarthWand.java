package org.crafted.craftedmage.magic.earth;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EarthWand {
    public static ItemStack earthWand;

    public static void init() {
        createAirWand();
    }
    private static void createAirWand() {
        //setting the name of the wand as well as item
        ItemStack EarthWand = new ItemStack(Material.STICK, 1);
        ItemMeta earthmeta = EarthWand.getItemMeta();
        earthmeta.setDisplayName(ChatColor.RED + "Earth Wand");

        //lore
        List<String> earthLore = new ArrayList<>();
        earthLore.add(ChatColor.DARK_RED + "The wand of earth");
        earthLore.add(ChatColor.DARK_RED + "Use this item to access your full potential");
        earthmeta.setLore(earthLore);
        //assign enchant and add it hidden
        earthmeta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, false);
        earthmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        //register item
        EarthWand.setItemMeta(earthmeta);
        earthWand = EarthWand;
    }
}
