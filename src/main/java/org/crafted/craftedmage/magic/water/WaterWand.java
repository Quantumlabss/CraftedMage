package org.crafted.craftedmage.magic.water;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WaterWand {
    public static ItemStack waterWand;
    public static void init() {
        createWaterWand();
    }
    private static void createWaterWand() {
        ItemStack waterwand = new ItemStack(Material.STICK, 1);
        ItemMeta watermeta = waterwand.getItemMeta();
        watermeta.setDisplayName(ChatColor.BLUE + "Water Wand");

        //lore
        List<String> waterLore = new ArrayList<>();
        waterLore.add(ChatColor.DARK_BLUE + "The wand of water");
        waterLore.add(ChatColor.DARK_BLUE + "Use this item to acces your full potential");
        watermeta.setLore(waterLore);

        watermeta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, false);
        watermeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);

        waterwand.setItemMeta(watermeta);
        waterWand = waterwand;
    }
}
