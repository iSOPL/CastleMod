package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.Human;

public class InventoryClickListener implements org.bukkit.event.Listener
{
  public InventoryClickListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.MONITOR)
  public void onInventoryClick(InventoryClickEvent e)
  {
    if (e.isCancelled()) return;
    Player p = (Player)e.getWhoClicked();
    Human h = Human.get(p.getName());
    if (e.getInventory() == null) return;
    if (e.getCurrentItem() == null) return;
    if (e.getCurrentItem().getType().equals(Material.AIR)) return;
    if ((h.getArena() != null) && (e.getInventory().getTitle() != null) && (e.getInventory().getTitle().equals("§6Itemy stale"))) {
      Config cfg = Config.getInst();
      if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
        if (h.isPerla()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztPerla) {
          h.setPerla(true);
          h.setCoins(h.getCoins() - kosztPerla);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + "§7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType());
        }
      }
      else if ((e.getCurrentItem().getType() == Material.GOLDEN_APPLE) && (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Super Złote Jabłko"))) {
        if (h.isKoks()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztKoks) {
          h.setKoks(true);
          h.setCoins(h.getCoins() - kosztKoks);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + "§7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType());
        }
      }
      else if ((e.getCurrentItem().getType() == Material.GOLDEN_APPLE) && (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Złote Jabłko"))) {
        if (h.isReffil()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztReffil) {
          h.setReffil(true);
          h.setCoins(h.getCoins() - kosztReffil);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + "§7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType());
        }
      }
      else if ((e.getCurrentItem().getType() == Material.POTION) && (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Odporność na Ogien (6min)"))) {
        if (h.isOgien6min()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztOgien6min) {
          h.setOgien6min(true);
          h.setCoins(h.getCoins() - kosztOgien6min);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + " (Odporność na Ogien 6min) §7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType() + " (Odporność na Ogien 6min)");
        }
      }
      else if ((e.getCurrentItem().getType() == Material.POTION) && (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Sila I (3min)"))) {
        if (h.isSila3min()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztSila3min) {
          h.setSila3min(true);
          h.setCoins(h.getCoins() - kosztSila3min);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + " (Sila I 3min) §7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType() + " (Sila I 3min)");
        }
      }
      else if ((e.getCurrentItem().getType() == Material.POTION) && (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Szybkość I (2min 15sek)"))) {
        if (h.isSpeed1_2min15sec()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztSpeed) {
          h.setSpeed1_2min15sec(true);
          h.setCoins(h.getCoins() - kosztSpeed);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + " (Szybkość I 2min 15sek) §7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType() + " (Szybkość I 2min 15sek)");
        }
      }
      else if ((e.getCurrentItem().getType() == Material.POTION) && (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Natychmastowe leczenie I"))) {
        if (h.isInstantheal()) {
          p.sendMessage("§cNie mozesz zakupic kolejny raz tego przedmiotu!");
        }
        else if (h.getCoins() >= kosztInstantHeal) {
          h.setInstantheal(true);
          h.setCoins(h.getCoins() - kosztInstantHeal);
          p.sendMessage("§6[CastleMod] §7Zakupiles nowy staly przedmiot (§61x " + e.getCurrentItem().getType() + " (Natychmastowe leczenie I) §7)");
        } else {
          p.sendMessage("§cNie posiadasz wymaganej kwoty, aby zakupic 1x " + e.getCurrentItem().getType() + " (Natychmastowe leczenie I)");
        }
      }
      
      e.setCancelled(true);
      p.closeInventory();
      return;
    }
    if ((e.getInventory().getTitle() != null) && (e.getInventory().getTitle().equalsIgnoreCase("§6Dostepne areny"))) {
      ItemStack select = e.getCurrentItem();
      String arena = select.getItemMeta().getDisplayName().replace("§8» §7Arena: §6", "");
      pl.inder00.rihc.castlemod.modes.manager.ArenaManager.joinGame(p, arena);
      e.setCancelled(true);
      p.closeInventory();
      return;
    }
  }
}
