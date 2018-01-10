package pl.inder00.rihc.castlemod.modes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util
{
  public Util() {}
  
  public static boolean chance(double chance)
  {
    Random random = new Random();
    double random1 = random.nextDouble() * 100.0D;
    if (random1 <= chance) {
      return true;
    }
    return false;
  }
  












  public static String calculateTime(int seconds)
  {
    int sec = seconds % 60;
    int minutes = seconds % 3600 / 60;
    if ((sec == 9) || (sec == 8) || (sec == 7) || (sec == 6) || (sec == 5) || (sec == 4) || (sec == 3) || (sec == 2) || (sec == 1))
      return minutes + ":0" + sec;
    if (sec == 0) {
      return minutes + ":" + sec + "0";
    }
    return minutes + ":" + sec;
  }
  
  public static ItemStack getStatus(Arena g)
  {
    ItemStack item = null;
    if ((g.getStatus() == ArenaStatus.WAITING) && (g.getNumPlayers() == 0)) {
      item = new ItemStack(Material.STAINED_CLAY, g.getNumPlayers(), (short)5);
    } else if ((g.getStatus() == ArenaStatus.WAITING) && (g.getNumPlayers() > 0)) {
      item = new ItemStack(Material.STAINED_CLAY, g.getNumPlayers(), (short)4);
    } else if (g.getStatus() == ArenaStatus.STARTING) {
      item = new ItemStack(Material.STAINED_CLAY, g.getNumPlayers(), (short)6);
    } else if (g.getStatus() == ArenaStatus.PLAYING) {
      item = new ItemStack(Material.STAINED_CLAY, g.getNumPlayers(), (short)14);
    } else if (g.getStatus() == ArenaStatus.RESTARTING) {
      item = new ItemStack(Material.STAINED_CLAY, g.getNumPlayers(), (short)2);
    }
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName("§8» §7Arena: §6" + g.getName());
    List<String> s = new ArrayList();
    String status = "W grze";
    if (g.getStatus() == ArenaStatus.WAITING) status = "Oczekiwanie";
    if (g.getStatus() == ArenaStatus.STARTING) status = "Startowanie";
    if (g.getStatus() == ArenaStatus.PLAYING) status = "W grze";
    if (g.getStatus() == ArenaStatus.RESTARTING) status = "Restartowanie";
    s.add("§8» §7Status: §6" + status);
    s.add("§8» §7Liczba graczy: §6" + g.getNumPlayers() + "/12");
    s.add("§8» §7Pozostaly czas: §6" + calculateTime(g.getTimeGame()));
    s.add("§8» §bBroniacy: §6" + g.getTeam1().size());
    s.add("§8» §cAtakujacy: §6" + g.getTeam2().size());
    meta.setLore(s);
    item.setItemMeta(meta);
    return item;
  }
  
  public static Inventory getInventory() { int column = 9;
    if (Arena.arenas.size() > 8) {
      column = 18;
    } else if (Arena.arenas.size() > 17) {
      column = 27;
    } else if (Arena.arenas.size() > 26) {
      column = 36;
    } else if (Arena.arenas.size() > 35) {
      column = 45;
    } else if (Arena.arenas.size() > 44) {
      column = 54;
    }
    Inventory inv = Bukkit.createInventory(null, column, "§6Dostepne areny");
    int id = 0;
    for (Arena g : Arena.arenas) {
      inv.setItem(id, getStatus(g));
      id++;
    }
    return inv;
  }
}
