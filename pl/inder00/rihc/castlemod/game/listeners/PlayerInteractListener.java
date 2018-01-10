package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.ArenaManager;

public class PlayerInteractListener implements org.bukkit.event.Listener
{
  public PlayerInteractListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerInteract(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena a = Arena.get(u.getArena().getName());
      if (((a.getStatus() == ArenaStatus.WAITING) || (a.getStatus() == ArenaStatus.STARTING)) && (
        (e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.LEFT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK)))) {
        if ((p.getItemInHand().getType().equals(Material.BONE)) && (p.getItemInHand().getItemMeta().getDisplayName().equals("§6Itemy Stale"))) {
          pl.inder00.rihc.castlemod.modes.ShopGUI.show(p, u);
          e.setCancelled(true);
          return;
        }
        if ((p.getItemInHand().getType().equals(Material.REDSTONE)) && (p.getItemInHand().getItemMeta().getDisplayName().equals("§cWyjdz z areny"))) {
          ArenaManager.leaveGame(p, true);
          e.setCancelled(true);
          return;
        }
      }
      





























      if ((a.getStatus() == ArenaStatus.WAITING) && 
        ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.LEFT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK))) && 
        (p.getItemInHand().getType().equals(Material.ARROW)) && (p.getItemInHand().getItemMeta().getDisplayName().equals("§6Zaglosuj na start areny"))) {
        ArenaManager.vote(p);
        e.setCancelled(true);
        return;
      }
    }
  }
}
