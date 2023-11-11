package benlukka.servermon.commands;

import benlukka.servermon.Servermon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class homeCommand implements CommandExecutor {
    FileConfiguration config = Servermon.getPlugin(Servermon.class).getConfig();
    private int time = 5;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory inv = Bukkit.getServer().createInventory(null, 27, "Bank");
            inv.setItem(12, new ItemStack(Material.GREEN_CONCRETE));
            inv.setItem(15, new ItemStack(Material.RED_CONCRETE));
            player.openInventory(inv);
            if (!player.isInsideVehicle() && !player.isSprinting() && !player.isSneaking() && !player.isFlying() && !player.isInsideVehicle() && !player.isGliding()) {
                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (time == 0) {
                            countdownComplete(player);
                            this.cancel();
                            time = 5;
                        } else if (player.getLocation().getX() == x && player.getLocation().getY() == y && player.getLocation().getZ() == z) {
                            player.sendMessage("Wait for: " + time);
                            Bukkit.getLogger().info("Wait for: " + time + " by " + player.getName());
                            if (!player.isOnline()){
                                this.cancel();
                                time = 5;
                            }
                            time--;
                        } else {
                            player.sendMessage("Move or teleportation was cancelled.");
                            time = 5;
                            this.cancel();
                        }
                        if (!player.isOnline()){
                            this.cancel();
                            time = 5;
                        }

                    }
                }.runTaskTimer(JavaPlugin.getPlugin(Servermon.class), 0, 20); // Replace YourPluginClass with the actual class name of your plugin
            } else {
                player.sendMessage("You cannot go home while moving!");
            }
        }
        return true;
    }
    private void countdownComplete(Player player) {

        World world = player.getWorld();
        double x = config.getDouble(player.getUniqueId() + "x");
        double y = config.getDouble(player.getUniqueId() + "y");
        double z = config.getDouble(player.getUniqueId() + "z");
        Location location = new Location(world, x, y, z);
        player.sendMessage("Teleport");
        player.teleport(location);
    }
}
