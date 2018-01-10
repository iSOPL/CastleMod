package pl.inder00.rihc.castlemod.game.files;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import pl.inder00.rihc.castlemod.modes.Arena;




public class Human
{
  public static List<Human> users = new ArrayList();
  
  private String name;
  
  private int coins;
  private int kills;
  private int deaths;
  private int wins;
  private int lose;
  private int pkt;
  private ScoreboardManager scoreboardmanager;
  private Objective scoreboardObjective;
  private Scoreboard scoreboard;
  private boolean perla;
  private boolean koks;
  private boolean reffil;
  private boolean sila3min;
  private boolean ogien6min;
  private boolean speed1_2min15sec;
  private boolean instantheal;
  private int antylogout = 0;
  
  private Arena arena;
  private int team = 0;
  
  private Player lastDamaged;
  private boolean voted;
  
  public Human(String name, int coins, int kills, int deaths, int wins, int lose, int pkt, boolean perla, boolean koks, boolean reffil, boolean sila3min, boolean ogien6min, boolean speed1_2min15sec, boolean instantheal)
  {
    this.name = name;
    this.coins = coins;
    this.kills = kills;
    this.deaths = deaths;
    this.wins = wins;
    this.lose = lose;
    this.pkt = pkt;
    scoreboardmanager = Bukkit.getScoreboardManager();
    scoreboard = scoreboardmanager.getNewScoreboard();
    scoreboardObjective = scoreboard.registerNewObjective("sidebar", "dummy");
    this.perla = perla;
    this.koks = koks;
    this.reffil = reffil;
    this.sila3min = sila3min;
    this.ogien6min = ogien6min;
    this.speed1_2min15sec = speed1_2min15sec;
    this.instantheal = instantheal;
    voted = false;
    users.add(this);
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getKills() {
    return kills;
  }
  
  public void setKills(int kills) {
    this.kills = kills;
  }
  
  public int getCoins() {
    return coins;
  }
  
  public void setCoins(int coins) {
    this.coins = coins;
  }
  
  public int getDeaths() {
    return deaths;
  }
  
  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }
  
  public int getWins() {
    return wins;
  }
  
  public void setWins(int wins) {
    this.wins = wins;
  }
  
  public int getLose() {
    return lose;
  }
  

  public void setLose(int lose) { this.lose = lose; }
  
  public static Human get(String name) {
    for (Human e : users) {
      if (e.getName().equalsIgnoreCase(name)) {
        return e;
      }
    }
    return null;
  }
  
  public int getPkt() {
    return pkt;
  }
  
  public void setPkt(int pkt) {
    this.pkt = pkt;
  }
  
  public ScoreboardManager getScoreboardManager() {
    return scoreboardmanager;
  }
  
  public void setScoreboardManager(ScoreboardManager sb) {
    scoreboardmanager = sb;
  }
  
  public Objective getScoreboardObjective() {
    return scoreboardObjective;
  }
  
  public void setScoreboardObjective(Objective scoreboardObjective) {
    this.scoreboardObjective = scoreboardObjective;
  }
  
  public Scoreboard getScoreboard() { return scoreboard; }
  
  public void setScoreboard(Scoreboard scoreboard) {
    this.scoreboard = scoreboard;
  }
  
  public boolean isPerla() {
    return perla;
  }
  
  public void setPerla(boolean perla) {
    this.perla = perla;
  }
  
  public boolean isKoks() {
    return koks;
  }
  
  public void setKoks(boolean koks) {
    this.koks = koks;
  }
  
  public boolean isReffil() {
    return reffil;
  }
  
  public void setReffil(boolean reffil) {
    this.reffil = reffil;
  }
  
  public boolean isSila3min() {
    return sila3min;
  }
  
  public void setSila3min(boolean sila3min) {
    this.sila3min = sila3min;
  }
  
  public boolean isOgien6min() {
    return ogien6min;
  }
  
  public void setOgien6min(boolean ogien6min) {
    this.ogien6min = ogien6min;
  }
  
  public boolean isSpeed1_2min15sec() {
    return speed1_2min15sec;
  }
  
  public void setSpeed1_2min15sec(boolean speed1_2min15sec) {
    this.speed1_2min15sec = speed1_2min15sec;
  }
  
  public boolean isInstantheal() {
    return instantheal;
  }
  
  public void setInstantheal(boolean instantheal) {
    this.instantheal = instantheal;
  }
  
  public int getAntylogout() {
    return antylogout;
  }
  
  public void setAntylogout(int antylogout) {
    this.antylogout = antylogout;
  }
  
  public Arena getArena() {
    return arena;
  }
  
  public void setArena(Arena arena) {
    this.arena = arena;
  }
  
  public int getTeam() {
    return team;
  }
  
  public void setTeam(int team) {
    this.team = team;
  }
  
  public Player getLastDamaged() {
    return lastDamaged;
  }
  
  public void setLastDamaged(Player lastDamaged) {
    this.lastDamaged = lastDamaged;
  }
  
  public boolean isVoted() {
    return voted;
  }
  
  public void setVoted(boolean voted) {
    this.voted = voted;
  }
}
