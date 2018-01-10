package pl.inder00.rihc.castlemod.game.files;

import org.bukkit.configuration.file.FileConfiguration;
import pl.inder00.rihc.castlemod.CastleMod;









public class Config
{
  private static Config inst;
  public FileConfiguration cfg = CastleMod.getInst().getConfig();
  
  public int coinsKill;
  
  public int coinsWin;
  
  public int startGame;
  
  public int lobbyX;
  
  public int lobbyY;
  
  public int lobbyZ;
  
  public String lobbyWorld;
  public int rankingDeath;
  public int kosztPerla;
  public int kosztKoks;
  public int kosztReffil;
  public int kosztSila3min;
  public int kosztOgien6min;
  public int kosztSpeed;
  public int kosztInstantHeal;
  
  public void reload()
  {
    CastleMod.getInst().reloadConfig();
    cfg = CastleMod.getInst().getConfig();
    load();
  }
  


  public void load()
  {
    coinsKill = cfg.getInt("config.coins.kill");
    coinsWin = cfg.getInt("config.coins.win");
    startGame = cfg.getInt("config.game.start");
    
    lobbyX = cfg.getInt("config.lobby.x");
    lobbyY = cfg.getInt("config.lobby.y");
    lobbyZ = cfg.getInt("config.lobby.z");
    lobbyWorld = cfg.getString("config.lobby.world");
    
    rankingDeath = cfg.getInt("config.ranking.death");
    
    kosztPerla = cfg.getInt("config.stale.perla");
    kosztKoks = cfg.getInt("config.stale.koks");
    kosztReffil = cfg.getInt("config.stale.reffil");
    kosztSila3min = cfg.getInt("config.stale.sila");
    kosztOgien6min = cfg.getInt("config.stale.ogien");
    kosztSpeed = cfg.getInt("config.stale.speed");
    kosztInstantHeal = cfg.getInt("config.stale.instantheal");
  }
  


  public static Config getInst()
  {
    if (inst == null) return new Config();
    return inst;
  }
  
  public Config() { inst = this; }
}
