package pl.inder00.rihc.castlemod.modes;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.Human;


public class ShopGUI
{
  public ShopGUI() {}
  
  public static void show(Player p, Human e)
  {
    Config cfg = Config.getInst();
    int perla = kosztPerla;
    int koks = kosztKoks;
    int reffil = kosztReffil;
    int sila = kosztSila3min;
    int ogien = kosztOgien6min;
    int speed = kosztSpeed;
    int instantheal = kosztInstantHeal;
    

    Inventory inv = Bukkit.createInventory(null, 27, "§6Itemy stale");
    ItemStack szyba = Item.glassPane();
    inv.setItem(0, szyba);
    inv.setItem(1, szyba);
    inv.setItem(2, szyba);
    inv.setItem(3, szyba);
    inv.setItem(4, szyba);
    inv.setItem(5, szyba);
    inv.setItem(6, szyba);
    inv.setItem(7, szyba);
    inv.setItem(8, szyba);
    inv.setItem(9, szyba);
    String unlockedPerla = "§c§lZABLOKOWANY";
    if (e.isPerla()) unlockedPerla = "§a§lODBLOKOWANY";
    inv.setItem(10, Item.createIM(Material.ENDER_PEARL, "§6Perła", 1, Arrays.asList(new String[] { "§aCena: §c" + perla + "$", "", unlockedPerla })));
    
    String unlockedKoks = "§c§lZABLOKOWANY";
    if (e.isKoks()) unlockedKoks = "§a§lODBLOKOWANY";
    inv.setItem(11, Item.createIM(Material.GOLDEN_APPLE, "§6Super Złote Jabłko", 1, (short)1, Arrays.asList(new String[] { "§aCena: §c" + koks + "$", "", unlockedKoks })));
    
    String unlockedReffil = "§c§lZABLOKOWANY";
    if (e.isReffil()) unlockedReffil = "§a§lODBLOKOWANY";
    inv.setItem(12, Item.createIM(Material.GOLDEN_APPLE, "§6Złote Jabłko", 1, Arrays.asList(new String[] { "§aCena: §c" + reffil + "$", "", unlockedReffil })));
    
    String unlockedSila = "§c§lZABLOKOWANY";
    if (e.isSila3min()) unlockedSila = "§a§lODBLOKOWANY";
    inv.setItem(13, Item.createIM(Material.POTION, "§6Sila I (3min)", 1, (short)8201, Arrays.asList(new String[] { "§aCena: §c" + sila + "$", "", unlockedSila })));
    
    String unlockedOgien = "§c§lZABLOKOWANY";
    if (e.isOgien6min()) unlockedOgien = "§a§lODBLOKOWANY";
    inv.setItem(14, Item.createIM(Material.POTION, "§6Odporność na Ogien (6min)", 1, (short)16451, Arrays.asList(new String[] { "§aCena: §c" + ogien + "$", "", unlockedOgien })));
    
    String unlockedSpeed = "§c§lZABLOKOWANY";
    if (e.isSpeed1_2min15sec()) unlockedSpeed = "§a§lODBLOKOWANY";
    inv.setItem(15, Item.createIM(Material.POTION, "§6Szybkość I (2min 15sek)", 1, (short)16386, Arrays.asList(new String[] { "§aCena: §c" + speed + "$", "", unlockedSpeed })));
    
    String unlockedInstantHeal = "§c§lZABLOKOWANY";
    if (e.isInstantheal()) unlockedInstantHeal = "§a§lODBLOKOWANY";
    inv.setItem(16, Item.createIM(Material.POTION, "§6Natychmastowe leczenie I", 1, (short)16453, Arrays.asList(new String[] { "§aCena: §c" + instantheal + "$", "", unlockedInstantHeal })));
    
    inv.setItem(17, szyba);
    inv.setItem(18, szyba);
    inv.setItem(19, szyba);
    inv.setItem(20, szyba);
    inv.setItem(21, szyba);
    inv.setItem(22, szyba);
    inv.setItem(23, szyba);
    inv.setItem(24, szyba);
    inv.setItem(25, szyba);
    inv.setItem(26, szyba);
    
    p.openInventory(inv);
  }
}
