package pl.inder00.rihc.castlemod.game.listeners;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.PlayerInventory;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.ScoreboardManager;
import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerDeathListener implements org.bukkit.event.Listener
{
  public PlayerDeathListener() {}
  
  @EventHandler
  public void onDeath(PlayerDeathEvent e)
  {
    Player p = e.getEntity();
    Human b = Human.get(p.getName());
    if (b.getArena() != null) {
      Arena a = b.getArena();
      if ((a != null) && (a.getStatus() == ArenaStatus.PLAYING)) {
        Config cfg = Config.getInst();
        b.setDeaths(b.getDeaths() + 1);
        b.setPkt(b.getPkt() - rankingDeath);
        b.setLose(b.getLose() + 1);
        b.setArena(null);
        e.setDeathMessage(null);
        for (ItemStack s : e.getDrops()) {
          p.getWorld().dropItemNaturally(p.getLocation(), s);
        }
        e.getDrops().clear();
        
        if (b.getLastDamaged() == null) {
          for (Player plls : a.getUsers()) {
            plls.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7znigal");
          }
        } else {
          Human c = Human.get(b.getLastDamaged().getName());
          c.setKills(c.getKills() + 1);
          c.setCoins(c.getCoins() + coinsKill);
          c.setPkt(c.getPkt() + rankingDeath);
          for (Player plls : a.getUsers()) {
            plls.sendMessage("§6[CastleMod] §7Gracz §6" + p.getName() + " §7zostal zabity przez §6" + b.getLastDamaged().getName() + " §7(§6+" + rankingDeath + "§7)");
          }
        }
        a.remUsers(p);
        b.setVoted(false);
        if (b.getTeam() == 1) {
          a.remTeam1(b);
        } else {
          a.remTeam2(b);
        }
        getHandleinventory.b(new NBTTagList());
        p.updateInventory();
        p.setScoreboard(org.bukkit.Bukkit.getScoreboardManager().getNewScoreboard());
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.setFallDistance(0.0F);
        p.setFireTicks(0);
        p.setLevel(0);
        

        PacketPlayInClientCommand in = new PacketPlayInClientCommand(net.minecraft.server.v1_7_R4.EnumClientCommand.PERFORM_RESPAWN);
        EntityPlayer cPlayer = ((CraftPlayer)p).getHandle();
        playerConnection.a(in);
        
        p.teleport(CastleMod.lobby);
        return;
      }
    }
  }
}
