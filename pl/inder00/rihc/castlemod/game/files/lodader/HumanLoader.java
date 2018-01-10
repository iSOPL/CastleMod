package pl.inder00.rihc.castlemod.game.files.lodader;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.inder00.rihc.castlemod.game.files.FileManager;
import pl.inder00.rihc.castlemod.game.files.Human;




public class HumanLoader
{
  public HumanLoader() {}
  
  public static void loadPlayers()
  {
    for (File f : FileManager.getUsersFolder().listFiles()) {
      YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
      String name = yml.getString("player.name");
      int kills = yml.getInt("player.kills");
      int deaths = yml.getInt("player.deaths");
      int coins = yml.getInt("player.coins");
      int wins = yml.getInt("player.wins");
      int lose = yml.getInt("player.lose");
      int pkt = yml.getInt("player.pkt");
      boolean perla = yml.getBoolean("player.stale.perla");
      boolean koks = yml.getBoolean("player.stale.koks");
      boolean reffil = yml.getBoolean("player.stale.reffil");
      boolean sila3min = yml.getBoolean("player.stale.sila3min");
      boolean ogien6min = yml.getBoolean("player.stale.ogien6min");
      boolean speed2min15sec = yml.getBoolean("player.stale.speed");
      boolean instantheal = yml.getBoolean("player.stale.instantheal");
      new Human(name, coins, kills, deaths, wins, lose, pkt, perla, koks, reffil, sila3min, ogien6min, speed2min15sec, instantheal);
    }
  }
  
  public static void savePlayers() {
    for (Human a : Human.users) {
      File f = new File(FileManager.getUsersFolder(), a.getName() + ".yml");
      if (!f.exists()) {
        try {
          f.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
      yml.set("player.name", a.getName());
      yml.set("player.coins", Integer.valueOf(a.getCoins()));
      yml.set("player.kills", Integer.valueOf(a.getKills()));
      yml.set("player.deaths", Integer.valueOf(a.getDeaths()));
      yml.set("player.wins", Integer.valueOf(a.getWins()));
      yml.set("player.lose", Integer.valueOf(a.getLose()));
      yml.set("player.pkt", Integer.valueOf(a.getPkt()));
      yml.set("player.stale.perla", Boolean.valueOf(a.isPerla()));
      yml.set("player.stale.koks", Boolean.valueOf(a.isKoks()));
      yml.set("player.stale.reffil", Boolean.valueOf(a.isReffil()));
      yml.set("player.stale.sila3min", Boolean.valueOf(a.isSila3min()));
      yml.set("player.stale.ogien6min", Boolean.valueOf(a.isOgien6min()));
      yml.set("player.stale.speed", Boolean.valueOf(a.isSpeed1_2min15sec()));
      yml.set("player.stale.instantheal", Boolean.valueOf(a.isInstantheal()));
      try {
        yml.save(f);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
