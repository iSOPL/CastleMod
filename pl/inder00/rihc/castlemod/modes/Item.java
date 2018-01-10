package pl.inder00.rihc.castlemod.modes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item
{
  public Item() {}
  
  public static ItemStack createIM(Material material, String name, int amount)
  {
    ItemStack is = new ItemStack(material);
    is.setAmount(amount);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', name));
    is.setItemMeta(im);
    return is;
  }
  
  public static ItemStack createIM(Material material, String name, int amount, short a) { ItemStack is = new ItemStack(material, amount, a);
    is.setAmount(amount);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', name));
    is.setItemMeta(im);
    return is;
  }
  
  public static ItemStack createIM(Material material, String name, int amount, short a, java.util.List<String> lore) { ItemStack is = new ItemStack(material, amount, a);
    is.setAmount(amount);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', name));
    im.setLore(lore);
    is.setItemMeta(im);
    return is;
  }
  
  public static ItemStack createIM(Material material, String name, int amount, java.util.List<String> lore) { ItemStack is = new ItemStack(material);
    is.setAmount(amount);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', name));
    im.setLore(lore);
    is.setItemMeta(im);
    return is;
  }
  
  public static ItemStack glassPane() { ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
    is.setAmount(1);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName("");
    is.setItemMeta(im);
    return is;
  }
}
