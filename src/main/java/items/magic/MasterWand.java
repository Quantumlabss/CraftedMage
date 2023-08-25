package items.magic;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MasterWand {

    public static ItemStack masterWand;

    public static void init() {
        createMasterWand();
    }

    private static void createMasterWand() {

        ItemStack masterwand = new ItemStack(Material.STICK, 1);
        ItemMeta masterMeta = masterwand.getItemMeta();
        masterMeta.setDisplayName(ChatColor.GOLD + "W" + ChatColor.RED + "a" + ChatColor.BLUE + "n" + ChatColor.GREEN + "d");
        //Lore
        List<String> masterLore = new ArrayList<>();

        masterLore.add("An Ancient Talisman");
        masterLore.add("use this to unlock your full powers");
        masterMeta.setLore(masterLore);

        masterMeta.addEnchant(Enchantment.LUCK, 1, false);
        masterMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        masterwand.setItemMeta(masterMeta);
        masterWand = masterwand;
    }
}
