package benlukka.servermon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Arrays;

public class ServmonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                createCustomScoreboard(player);
            } else {
                player.sendMessage("You are not an Operator!");
            }
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
        return true;
    }

    private void createCustomScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("stats", "dummy", ChatColor.AQUA + "" + ChatColor.BOLD + "Stats");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score1 = objective.getScore("Server:                ");
        score1.setScore(3);

        Score score2 = objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "McV    " + ChatColor.GREEN + Arrays.toString(Bukkit.getMinecraftVersion().toCharArray()));
        score2.setScore(2);

        Score score3 = objective.getScore(ChatColor.BLUE + "" + ChatColor.BOLD + "Online    " + ChatColor.GREEN + onlinePlayers());
        score3.setScore(1);

        player.setScoreboard(board);
    }

    private int onlinePlayers() {
        return Bukkit.getOnlinePlayers().size();
    }
}
