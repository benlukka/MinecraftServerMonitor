package benlukka.servermon.item;


import benlukka.servermon.Servermon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import static benlukka.servermon.item.sword.createCustomItem;

public class ItemEventhandler implements Listener {
    static FileConfiguration config = Servermon.getPlugin(Servermon.class).getConfig();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome to the server, " + event.getPlayer().getName() + "!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        //Bank
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Player right-clicked
            if (item != null && isCustomSword(item)) {
                int count = 0;
                boolean did = false;
                for (ItemStack emeralds : event.getPlayer().getInventory().getContents()) {
                    if (emeralds != null && emeralds.getType() == Material.EMERALD) {
                        count += emeralds.getAmount();
                    }
                }
                if (!did){
                   int b = config.getInt(event.getPlayer().getUniqueId() + "bank");
                    config.set(event.getPlayer().getUniqueId() + "bank", count + b);
                    Servermon.getPlugin(Servermon.class).saveConfig();
                    event.getPlayer().getInventory().remove(Material.EMERALD);
                }
                Inventory inv = Bukkit.getServer().createInventory(null, 27, "Bank");

                ItemStack payinMenue = getpayinmenue(event);
                inv.setItem(11, payinMenue);

                ItemStack payoutMenue = getpayoutmenue(event);
                inv.setItem(13, payoutMenue);

                ItemStack payhistory = gatpayhistory(event);
                inv.setItem(15, payhistory);
                event.getPlayer().openInventory(inv);
            }
        }


        //Bank payin gui
        if (item != null && isCustomPayInSign(item)) {
            // Open your custom inventory
            Player player = event.getPlayer();
            Inventory customInventory = payingui(event);
            player.openInventory(customInventory);

            // Optionally, you can cancel the event to prevent the item from being used for its default action
            event.setCancelled(true);
        }
    }

    private boolean isCustomPayInSign(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(Servermon.getPlugin(Servermon.class), "ispayinsign"), PersistentDataType.STRING);
    }

    @NotNull
    private static Inventory payingui(PlayerInteractEvent event) {
        Inventory payinguiInventory = Bukkit.getServer().createInventory(null, 27, ChatColor.GREEN + "Payin");
        ItemStack payingui = new ItemStack(Material.CHEST);
        ItemMeta payinguiItemMeta = payingui.getItemMeta();
        payinguiItemMeta.getPersistentDataContainer().set(new NamespacedKey(Servermon.getPlugin(Servermon.class), "ispayinsign"), PersistentDataType.STRING, "myCustomValue");
        payinguiItemMeta.setDisplayName(ChatColor.GOLD +"Pay History.");
        ArrayList payhistorylore = new ArrayList<>();
        payhistorylore.add("");
        payhistorylore.add(ChatColor.AQUA + "In Bank: " + ChatColor.GOLD + config.getInt(event.getPlayer().getUniqueId() + "bank"));
        payhistorylore.add(ChatColor.GRAY + "This is a special item with a custom description.");
        payhistorylore.add(ChatColor.GREEN + "Use it wisely!");
        payinguiItemMeta.setLore(payhistorylore);
        payingui.setItemMeta(payinguiItemMeta);
        payinguiInventory.setItem(11, payingui);
        return payinguiInventory;
    }

    @NotNull
    private static ItemStack gatpayhistory(PlayerInteractEvent event) {
        ItemStack payhistory = new ItemStack(Material.CHEST);
        ItemMeta payhistorymeta = payhistory.getItemMeta();
        payhistorymeta.setDisplayName(ChatColor.GOLD +"Pay History.");
        ArrayList payhistorylore = new ArrayList<>();
        payhistorylore.add("");
        payhistorylore.add(ChatColor.AQUA + "In Bank: " + ChatColor.GOLD + config.getInt(event.getPlayer().getUniqueId() + "bank"));
        payhistorylore.add(ChatColor.GRAY + "This is a special item with a custom description.");
        payhistorylore.add(ChatColor.GREEN + "Use it wisely!");
        payhistorymeta.setLore(payhistorylore);
        payhistory.setItemMeta(payhistorymeta);
        return payhistory;
    }
    @NotNull
    private static ItemStack getpayoutmenue(PlayerInteractEvent event) {
        ItemStack payoutmenue = new ItemStack(Material.DISPENSER);
        ItemMeta payoutmeta = payoutmenue.getItemMeta();
        payoutmeta.setDisplayName(ChatColor.RED +"Pay out your Emeralds.");
        ArrayList payoutlore = new ArrayList<>();
        payoutlore.add("");
        payoutlore.add(ChatColor.AQUA + "In Bank: " + ChatColor.GOLD + config.getInt(event.getPlayer().getUniqueId() + "bank"));
        payoutlore.add(ChatColor.GRAY + "This is a special item with a custom description.");
        payoutlore.add(ChatColor.GREEN + "Use it wisely!");
        payoutmeta.setLore(payoutlore);
        payoutmenue.setItemMeta(payoutmeta);
        return payoutmenue;
    }
    @NotNull
    private static ItemStack getpayinmenue(PlayerInteractEvent event) {
        ItemStack payinmenue = new ItemStack(Material.CHEST);
        ItemMeta payinmeta = payinmenue.getItemMeta();
        payinmeta.setDisplayName(ChatColor.GREEN +"Pay in your Emeralds.");
        ArrayList payoutlore = new ArrayList<>();
        payoutlore.add("");
        payoutlore.add(ChatColor.AQUA + "In Bank: " + ChatColor.GOLD + config.getInt(event.getPlayer().getUniqueId() + "bank"));
        payoutlore.add(ChatColor.GRAY + "This is a special item with a custom description.");
        payoutlore.add(ChatColor.GREEN + "Use it wisely!");
        payinmeta.setLore(payoutlore);
        payinmenue.setItemMeta(payinmeta);
        return payinmenue;
    }



    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            Bukkit.getLogger().info("Villager call.");
            Villager villager = (Villager) event.getRightClicked();

            if (villager.getProfession() == Villager.Profession.TOOLSMITH &&villager.getVillagerLevel() > 4) {

                    ItemStack tradeItem1 = new ItemStack(Material.EMERALD, 64);
                    ItemStack resultItem = new ItemStack(createCustomItem());

                    MerchantRecipe customRecipe = new MerchantRecipe(resultItem, 3); // Trade 5 times
                    customRecipe.addIngredient(tradeItem1);

                    // Retrieve existing master level recipes
                    List<MerchantRecipe> masterRecipes = new ArrayList<>(villager.getRecipes());

                    // Check if the custom recipe is not already present
                    if (!isCustomRecipeAlreadyPresent(masterRecipes, customRecipe)) {
                        // Add your custom recipe to the master level
                        masterRecipes.add(customRecipe);

                        // Add back modified recipes to the villager
                        villager.setRecipes(masterRecipes);
                    }

            }
        }
    }

    private boolean isCustomRecipeAlreadyPresent(List<MerchantRecipe> recipes, MerchantRecipe customRecipe) {
        for (MerchantRecipe recipe : recipes) {
            if (recipe.getResult().equals(customRecipe.getResult())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCustomSword(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(Servermon.getPlugin(Servermon.class), "isOnlinebank"), PersistentDataType.STRING);
    }


}
