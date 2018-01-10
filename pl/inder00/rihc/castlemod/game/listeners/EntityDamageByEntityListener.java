package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class EntityDamageByEntityListener implements Listener
{
  public EntityDamageByEntityListener() {}
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onProjectileHit(EntityDamageByEntityEvent e)
  {
    if (e.isCancelled()) return;
    if (e.getEntity().getType() == EntityType.PLAYER) {
      Player victim = (Player)e.getEntity();
      Human eg = Human.get(victim.getName());
      if ((eg.getArena() != null) && (
        (eg.getArena().getStatus() == ArenaStatus.WAITING) || (eg.getArena().getStatus() == ArenaStatus.STARTING))) {
        e.setCancelled(true);
        return;
      }
    }
    
    if (e.getDamager().getType() == EntityType.PRIMED_TNT) return;
    if (e.getDamager().getType() == EntityType.ARROW) {
      if (e.getEntity().getType() != EntityType.PLAYER) return;
      Projectile projectile = (Projectile)e.getDamager();
      if (projectile.getShooter().getType() != EntityType.PLAYER) return;
      Player attacker = (Player)projectile.getShooter();
      Player victim = (Player)e.getEntity();
      if (attacker != victim) {
        Human a = Human.get(attacker.getName());
        Human b = Human.get(victim.getName());
        if ((((a.getArena() != null ? 1 : 0) & (b.getArena() != null ? 1 : 0)) != 0) && 
          (a.getArena().getName().equalsIgnoreCase(b.getArena().getName())))
        {
          if (a.getTeam() == b.getTeam()) {
            e.setCancelled(true);
            return;
          }
          b.setLastDamaged(attacker);
          a.setLastDamaged(victim);
          a.setAntylogout(20);
          b.setAntylogout(20);
        }
        
      }
      

    }
    else if ((e.getDamager().getType() == EntityType.PLAYER) && (e.getEntity().getType() == EntityType.PLAYER)) {
      Player attacker = (Player)e.getDamager();
      Player victim = (Player)e.getEntity();
      if (attacker != victim) {
        Human a = Human.get(attacker.getName());
        Human b = Human.get(victim.getName());
        if ((((a.getArena() != null ? 1 : 0) & (b.getArena() != null ? 1 : 0)) != 0) && 
          (a.getArena().getName().equalsIgnoreCase(b.getArena().getName())))
        {
          if (a.getTeam() == b.getTeam()) {
            e.setCancelled(true);
            return;
          }
          b.setLastDamaged(attacker);
          a.setLastDamaged(victim);
          a.setAntylogout(20);
          b.setAntylogout(20);
          return;
        }
      }
    }
  }
}
