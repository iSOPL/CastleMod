package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerMoveListener implements Listener
{
  public PlayerMoveListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.NORMAL)
  public void onMove(PlayerMoveEvent e)
  {
    if (e.isCancelled()) {
      return;
    }
    Player p = e.getPlayer();
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena a = Arena.get(u.getArena().getName());
      Location from = e.getFrom();
      Location to = e.getTo();
      if (a.getStatus() == ArenaStatus.PLAYING) {
        if (!a.atMap(to, a)) {
          p.teleport(from);
          e.setCancelled(true);
          return;
        }
        if ((u.getTeam() == 2) && (a.inAttackArea(to, a)) && 
          (!a.isAttackingCastle()) && (u.getAntylogout() == 0)) {
          a.setAttackCastle(0);
          a.setAttackerCastle(p);
          a.setAttackingCastle(true);
        }
      }
    }
  }
}
