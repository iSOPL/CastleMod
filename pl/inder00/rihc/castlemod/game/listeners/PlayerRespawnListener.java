package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.inder00.rihc.castlemod.CastleMod;

public class PlayerRespawnListener implements org.bukkit.event.Listener
{
  public PlayerRespawnListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.MONITOR)
  public void onRespawn(PlayerRespawnEvent e)
  {
    if (e.isBedSpawn()) {
      return;
    }
    e.setRespawnLocation(CastleMod.lobby);
  }
}
