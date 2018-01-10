package pl.inder00.rihc.castlemod.modes;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.confuser.barapi.BarAPI;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;
import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.manager.ArenaManager;
import pl.inder00.rihc.castlemod.modes.manager.ScoreBoardManager;








public class Arena
{
  public static List<Arena> arenas = new ArrayList();
  
  private String name;
  
  private String creator;
  private int x1;
  private int z1;
  private int y1 = 0;
  
  private int x2;
  private int z2;
  private int y2 = 255;
  
  private int spawn_1_x;
  
  private int spawn_1_y;
  
  private int spawn_1_z;
  
  private String spawn_1_world;
  
  private int spawn_2_x;
  
  private int spawn_2_y;
  private int spawn_2_z;
  private String spawn_2_world;
  private int map_1_x;
  private int map_1_y;
  private int map_1_z;
  private String map_1_world;
  private int map_2_x;
  private int map_2_y;
  private int map_2_z;
  private String map_2_world;
  private String world;
  private ArrayList<Human> team1 = new ArrayList();
  private ArrayList<Human> team2 = new ArrayList();
  
  private ArenaStatus status;
  
  private int numPlayers;
  
  private ArrayList<Player> users = new ArrayList();
  
  private int taskID;
  
  private int counterToStart;
  
  private int canBuild;
  
  private int attackCastle;
  
  private boolean attackingCastle;
  
  private Player attackerCastle;
  private int attack_1_x;
  private int attack_1_y;
  private int attack_1_z;
  private int attack_2_x;
  private int attack_2_y;
  private int attack_2_z;
  private int tntExplode = 0;
  
  private int time = 900;
  
  private boolean castleCuboid = true;
  private int votes;
  
  public Arena(String arena, String creator, String world, int x1, int z1, int x2, int z2, int spawn_1_x, int spawn_1_z, int spawn_1_y, String spawn_1_world, int spawn_2_x, int spawn_2_z, int spawn_2_y, String spawn_2_world, int map_1_x, int map_1_y, int map_1_z, String map_1_world, int map_2_x, int map_2_y, int map_2_z, String map_2_world, int attack_1_x, int attack_1_y, int attack_1_z, int attack_2_x, int attack_2_y, int attack_2_z)
  {
    name = arena;
    this.creator = creator;
    this.x1 = x1;
    this.z1 = z1;
    this.x2 = x2;
    this.z2 = z2;
    numPlayers = 0;
    status = ArenaStatus.WAITING;
    this.spawn_1_world = spawn_1_world;
    this.spawn_2_world = spawn_2_world;
    this.spawn_1_x = spawn_1_x;
    this.spawn_1_z = spawn_1_z;
    this.spawn_1_y = spawn_1_y;
    this.spawn_2_x = spawn_2_x;
    this.spawn_2_z = spawn_2_z;
    this.spawn_2_y = spawn_2_y;
    this.world = world;
    this.map_1_x = map_1_x;
    this.map_1_y = map_1_y;
    this.map_1_z = map_1_z;
    this.map_1_world = map_1_world;
    this.map_2_x = map_2_x;
    this.map_2_y = map_2_y;
    this.map_2_z = map_2_z;
    this.map_2_world = map_2_world;
    this.attack_1_x = attack_1_x;
    this.attack_1_y = attack_1_y;
    this.attack_1_z = attack_1_z;
    this.attack_2_x = attack_2_x;
    this.attack_2_y = attack_2_y;
    this.attack_2_z = attack_2_z;
    arenas.add(this);
  }
  
  public boolean atArea(Location l, Arena a) { double[] dim = new double[2];
    
    dim[0] = a.getX1();
    dim[1] = a.getX2();
    Arrays.sort(dim);
    if ((l.getX() > dim[1]) || (l.getX() < dim[0])) {
      return false;
    }
    dim[0] = a.getZ1();
    dim[1] = a.getZ2();
    Arrays.sort(dim);
    if ((l.getZ() > dim[1]) || (l.getZ() < dim[0])) { return false;
    }
    return true;
  }
  
  public boolean atMap(Location l, Arena a) {
    double[] dim = new double[2];
    
    dim[0] = a.getMap_1_x();
    dim[1] = a.getMap_2_x();
    Arrays.sort(dim);
    if ((l.getX() > dim[1]) || (l.getX() < dim[0])) { return false;
    }
    dim[0] = a.getMap_1_z();
    dim[1] = a.getMap_2_z();
    Arrays.sort(dim);
    if ((l.getZ() > dim[1]) || (l.getZ() < dim[0])) { return false;
    }
    
    return true;
  }
  
  public boolean inAttackArea(Location l, Arena a) { double[] dim = new double[2];
    
    dim[0] = a.getAttack_1_x();
    dim[1] = a.getAttack_2_x();
    Arrays.sort(dim);
    if ((l.getX() > dim[1]) || (l.getX() < dim[0])) { return false;
    }
    dim[0] = a.getAttack_1_z();
    dim[1] = a.getAttack_2_z();
    Arrays.sort(dim);
    if ((l.getZ() > dim[1]) || (l.getZ() < dim[0])) { return false;
    }
    dim[0] = a.getAttack_1_y();
    dim[1] = a.getAttack_2_y();
    Arrays.sort(dim);
    if ((l.getY() > dim[1]) || (l.getY() < dim[0])) { return false;
    }
    
    return true;
  }
  
  public void stop() { restore(); }
  
  public void timmer()
  {
    final Arena a = this;
    taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(CastleMod.getInst(), new Runnable() {
      public void run() {
        if (getCounterToStart() > -1) {
          if (getCounterToStart() == 0) {
            for (Player p : getUsers()) {
              p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 10.0F);
            }
            
            start();
            setCounterToStart(getCounterToStart() - 1);
            Bukkit.getScheduler().cancelTask(getTaskID());
          } else {
            if ((getNumPlayers() < 2) && (getStatus() == ArenaStatus.STARTING)) {
              for (Player p : getUsers()) {
                p.sendMessage("§6[CastleMod] §7Start zostal zatrzymany poniewaz jest nie wystarczajaca ilosc osob do wystartowania.");
              }
              Bukkit.getScheduler().cancelTask(getTaskID());
              setStatus(ArenaStatus.WAITING);
              setCounterToStart(20);
              return;
            }
            for (Player p : getUsers()) {
              p.sendMessage("§6[CastleMod] §7Arena rozpocznie sie za§6 " + getCounterToStart());
              p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 10.0F);
            }
            setStatus(ArenaStatus.STARTING);
            setCounterToStart(getCounterToStart() - 1);
            ScoreBoardManager.checkPlayers(a);
          }
        }
      }
    }, 0L, 20L);
  }
  
  public void start() { World w = Bukkit.getWorld(spawn_1_world);
    w.setTime(6000L);
    w.setWeatherDuration(0);
    setStatus(ArenaStatus.PLAYING);
    setAttackingCastle(false);
    Location spawn1 = new Location(Bukkit.getWorld(getSpawn_1_world()), getSpawn_1_x(), getSpawn_1_y(), getSpawn_1_z());
    Location spawn2 = new Location(Bukkit.getWorld(getSpawn_2_world()), getSpawn_2_x(), getSpawn_2_y(), getSpawn_2_z());
    for (Player p : users) {
      Human u = Human.get(p.getName());
      Integer temp = Integer.valueOf(Math.min(team1.size(), team2.size()));
      if (temp.intValue() == team1.size()) {
        addTeam1(u);
        p.teleport(spawn1);
        p.sendMessage(" ");
        p.sendMessage("§6[CastleMod] §7Zostales przydzielony do druzynuy §bbroniacych!");
        p.sendMessage(" ");
        u.setTeam(1);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFlying(false);
        p.setAllowFlight(false);
        getHandleinventory.b(new NBTTagList());
        ArenaManager.items(p, 0);
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.setFallDistance(0.0F);
        p.setFireTicks(0);
        
        for (PotionEffect pEffect : p.getActivePotionEffects()) {
          p.removePotionEffect(pEffect.getType());
        }
      } else if (temp.intValue() == team2.size()) {
        addTeam2(u);
        p.teleport(spawn2);
        p.sendMessage(" ");
        p.sendMessage("§6[CastleMod] §7Zostales przydzielony do druzynuy §aatakujacych!");
        p.sendMessage(" ");
        u.setTeam(2);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFlying(false);
        p.setAllowFlight(false);
        getHandleinventory.b(new NBTTagList());
        ArenaManager.items(p, 1);
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.setFallDistance(0.0F);
        p.setFireTicks(0);
        for (PotionEffect pEffect : p.getActivePotionEffects())
          p.removePotionEffect(pEffect.getType());
      }
    }
  }
  
  public void restore() {
    setStatus(ArenaStatus.RESTARTING);
    List<Player> p = users;
    Player player; for (int i = 0; i < p.size(); i++) {
      player = (Player)p.get(i);
      if (BarAPI.hasBar(player)) {
        BarAPI.removeBar(player);
      }
      ArenaManager.leaveGame(player, false);
    }
    for (Entity entity : Bukkit.getWorld(spawn_1_world).getEntities()) {
      Integer minX = Integer.valueOf(Math.min(getMap_1_x(), getMap_2_x()));
      Integer maxX = Integer.valueOf(Math.max(getMap_1_x(), getMap_2_x()));
      Integer minZ = Integer.valueOf(Math.min(getMap_1_z(), getMap_2_z()));
      Integer maxZ = Integer.valueOf(Math.max(getMap_1_z(), getMap_2_z()));
      if (((entity.getLocation().getBlockX() < maxX.intValue()) || (entity.getLocation().getBlockX() > minX.intValue())) && 
        ((entity.getLocation().getBlockY() < 255) || (entity.getLocation().getBlockY() > 0)) && 
        ((entity.getLocation().getBlockZ() < maxZ.intValue()) || (entity.getLocation().getBlockZ() > minZ.intValue())) && 
        (entity.getType() != EntityType.PLAYER)) {
        entity.remove();
      }
    }
    


    File schematic = new File("plugins/WorldEdit/schematics/" + getName().toLowerCase() + ".schematic");
    if (schematic.exists()) {
      try
      {
        EditSession editSession = new EditSession(new BukkitWorld(Bukkit.getWorld(spawn_1_world)), -1);
        editSession.enableQueue();
        
        SchematicFormat format = SchematicFormat.getFormat(schematic);
        CuboidClipboard clipboard = format.load(schematic);
        
        clipboard.paste(editSession, new Vector(getSpawn_1_x(), getSpawn_1_y(), getSpawn_1_z()), false);
        editSession.flushQueue();
      } catch (DataException|IOException ex) {
        ex.printStackTrace();
      } catch (MaxChangedBlocksException ex) {
        ex.printStackTrace();
      }
    }
    
    setCounterToStart(0);
    clearTeam1();
    clearTeam2();
    users.clear();
    setNumPlayers(0);
    setTntExplode(0);
    setTimeGame(900);
    setCastleCuboid(true);
    setVotes(0);
    setStatus(ArenaStatus.WAITING);
    
    Bukkit.getConsoleSender().sendMessage("§a[CastleMod] Zrestartowano mape " + getName());
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getCreator() {
    return creator;
  }
  
  public void setCreator(String creator) {
    this.creator = creator;
  }
  
  public int getX1() {
    return x1;
  }
  
  public void setX1(int x1) {
    this.x1 = x1;
  }
  
  public int getZ1() {
    return z1;
  }
  
  public void setZ1(int z1) {
    this.z1 = z1;
  }
  
  public int getY1() {
    return y1;
  }
  
  public void setY1(int y1) {
    this.y1 = y1;
  }
  
  public int getX2() {
    return x2;
  }
  
  public void setX2(int x2) {
    this.x2 = x2;
  }
  
  public int getZ2() {
    return z2;
  }
  
  public void setZ2(int z2) {
    this.z2 = z2;
  }
  
  public int getY2() {
    return y2;
  }
  
  public void setY2(int y2) {
    this.y2 = y2;
  }
  
  public List<Human> getTeam1() { return team1; }
  
  public List<Human> getTeam2() {
    return team2;
  }
  
  public void addTeam2(Human p) {
    team2.add(p);
  }
  
  public void remTeam2(Human p) { team2.remove(p); }
  
  public void clearTeam2() {
    team2.clear();
  }
  
  public void addTeam1(Human p) { team1.add(p); }
  
  public void remTeam1(Human p) {
    team1.remove(p);
  }
  
  public void clearTeam1() { team1.clear(); }
  
  public static Arena get(String a)
  {
    for (Arena arena : arenas) {
      if (arena.getName().equalsIgnoreCase(a)) {
        return arena;
      }
    }
    return null;
  }
  
  public ArenaStatus getStatus() {
    return status;
  }
  
  public void setStatus(ArenaStatus status) {
    this.status = status;
  }
  
  public int getNumPlayers() {
    return numPlayers;
  }
  
  public void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }
  
  public List<Player> getUsers() {
    return users;
  }
  
  public void remUsers(Player users) {
    this.users.remove(users);
  }
  
  public void addUsers(Player users) { this.users.add(users); }
  
  public int getSpawn_1_x()
  {
    return spawn_1_x;
  }
  
  public void setSpawn_1_x(int spawn_1_x) {
    this.spawn_1_x = spawn_1_x;
  }
  
  public int getSpawn_1_y() {
    return spawn_1_y;
  }
  
  public void setSpawn_1_y(int spawn_1_y) {
    this.spawn_1_y = spawn_1_y;
  }
  
  public int getSpawn_1_z() {
    return spawn_1_z;
  }
  
  public void setSpawn_1_z(int spawn_1_z) {
    this.spawn_1_z = spawn_1_z;
  }
  
  public String getSpawn_1_world() {
    return spawn_1_world;
  }
  
  public void setSpawn_1_world(String spawn_1_world) {
    this.spawn_1_world = spawn_1_world;
  }
  
  public int getSpawn_2_x() {
    return spawn_2_x;
  }
  
  public void setSpawn_2_x(int spawn_2_x) {
    this.spawn_2_x = spawn_2_x;
  }
  
  public int getSpawn_2_y() {
    return spawn_2_y;
  }
  
  public void setSpawn_2_y(int spawn_2_y) {
    this.spawn_2_y = spawn_2_y;
  }
  
  public int getSpawn_2_z() {
    return spawn_2_z;
  }
  
  public void setSpawn_2_z(int spawn_2_z) {
    this.spawn_2_z = spawn_2_z;
  }
  
  public String getSpawn_2_world() {
    return spawn_2_world;
  }
  
  public void setSpawn_2_world(String spawn_2_world) {
    this.spawn_2_world = spawn_2_world;
  }
  
  public void delete() { arenas.remove(this); }
  
  public String getWorld()
  {
    return world;
  }
  
  public void setWorld(String world) {
    this.world = world;
  }
  
  public int getTaskID() {
    return taskID;
  }
  
  public void setTaskID(int taskID) {
    this.taskID = taskID;
  }
  
  public int getCounterToStart() {
    return counterToStart;
  }
  
  public void setCounterToStart(int counterToStart) {
    this.counterToStart = counterToStart;
  }
  
  public void checkGame() {
    final Config cfg = Config.getInst();
    final Arena a = this;
    if ((getStatus() != ArenaStatus.STARTING) && (getStatus() != ArenaStatus.PLAYING) && (getStatus() != ArenaStatus.RESTARTING)) {
      setCounterToStart(20);
    }
    if (getStatus() == ArenaStatus.WAITING) {
      if (getUsers().size() >= startGame) {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(CastleMod.getInst(), new Runnable() {
          public void run() {
            if (getCounterToStart() > -1) {
              if (getCounterToStart() == 0) {
                for (Player p : getUsers()) {
                  p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 10.0F);
                }
                
                start();
                setCounterToStart(getCounterToStart() - 1);
                Bukkit.getScheduler().cancelTask(getTaskID());
              } else {
                if ((getNumPlayers() < cfgstartGame) && (getStatus() == ArenaStatus.STARTING)) {
                  for (Player p : getUsers()) {
                    p.sendMessage("§6[CastleMod] §7Start zostal zatrzymany poniewaz jest nie wystarczajaca ilosc osob do wystartowania.");
                  }
                  Bukkit.getScheduler().cancelTask(getTaskID());
                  setStatus(ArenaStatus.WAITING);
                  setCounterToStart(20);
                  return;
                }
                for (Player p : getUsers()) {
                  p.sendMessage("§6[CastleMod] §7Arena rozpocznie sie za§6 " + getCounterToStart());
                  p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 10.0F);
                }
                setStatus(ArenaStatus.STARTING);
                setCounterToStart(getCounterToStart() - 1);
                ScoreBoardManager.checkPlayers(a);
              }
            }
          }
        }, 0L, 20L);
      }
    } else if (getTimeGame() <= 0) {
      for (Player p : getUsers()) {
        Human e = Human.get(p.getName());
        if (e.getTeam() == 1) {
          if (e != null) {
            e.setCoins(e.getCoins() + coinsWin);
            e.setWins(e.getWins() + 1);
          }
        }
        else if (e != null) {
          e.setLose(e.getLose() + 1);
        }
      }
      
      Bukkit.broadcastMessage("§6[CastleMod] §7Druzyna §6broniacych §7wygrala na arenie §6" + getName());
      restore();
    } else if ((getStatus() == ArenaStatus.PLAYING) && (getUsers().size() != 0)) {
      if (getTeam1().size() == getUsers().size()) {
        for (Player p : getUsers()) {
          Human e = Human.get(p.getName());
          if (e != null) {
            e.setCoins(e.getCoins() + coinsWin);
            e.setWins(e.getWins() + 1);
          }
        }
        Bukkit.broadcastMessage("§6[CastleMod] §7Druzyna §bbroniacych §7wygrala na arenie §6" + getName());
        restore();
      } else if (getTeam2().size() == getUsers().size()) {
        for (Player p : getUsers()) {
          Human e = Human.get(p.getName());
          if (e != null) {
            e.setCoins(e.getCoins() + coinsWin);
            e.setWins(e.getWins() + 1);
          }
        }
        Bukkit.broadcastMessage("§6[CastleMod] §7Druzyna §catakujacych §7wygrala na arenie §6" + getName());
        restore();
      }
    }
  }
  
  public int getMap_1_x() { return map_1_x; }
  
  public void setMap_1_x(int map_1_x) {
    this.map_1_x = map_1_x;
  }
  
  public int getMap_1_y() { return map_1_y; }
  
  public void setMap_1_y(int map_1_y) {
    this.map_1_y = map_1_y;
  }
  
  public int getMap_1_z() { return map_1_z; }
  
  public void setMap_1_z(int map_1_z) {
    this.map_1_z = map_1_z;
  }
  
  public String getMap_1_world() { return map_1_world; }
  
  public void setMap_1_world(String map_1_world) {
    this.map_1_world = map_1_world;
  }
  
  public int getMap_2_x() { return map_2_x; }
  
  public void setMap_2_x(int map_2_x) {
    this.map_2_x = map_2_x;
  }
  
  public int getMap_2_y() { return map_2_y; }
  
  public void setMap_2_y(int map_2_y) {
    this.map_2_y = map_2_y;
  }
  
  public int getMap_2_z() { return map_2_z; }
  
  public void setMap_2_z(int map_2_z) {
    this.map_2_z = map_2_z;
  }
  
  public String getMap_2_world() { return map_2_world; }
  
  public void setMap_2_world(String map_2_world) {
    this.map_2_world = map_2_world;
  }
  
  public int getCanBuild() { return canBuild; }
  
  public void setCanBuild(int canBuild) {
    this.canBuild = canBuild;
  }
  
  public int getAttackCastle() { return attackCastle; }
  
  public void setAttackCastle(int attackCastle) {
    this.attackCastle = attackCastle;
  }
  
  public boolean isAttackingCastle() { return attackingCastle; }
  
  public void setAttackingCastle(boolean attackingCastle) {
    this.attackingCastle = attackingCastle;
  }
  
  public Player getAttackerCastle() { return attackerCastle; }
  
  public void setAttackerCastle(Player attackerCastle) {
    this.attackerCastle = attackerCastle;
  }
  
  public int getAttack_1_x() { return attack_1_x; }
  
  public void setAttack_1_x(int attack_1_x) {
    this.attack_1_x = attack_1_x;
  }
  
  public int getAttack_1_y() { return attack_1_y; }
  
  public void setAttack_1_y(int attack_1_y) {
    this.attack_1_y = attack_1_y;
  }
  
  public int getAttack_1_z() { return attack_1_z; }
  
  public void setAttack_1_z(int attack_1_z) {
    this.attack_1_z = attack_1_z;
  }
  
  public int getAttack_2_x() { return attack_2_x; }
  
  public void setAttack_2_x(int attack_2_x) {
    this.attack_2_x = attack_2_x;
  }
  
  public int getAttack_2_y() { return attack_2_y; }
  
  public void setAttack_2_y(int attack_2_y) {
    this.attack_2_y = attack_2_y;
  }
  
  public int getAttack_2_z() { return attack_2_z; }
  
  public void setAttack_2_z(int attack_2_z) {
    this.attack_2_z = attack_2_z;
  }
  
  public int getTntExplode() { return tntExplode; }
  
  public void setTntExplode(int tntExplode) {
    this.tntExplode = tntExplode;
  }
  
  public int getTimeGame() { return time; }
  
  public void setTimeGame(int time) {
    this.time = time;
  }
  
  public boolean isCastleCuboid() { return castleCuboid; }
  
  public void setCastleCuboid(boolean castleCuboid) {
    this.castleCuboid = castleCuboid;
  }
  
  public int getVotes() { return votes; }
  
  public void setVotes(int votes) {
    this.votes = votes;
  }
}
