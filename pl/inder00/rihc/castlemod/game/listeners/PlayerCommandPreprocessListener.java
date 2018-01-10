package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.inder00.rihc.castlemod.game.files.Human;

public class PlayerCommandPreprocessListener implements org.bukkit.event.Listener
{
  public PlayerCommandPreprocessListener() {}
  
  @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.HIGH)
  public void onCommand(PlayerCommandPreprocessEvent e)
  {
    if (e.isCancelled()) {
      return;
    }
    Human u = Human.get(e.getPlayer().getName());
    if (u.getArena() == null) {
      return;
    }
    if ((!e.getMessage().toLowerCase().contains("/castlemod")) && (!e.getMessage().toLowerCase().contains("/cmod")) && (!e.getMessage().toLowerCase().contains("/cm")) && (!e.getPlayer().isOp())) {
      e.getPlayer().sendMessage("§cPodana komenda jest zablokowana na castlemod! Zezwolone komendy: /castlemod");
      e.setCancelled(true);
    }
  }
}
