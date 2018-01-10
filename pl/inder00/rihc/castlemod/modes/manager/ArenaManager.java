package pl.inder00.rihc.castlemod.modes.manager;

import java.util.Iterator;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.ScoreboardManager;
import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class ArenaManager
{
  public ArenaManager() {}
  
  public static void joinGame(Player p, String a)
  {
    Human u = Human.get(p.getName());
    if (u.getArena() == null) {
      Arena arena = Arena.get(a);
      if (arena != null) {
        if ((arena.getNumPlayers() >= 10) && (!p.hasPermission("cm.vip"))) {
          p.sendMessage("&cVIP: Slot jest zarezerowany dla rangi VIP!");
          return;
        }
        if ((arena.getStatus() == ArenaStatus.RESTARTING) || (arena.getStatus() == ArenaStatus.PLAYING)) {
          p.sendMessage("§c[CastleMod] Arena jest restartowana lub trwa na niej juz rozgrywka");
          return;
        }
        if (arena.getNumPlayers() > 11) {
          p.sendMessage("§c[CastleMod] Arena jest juz pelna");
          return;
        }
        getHandleinventory.b(new NBTTagList());
        p.updateInventory();
        arena.setNumPlayers(arena.getNumPlayers() + 1);
        arena.addUsers(p);
        for (Player pall : arena.getUsers()) {
          pall.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7dolaczyl do areny (§6" + arena.getNumPlayers() + "§7/§612§7)");
        }
        p.sendMessage("§6[CastleMod] §7Mapa §6" + arena.getName() + " §7zostala stworzona przez §6" + arena.getCreator());
        











        ItemStack sklep = new ItemStack(Material.BONE, 1);
        ItemMeta sklepMeta = sklep.getItemMeta();
        sklepMeta.setDisplayName("§6Itemy Stale");
        sklep.setItemMeta(sklepMeta);
        
        ItemStack wyjscie = new ItemStack(Material.REDSTONE, 1);
        ItemMeta wyjscieMeta = sklep.getItemMeta();
        wyjscieMeta.setDisplayName("§cWyjdz z areny");
        wyjscie.setItemMeta(wyjscieMeta);
        
        ItemStack vote = new ItemStack(Material.ARROW, 1);
        ItemMeta voteMeta = sklep.getItemMeta();
        voteMeta.setDisplayName("§6Zaglosuj na start areny");
        vote.setItemMeta(voteMeta);
        
        p.getInventory().setItem(0, sklep);
        p.getInventory().setItem(7, vote);
        p.getInventory().setItem(8, wyjscie);
        
        p.sendMessage("§6[CastleMod] §7Dolaczyles do areny §6" + arena.getName());
        p.teleport(CastleMod.lobby);
        u.setArena(arena);
      } else {
        p.sendMessage("§c[CastleMod] Podana arena nie istnieje!");
      }
    }
    else
    {
      p.sendMessage("§c[CastleMod] Aktualnie grasz na arenie, jesli uwazasz, ze to blad wejdz ponownie na serwer");
      return;
    }
  }
  
  public static void vote(Player p) {
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena arena = u.getArena();
      if (arena != null) {
        if (arena.getStatus() == ArenaStatus.WAITING) {
          if (arena.getNumPlayers() < 2) {
            p.sendMessage("§c[CastleMod] Aby moc glosowac, musi byc przynajmniej 2 osoby na arenie!");
            return;
          }
          if (u.isVoted()) {
            p.sendMessage("§c[CastleMod] Juz glosowales!");
            return;
          }
          u.setVoted(true);
          arena.setVotes(arena.getVotes() + 1);
          int percent = (int)(arena.getVotes() * 100.0F / arena.getNumPlayers());
          p.sendMessage("§6[CastleMod] §7Oddales glos na start areny: §6" + percent + "%§7/§675%§7!");
          return; }
        if ((arena.getStatus() == ArenaStatus.STARTING) || (arena.getStatus() == ArenaStatus.PLAYING)) {
          p.sendMessage("§c[CastleMod] Gra sie rozpoczela!");
        }
      }
      else {
        p.sendMessage("§c[CastleMod] Podana arena nie istnieje!");
      }
    }
    else {
      p.sendMessage("§c[CastleMod] Aktualnie grasz na arenie, jesli uwazasz, ze to blad wejdz ponownie na serwer");
      return;
    }
  }
  
  public static void leaveGame(Player p, boolean notify) {
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena arena = u.getArena();
      if (arena != null) {
        arena.setNumPlayers(arena.getNumPlayers() - 1);
        Player plls; if ((u.getLastDamaged() != null) && (arena.getStatus() == ArenaStatus.PLAYING)) {
          int points = getInstrankingDeath;
          u.setKills(u.getKills() + 1);
          u.setPkt(u.getPkt() + points);
          for (Iterator localIterator = arena.getUsers().iterator(); localIterator.hasNext();) { plls = (Player)localIterator.next();
            if (notify) plls.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7zostal zabity przez §6" + u.getLastDamaged().getName() + " §7(§6" + points + "§7)");
          }
        } else if ((u.getLastDamaged() == null) && (arena.getStatus() == ArenaStatus.PLAYING)) {
          for (Player plls : arena.getUsers()) {
            if (notify) { plls.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7znigal");
            }
          }
        }
        if ((arena.getStatus() == ArenaStatus.WAITING) || (arena.getStatus() == ArenaStatus.STARTING)) {
          for (Player pall : arena.getUsers()) {
            if (notify) pall.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7opuscil gre (§6" + arena.getNumPlayers() + "§7/§612§7)");
          }
        } else {
          Arena a = u.getArena();
          if (u.getTeam() == 1) {
            a.remTeam1(u);
          } else {
            a.remTeam2(u);
          }
          if (notify) p.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7opuscil gre (§6" + arena.getNumPlayers() + "§7/§624§7)");
        }
        getHandleinventory.b(new NBTTagList());
        p.updateInventory();
        p.teleport(CastleMod.lobby);
        p.setScoreboard(org.bukkit.Bukkit.getScoreboardManager().getNewScoreboard());
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.setFallDistance(0.0F);
        p.setFireTicks(0);
        arena.remUsers(p);
        if (((u.getArena().getStatus() == ArenaStatus.STARTING) || (u.getArena().getStatus() == ArenaStatus.WAITING)) && (u.isVoted())) {
          u.getArena().setVotes(u.getArena().getVotes() - 1);
        }
        u.setArena(null);
        u.setVoted(false);
        return;
      }
      if (notify) p.sendMessage("§c[CastleMod] Podana arena nie istnieje!");
      return;
    }
    

    if (notify) { p.sendMessage("§c[CastleMod] Nie grasz na zadnej arenie");
    }
  }
  
  public static void items(Player p, int i)
  {
    ItemStack helm = new ItemStack(Material.DIAMOND_HELMET, 1);
    ItemMeta helmIM = helm.getItemMeta();
    helmIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
    helmIM.addEnchant(Enchantment.DURABILITY, 3, true);
    helm.setItemMeta(helmIM);
    
    ItemStack klata = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
    ItemMeta klataIM = klata.getItemMeta();
    klataIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
    klataIM.addEnchant(Enchantment.DURABILITY, 3, true);
    klata.setItemMeta(klataIM);
    
    ItemStack spodnie = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
    ItemMeta spodnieIM = spodnie.getItemMeta();
    spodnieIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
    spodnieIM.addEnchant(Enchantment.DURABILITY, 3, true);
    spodnie.setItemMeta(spodnieIM);
    
    ItemStack buty = new ItemStack(Material.DIAMOND_BOOTS, 1);
    ItemMeta butyIM = buty.getItemMeta();
    butyIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
    butyIM.addEnchant(Enchantment.DURABILITY, 3, true);
    buty.setItemMeta(butyIM);
    
    p.getInventory().setHelmet(helm);
    p.getInventory().setChestplate(klata);
    p.getInventory().setLeggings(spodnie);
    p.getInventory().setBoots(buty);
    

    ItemStack miecz = new ItemStack(Material.DIAMOND_SWORD);
    ItemMeta mieczIM = miecz.getItemMeta();
    mieczIM.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
    mieczIM.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
    miecz.setItemMeta(mieczIM);
    
    ItemStack kilof = new ItemStack(Material.DIAMOND_PICKAXE);
    ItemMeta kilofIM = kilof.getItemMeta();
    kilofIM.addEnchant(Enchantment.DURABILITY, 3, true);
    kilofIM.addEnchant(Enchantment.DIG_SPEED, 5, true);
    kilof.setItemMeta(kilofIM);
    
    ItemStack luk = new ItemStack(Material.BOW);
    ItemMeta lukIM = luk.getItemMeta();
    lukIM.addEnchant(Enchantment.ARROW_FIRE, 1, true);
    lukIM.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
    lukIM.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
    lukIM.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
    luk.setItemMeta(lukIM);
    
    ItemStack koxy = new ItemStack(Material.GOLDEN_APPLE, 2, (short)1);
    ItemStack refy = new ItemStack(Material.GOLDEN_APPLE, 5);
    ItemStack steki = new ItemStack(Material.COOKED_BEEF, 16);
    
    p.getInventory().addItem(new ItemStack[] { miecz });
    p.getInventory().addItem(new ItemStack[] { luk });
    p.getInventory().addItem(new ItemStack[] { koxy });
    p.getInventory().addItem(new ItemStack[] { refy });
    p.getInventory().addItem(new ItemStack[] { steki });
    p.getInventory().addItem(new ItemStack[] { kilof });
    if (i == 1) {
      p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.TNT, 64) });
    }
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COBBLESTONE, 64) });
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ENDER_PEARL, 1) });
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WATER_BUCKET, 1) });
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW, 1) });
    

    p.updateInventory();
  }
}
