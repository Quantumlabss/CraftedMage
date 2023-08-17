package items.magic.fire;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FireWand {
    public static ItemStack fireWand;
    
    public static void init() {
        createFireWand();
    }
    private static void createFireWand() {
        ItemStack firewand = new ItemStack(Material.STICK, 1);
        ItemMeta firemeta = firewand.getItemMeta();
        firemeta.setDisplayName(ChatColor.RED + "Fire Wand");
        
        //lore
        List<String> fireLore = new ArrayList<>();
        fireLore.add(ChatColor.DARK_RED + "The wand of fire");
        fireLore.add(ChatColor.DARK_RED + "Use this item to acces your full potential");
        firemeta.setLore(fireLore);
        
        firemeta.addEnchant(Enchantment.LUCK, 1, false);
        firemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        
        firewand.setItemMeta(firemeta);
        fireWand = firewand;
    }
}
