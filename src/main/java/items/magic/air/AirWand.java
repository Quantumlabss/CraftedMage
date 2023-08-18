package items.magic.air;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AirWand {
    public static ItemStack airWand;

    public static void init() {
        createAirWand();
    }
    private static void createAirWand() {
        //setting the name of the wand as well as item
        ItemStack AirWand = new ItemStack(Material.STICK, 1);
        ItemMeta airmeta = AirWand.getItemMeta();
        airmeta.setDisplayName(ChatColor.AQUA + "Air Wand");

        //lore
        List<String> airLore = new ArrayList<>();
        airLore.add(ChatColor.DARK_AQUA + "The wand of air");
        airLore.add(ChatColor.DARK_AQUA + "Use this item to access your full potential");
        airmeta.setLore(airLore);
        //assign enchant and add it hidden
        airmeta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, false);
        airmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        //register item
        AirWand.setItemMeta(airmeta);
        airWand = AirWand;
    }
}
