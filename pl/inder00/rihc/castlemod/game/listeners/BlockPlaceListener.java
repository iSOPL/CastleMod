package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class BlockPlaceListener implements Listener
{
  public BlockPlaceListener() {}
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
  public void onPlace(BlockPlaceEvent e)
  {
    if (e.isCancelled()) {
      return;
    }
    Player p = e.getPlayer();
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena a = Arena.get(u.getArena().getName());
      if (a.getStatus() == ArenaStatus.PLAYING) {
        if ((u.getArena() == a) && (u.getTeam() == 1) && (a.inAttackArea(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          p.sendMessage("§cNie mozesz niszczyc/stawiac blokow na strefie przejmowania!");
          return;
        }
        if ((u.getArena() == a) && (!a.isCastleCuboid())) return;
        if ((u.getArena() == a) && (u.getTeam() == 2) && (a.atArea(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          return;
        }
        if ((u.getArena() == a) && (a.getTntExplode() > 0) && (u.getTeam() == 1) && (a.atArea(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          return;
        }
        if ((u.getArena() == a) && (!a.atMap(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          return;
        }
        return;
      }
      e.setCancelled(true);
      return;
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
  public void onBreak(BlockBreakEvent e)
  {
    if (e.isCancelled()) {
      return;
    }
    Player p = e.getPlayer();
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena a = Arena.get(u.getArena().getName());
      if (a.getStatus() == ArenaStatus.PLAYING) {
        if ((u.getArena() == a) && (u.getTeam() == 1) && (a.inAttackArea(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          p.sendMessage("§cNie mozesz niszczyc/stawiac blokow na strefie przejmowania!");
          return;
        }
        if ((u.getArena() == a) && (!a.isCastleCuboid())) return;
        if ((u.getArena() == a) && (u.getTeam() == 2) && (a.atArea(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          return;
        }
        if ((u.getArena() == a) && (!a.atMap(e.getBlock().getLocation(), a))) {
          e.setCancelled(true);
          return;
        }
        return;
      }
      e.setCancelled(true);
      return;
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onExplode(EntityExplodeEvent e) {
    if (e.isCancelled()) {
      return;
    }
    for (Arena a : Arena.arenas) {
      if (a.getStatus() == ArenaStatus.PLAYING) {
        if (!a.isCastleCuboid()) return;
        if (a.atArea(e.getLocation(), a)) {
          if (a.getTntExplode() < 31) {
            for (Human u : a.getTeam1()) {
              org.bukkit.Bukkit.getPlayer(u.getName()).sendMessage("§cZablokowano budowanie na 2min z powodu wybuchu TNT!");
            }
          }
          a.setTntExplode(120);
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onBucketEmpty(PlayerBucketEmptyEvent e) {
    if (e.isCancelled()) return;
    if ((e.getBucket() == Material.LAVA_BUCKET) || (e.getBucket() == Material.WATER_BUCKET)) {
      Player p = e.getPlayer();
      Human u = Human.get(p.getName());
      if (u.getArena() != null) {
        Arena a = Arena.get(u.getArena().getName());
        if (a.getStatus() == ArenaStatus.PLAYING) {
          if ((u.getArena() == a) && (u.getTeam() == 1) && (a.inAttackArea(e.getBlockClicked().getLocation(), a))) {
            e.setCancelled(true);
            p.sendMessage("§cNie mozesz niszczyc/stawiac blokow na strefie przejmowania!");
            return;
          }
          if ((u.getArena() == a) && (!a.isCastleCuboid())) return;
          if ((u.getArena() == a) && (!a.atMap(e.getBlockClicked().getLocation(), a))) {
            e.setCancelled(true);
            return;
          }
          return;
        }
        e.setCancelled(true);
        return;
      }
    }
  }
}
