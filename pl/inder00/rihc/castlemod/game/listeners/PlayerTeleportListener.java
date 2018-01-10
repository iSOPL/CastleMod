package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerTeleportListener implements org.bukkit.event.Listener
{
  public PlayerTeleportListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.MONITOR)
  public void noEnderPearl(PlayerTeleportEvent e)
  {
    if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
      Player p = e.getPlayer();
      org.bukkit.Location to = e.getTo();
      Human a = Human.get(p.getName());
      if ((a.getArena() != null) && (a.getArena().getStatus() == ArenaStatus.PLAYING) && 
        (!a.getArena().atMap(to, a.getArena()))) {
        p.sendMessage("§cNie mozesz wyrzucac perly za arene!");
        e.setTo(e.getFrom());
        e.setCancelled(true);
        return;
      }
    }
  }
}
