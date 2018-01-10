package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerDropItemListener implements org.bukkit.event.Listener
{
  public PlayerDropItemListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.MONITOR)
  public void onPlayerDropItemEvent(PlayerDropItemEvent e)
  {
    if (e.isCancelled()) {
      return;
    }
    Player p = e.getPlayer();
    Human u = Human.get(p.getName());
    if (u.getArena() != null) {
      Arena a = Arena.get(u.getArena().getName());
      if ((u.getArena() == a) && (
        (a.getStatus() == ArenaStatus.WAITING) || (a.getStatus() == ArenaStatus.STARTING))) {
        e.setCancelled(true);
        return;
      }
    }
  }
}
