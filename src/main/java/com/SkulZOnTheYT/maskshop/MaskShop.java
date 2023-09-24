package com.SkulZOnTheYT.maskshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MaskShopPlugin extends JavaPlugin {
    
    private List<Mask> masks = new ArrayList<>();
    
    @Override
    public void onEnable() {
        
        masks.add(new Mask("Masker Kain", 5000, MaskType.CLOTH));
        masks.add(new Mask("Masker Medis", 10000, MaskType.MEDICAL));
        
        getCommand("maskshop").setExecutor(new MaskShopCommand());
        
        getServer().getPluginManager().registerEvents(new MaskShopListener(), this);
    }
    
    public List<Mask> getMasks() {
        return masks;
    }
    
    public class Mask {
        private String name;
        private int price;
        private MaskType type;
        
        public Mask(String name, int price, MaskType type) {
            this.name = name;
            this.price = price;
            this.type = type;
        }
        
        public String getName() {
            return name;
        }
        
        public int getPrice() {
            return price;
        }
        
        public MaskType getType() {
            return type;
        }
    }
    
    public enum MaskType {
        CLOTH,
        MEDICAL
    }
    
    public class MaskShopCommand implements org.bukkit.command.CommandExecutor {
        @Override
        public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                
                Inventory inventory = Bukkit.createInventory(null, 9, "Mask Shop");
                for (Mask mask : masks) {
                    ItemStack item = new ItemStack(Material.PAPER);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(mask.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add("Harga: " + mask.getPrice());
                    lore.add("Tipe: " + mask.getType().toString());
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    inventory.addItem(item);
                }
                
                
                player.openInventory(inventory);
            }
            return true;
        }
    }
    
    public class MaskShopListener implements org.bukkit.event.Listener {
        @org.bukkit.event.EventHandler
        public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
            if (event.getView().getTitle().equals("Mask Shop")) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null) {
                    ItemStack item = event.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    String name = meta.getDisplayName();
                    for (Mask mask : masks) {
                        if (mask.getName().equals(name)) {
                            Player player = (Player) event.getWhoClicked();
                            if (player.getInventory().firstEmpty() != -1) {
                                if (player.getBalance() >= mask.getPrice()) {
                                    player.getInventory().addItem(item);
                                    player.sendMessage("Anda telah membeli " + mask.getName());
                                    player.sendMessage("Uang Anda sekarang: " + (player.getBalance() - mask.getPrice()));
                                    player.setBalance(player.getBalance() - mask.getPrice());
                                } else {
                                    player.sendMessage("Uang Anda tidak cukup untuk membeli " + mask.getName());
                                }
                            } else {
                                player.sendMessage("Inventory Anda penuh");
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
