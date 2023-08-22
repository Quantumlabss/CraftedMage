// Source code is decompiled from a .class file using FernFlower decompiler.(Visual studio)
package items.magic;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MasterWand {
   public static ItemStack masterWand;

   public MasterWand() {
   }

   public static void init() {
      createMasterWand();
   }

private static void createMasterWand() {
    masterWand = new ItemStack(Material.STICK, 1);
    ItemMeta masterMeta = masterWand.getItemMeta();
    masterMeta.setDisplayName(ChatColor.GOLD + "W" + ChatColor.RED + "a" + ChatColor.BLUE + "n" + ChatColor.GREEN + "d");
    List<String> masterLore = new ArrayList<>();
    masterLore.add("An Ancient Talisman");
    masterLore.add("use this to unlock your full powers");
    masterMeta.setLore(masterLore);
    masterMeta.addEnchant(Enchantment.LUCK, 1, false);
    masterMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    masterWand.setItemMeta(masterMeta);
}
}
