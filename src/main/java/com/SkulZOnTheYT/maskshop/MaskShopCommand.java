package com.SkulZOnTheYT.maskshop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaskShopCommand implements CommandExecutor {

    private final MaskShopPlugin plugin;

    public MaskShopCommand(MaskShopPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        
        Inventory inventory = plugin.getServer().createInventory(null, 9, "Mask Shop");

       
        for (Mask mask : plugin.getMasks()) {
            ItemStack item = mask.getType().getItemStack();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(mask.getName());
            item.setItemMeta(meta);
            inventory.addItem(item);
        }

        
        player.openInventory(inventory);

        return true;
    }
}
