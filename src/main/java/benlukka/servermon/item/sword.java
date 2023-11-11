package benlukka.servermon.item;

import benlukka.servermon.Servermon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class sword implements Listener {

    public static void sword() {
        ShapedRecipe customRecipe = new ShapedRecipe(new NamespacedKey(Servermon.getPlugin(Servermon.class), "custom_sword"), createCustomItem());
        customRecipe.shape(" S ", " S ", " S ");
        customRecipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(customRecipe);
        Bukkit.getLogger().info("Sword added!");
    }


    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if (result.isSimilar(createCustomItem())) {
            Bukkit.getLogger().info("sword crafted");
            event.setCurrentItem(createCustomItem());
        }
    }



     static ItemStack createCustomItem() {
        ItemStack customItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = customItem.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Servermon.getPlugin(Servermon.class), "isOnlinebank"), PersistentDataType.STRING, "myCustomValue");
        meta.setDisplayName(ChatColor.GOLD + "Bank");
        meta.setLore(Arrays.asList("Powerful sword created by me!"));
        customItem.setItemMeta(meta);

        return customItem;
    }
}