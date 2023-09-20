package org.crafted.craftedmage.magic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.crafted.craftedmage.CraftedMage;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ItemManager {
    public static ItemStack devWand;
    public static ItemStack FireWand;
    public static ItemStack WaterWand;
    public static ItemStack WindWand;
    public static ItemStack EarthWand;

    public static void ItemInit() {
        createDevWand();
       // createFireWand();
    }

    static Plugin plugin = CraftedMage.getPlugin(CraftedMage.class);

    public static void createDevWand() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Dev Wand");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_BLUE + "the altimate wand");
        lore.add(ChatColor.DARK_BLUE + "devwand");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        devWand = item;
        NamespacedKey devWandKey = new NamespacedKey(plugin, "devWandKey");

        //Recipe
        ShapedRecipe devRecipe = new ShapedRecipe(devWandKey, item);
        devRecipe.shape("afa","asa","asa");
        devRecipe.setIngredient('s',Material.STICK);
        devRecipe.setIngredient('f', Material.FEATHER);
        devRecipe.setIngredient('a', Material.AIR);
        getServer().addRecipe(devRecipe);

    }
  //  public static void createFireWand() {
      //  ItemStack item = new ItemStack(Material.STICK);
      //  ItemMeta meta = item.getItemMeta();
       // meta.setDisplayName(ChatColor.RED + "Fire Wand");
      //  List<String> lore = new ArrayList<>();
      //  lore.add(ChatColor.RED + "The Fire Stick");
      //  lore.add(ChatColor.DARK_RED + "Level 1");
      //  meta.setLore(lore);
      //  meta.addEnchant(Enchantment.LUCK,1,false);
      //  meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
     //   item.setItemMeta(meta);
     //   FireWand = item;
   // }
}
