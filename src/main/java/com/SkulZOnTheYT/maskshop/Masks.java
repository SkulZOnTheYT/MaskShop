package com.SkulZOnTheYT.maskshop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Masks {
  
  public void giveMask(Player player, Mask mask){
       ItemStack item;
        if (mask.getType() == MaskType.PLAYER) {
            item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(player);
            meta.setDisplayName(mask.getName());
            item.setItemMeta(meta);
        } else {
            switch (mask.getType()) {
                case SKELETON:
                    item = new ItemStack(Material.SKELETON_SKULL);
                    break;
                case WITHER:
                    item = new ItemStack(Material.WITHER_SKELETON_SKULL);
                    break;
                case ZOMBIE:
                    item = new ItemStack(Material.ZOMBIE_HEAD);
                    break;
                case PIGLIN:
                    item = new ItemStack(Material.PIGLIN_HEAD);
                    break;
                case DRAGON:
                    item = new ItemStack(Material.DRAGON_HEAD);
                    break;
                case CREEPER:
                    item = new ItemStack(Material.CREEPER_HEAD);
                    break;
                default:
                    item = new ItemStack(Material.AIR);
                    break;
            }
            item.getItemMeta().setDisplayName(mask.getName());
        }
        player.getInventory().addItem(item);
     }
}   
