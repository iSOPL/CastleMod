package pl.inder00.rihc.castlemod;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import pl.inder00.rihc.castlemod.game.commands.CastleModCommand;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.FileManager;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.game.files.lodader.ArenaLoader;
import pl.inder00.rihc.castlemod.game.files.lodader.HumanLoader;
import pl.inder00.rihc.castlemod.game.listeners.BlockPlaceListener;
import pl.inder00.rihc.castlemod.game.listeners.EntityDamageByEntityListener;
import pl.inder00.rihc.castlemod.game.listeners.InventoryClickListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerCommandPreprocessListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerDeathListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerDropItemListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerInteractListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerJoinListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerMoveListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerQuitListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerRespawnListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerTeleportListener;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.ArenaManager;
import pl.inder00.rihc.castlemod.modes.manager.ScoreBoardManager;




public class CastleMod
  extends JavaPlugin
{
  private static CastleMod inst;
  public static WorldEditPlugin wep;
  public static WorldEdit we;
  public static Location lobby;
  
  public static CastleMod getInst()
  {
    if (inst == null) return new CastleMod();
    return inst;
  }
  
  public CastleMod() { inst = this; }
  





  public void onDisable()
  {
    for (Player p : ) {
      ArenaManager.leaveGame(p, false);
    }
    ArenaLoader.saveArenas();
    HumanLoader.savePlayers();
  }
  


  public void onEnable()
  {
    Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
    if ((plugin == null) || (!(plugin instanceof WorldEditPlugin))) {
      Bukkit.getConsoleSender().sendMessage("§4[CastleMod] Zainstaluj plugin WorldEdit, aby castlemod dzialal w pelni!");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }
    wep = (WorldEditPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    we = wep.getWorldEdit();
    
    FileManager.check();
    new CastleModCommand(this);
    Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
    Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
    Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
    
    final Config cfg = Config.getInst();
    cfg.load();
    
    ArenaLoader.loadArenas();
    HumanLoader.loadPlayers();
    
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (Human.get(p.getName()) == null) {
        new Human(p.getName(), 0, 0, 0, 0, 0, 1000, false, false, false, false, false, false, false);
      }
    }
    for (Arena a : Arena.arenas) {
      a.restore();
    }
    
    lobby = new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ);
    getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      public void run() {
        for (Human e : Human.users) {
          if (e.getAntylogout() > 0) {
            e.setAntylogout(e.getAntylogout() - 1);
          } else if ((e != null) && (e.getLastDamaged() != null) && (e.getAntylogout() == 0)) {
            e.setLastDamaged(null);
          }
        }
        for (Arena a : Arena.arenas) {
          a.checkGame();
          if ((a.getStatus() == ArenaStatus.WAITING) && (a.getNumPlayers() >= 2)) {
            int percent = (int)(a.getVotes() * 100.0F / a.getNumPlayers());
            if (percent >= 75) {
              a.timmer();
            }
          }
          if ((a.getStatus() == ArenaStatus.PLAYING) || (a.getStatus() == ArenaStatus.WAITING)) {
            ScoreBoardManager.checkPlayers(a);
          }
          if (a.getStatus() == ArenaStatus.PLAYING) {
            if (a.getTntExplode() > 0) {
              a.setTntExplode(a.getTntExplode() - 1);
            }
            if (a.getTimeGame() == 900) {
              for (Player p : a.getUsers()) {
                p.sendMessage("§6[CastleMod] §7Przedmioty stale zostana dodane za §62:00");
              }
            }
            if (a.getTimeGame() == 840) {
              for (Player p : a.getUsers()) {
                p.sendMessage("§6[CastleMod] §7Przedmioty stale zostana dodane za §61:00");
              }
            }
            if (a.getTimeGame() == 810) {
              for (Player p : a.getUsers())
                p.sendMessage("§6[CastleMod] §7Przedmioty stale zostana dodane za §60:30");
            }
            Human e;
            if (a.getTimeGame() == 780) {
              for (Player p : a.getUsers()) {
                p.sendMessage("§6[CastleMod] §7Przedmioty stale zostaly §6dodane§7!");
                e = Human.get(p.getName());
                if (e != null) {
                  if (e.isPerla()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ENDER_PEARL, 1) });
                  if (e.isKoks()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLDEN_APPLE, 1, 1) });
                  if (e.isReffil()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLDEN_APPLE, 1) });
                  if (e.isSila3min()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, 8201) });
                  if (e.isOgien6min()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, 16451) });
                  if (e.isSpeed1_2min15sec()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, 16386) });
                  if (e.isInstantheal()) p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, 16453) });
                }
              }
            }
            a.setTimeGame(a.getTimeGame() - 1);
            if (a.getTimeGame() == 600) {
              for (Player p : a.getUsers()) {
                p.sendMessage("§6[CastleMod] §7Do konca gry pozostalo §610:00");
              }
            }
            if (a.getTimeGame() == 300) {
              for (Player p : a.getUsers()) {
                p.sendMessage("§6[CastleMod] §7Do konca gry pozostalo §65:00");
              }
            }
            if (a.getTimeGame() == 60) {
              for (Player p : a.getUsers()) {
                p.sendMessage("§6[CastleMod] §7Do konca gry pozostalo §61:00");
              }
            }
            if (a.isAttackingCastle()) {
              if (Bukkit.getServer().getPlayerExact(a.getAttackerCastle().getName()) == null) {
                a.setAttackingCastle(false);
              } else {
                Human p = Human.get(a.getAttackerCastle().getName());
                if (p != null) {
                  if (p.getAntylogout() != 0) {
                    a.setAttackingCastle(false);
                    a.getAttackerCastle().sendMessage("§cPrzejmowanie anulowane, zostales/as zaatakowany/a!");
                    for (Player player : a.getUsers()) {
                      if (BarAPI.hasBar(player)) {
                        BarAPI.removeBar(player);
                        BarAPI.setMessage(player, "§8-------- §7Przejmowanie anulowane §8--------", 3);
                      }
                    }
                  } else {
                    Location to = a.getAttackerCastle().getLocation();
                    if (a.inAttackArea(to, a)) {
                      a.setAttackCastle(a.getAttackCastle() + 1);
                      for (Player player : a.getUsers()) {
                        BarAPI.setMessage(player, "§8-------- §7Przejmowanie §6" + a.getAttackCastle() + " §8--------", 100.0F);
                      }
                      if ((a.getAttackCastle() == 85) && (a.isCastleCuboid())) {
                        for (Player player : a.getUsers()) {
                          player.sendMessage("§c[CastleMod] Cuboid zamku zostal usuniety!");
                          player.sendMessage("§c[CastleMod] Cuboid zamku zostal usuniety!");
                          player.sendMessage("§c[CastleMod] Cuboid zamku zostal usuniety!");
                        }
                        a.setCastleCuboid(false);
                      }
                      if (a.getAttackCastle() == 100) {
                        for (Human t2 : a.getTeam2()) {
                          Human.get(t2.getName()).setCoins(cfgcoinsWin);
                        }
                        Bukkit.broadcastMessage("§6[CastleMod] §7Druzyna §6atakujacych §7wygrala na arenie §6" + a.getName());
                        
                        a.restore();
                      }
                    } else {
                      a.setAttackingCastle(false);
                      a.getAttackerCastle().sendMessage("§cPrzejmowanie anulowane, ruszyles/as sie!");
                      for (Player player : a.getUsers()) {
                        if (BarAPI.hasBar(player)) {
                          BarAPI.removeBar(player);
                          BarAPI.setMessage(player, "§8-------- §7Przejmowanie anulowane §8--------", 3);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }, 0L, 20L);
  }
}
