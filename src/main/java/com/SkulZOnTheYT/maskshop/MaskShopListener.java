package com.example.maskshop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MaskShopListener implements Listener {

    private final MaskShopPlugin plugin;

    public MaskShopListener(MaskShopPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Mask Shop")) {
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        Mask mask = plugin.getMask(item);

        if (mask == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (plugin.getEconomy().getBalance(player) < mask.getPrice()) {
            player.sendMessage("You don't have enough money to buy this mask.");
            return;
        }

        plugin.getEconomy().withdrawPlayer(player, mask.getPrice());
        player.getInventory().addItem(mask.getType().getItemStack());
        player.sendMessage("You have bought a " + mask.getName() + " mask for " + mask.getPrice() + " coins.");
    }
}
