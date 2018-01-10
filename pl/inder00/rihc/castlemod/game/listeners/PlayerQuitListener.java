package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.inder00.rihc.castlemod.game.files.Human;

public class PlayerQuitListener implements Listener
{
  public PlayerQuitListener() {}
  
  @org.bukkit.event.EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    Human u = Human.get(e.getPlayer().getName());
    if (u.getArena() != null) {
      pl.inder00.rihc.castlemod.modes.manager.ArenaManager.leaveGame(e.getPlayer(), false);
    }
  }
}
