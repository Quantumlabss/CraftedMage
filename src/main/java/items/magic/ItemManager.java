package items.magic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.crafted.craftedmage.craftedmage.CraftedMage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ItemManager {
    public static ItemStack devWand;

    public static void ItemInit() {
        createDevWand();
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
}
