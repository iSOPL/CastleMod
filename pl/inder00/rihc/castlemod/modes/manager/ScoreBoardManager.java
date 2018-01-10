package pl.inder00.rihc.castlemod.modes.manager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import pl.inder00.rihc.castlemod.game.files.Human;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.Util;

public class ScoreBoardManager
{
  public ScoreBoardManager() {}
  
  static String time = "0:00";
  
  public static void checkPlayers(Arena a) {
    if (a.getStatus() == ArenaStatus.STARTING) {
      time = Util.calculateTime(a.getCounterToStart());
    }
    if (a.getStatus() == ArenaStatus.PLAYING) {
      time = Util.calculateTime(a.getTimeGame());
    }
    for (Player p : a.getUsers()) {
      Human e = Human.get(p.getName());
      Scoreboard board = e.getScoreboard();
      Objective objective = e.getScoreboardObjective();
      objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
      objective.setDisplayName("§9Castlemod §7" + time);
      Score pkt = objective.getScore(ChatColor.GREEN + "Ranking:");
      pkt.setScore(e.getPkt());
      Score kills = objective.getScore(ChatColor.GREEN + "Zabojstwa:");
      kills.setScore(e.getKills());
      Score deaths = objective.getScore(ChatColor.GREEN + "Smierci:");
      deaths.setScore(e.getDeaths());
      Score monety = objective.getScore(ChatColor.GREEN + "Monety:");
      monety.setScore(e.getCoins());
      Score wygrane = objective.getScore(ChatColor.GREEN + "Wygrane:");
      wygrane.setScore(e.getWins());
      Score przegrane = objective.getScore(ChatColor.GREEN + "Przegrane:");
      przegrane.setScore(e.getLose());
      p.setScoreboard(board);
    }
  }
}
