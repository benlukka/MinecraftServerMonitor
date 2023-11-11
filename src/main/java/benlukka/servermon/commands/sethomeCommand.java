package benlukka.servermon.commands;

import benlukka.servermon.Servermon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class sethomeCommand implements CommandExecutor {
    FileConfiguration config = Servermon.getPlugin(Servermon.class).getConfig();



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
       World world = player.getWorld();

        config.set(player.getUniqueId() + "x", x);
        config.set(player.getUniqueId() + "y", y);
        config.set(player.getUniqueId() + "z", z);
        config.set(player.getUniqueId() + "w", world.getName());
        Servermon.getPlugin(Servermon.class).saveConfig();
        player.sendMessage("Home is set at:" + ChatColor.GREEN + " x: " + x + ChatColor.BLUE  + " y: " + y + ChatColor.RED  + " z: " + z +  ChatColor.AQUA + " World: " + world.getName());
        return false;
    }



}

