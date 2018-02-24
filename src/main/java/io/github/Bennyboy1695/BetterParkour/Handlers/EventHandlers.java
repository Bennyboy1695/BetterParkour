package io.github.Bennyboy1695.BetterParkour.Handlers;

import io.github.Bennyboy1695.BetterParkour.BetterParkour;
import io.github.Bennyboy1695.BetterParkour.Storage.SQLite.Database;
import io.github.Bennyboy1695.BetterParkour.Utils.RefStrings;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * io.github.Bennyboy1695.BetterParkour.Handlers was created by Bennyboy1695 on 19/08/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class EventHandlers implements Listener {

    ItemStack wandItem = this.loreItem(Material.BLAZE_ROD, ChatColor.GOLD + "Return Stick", ChatColor.AQUA + "Use this to return to your last checkpoint!");
    ItemStack adminItem = this.loreItem(Material.BLAZE_ROD, ChatColor.GOLD + "Admin Stick", ChatColor.AQUA + "Use this to return to set the checkpoints!");


    @EventHandler
    public void onIronPlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.IRON_PLATE) {
                Database data = BetterParkour.getInstance().getRDatabase();
                Block plate = event.getClickedBlock();
                Player player = event.getPlayer();
                int playerscheckpoint = BetterParkour.getInstance().getCheckpoint(player);
                int platecheckpoint = BetterParkour.getInstance().getCheckpointFromXYZ(plate.getX(), plate.getY(), plate.getZ());
                //Checkpoints
                if (playerscheckpoint < platecheckpoint) {
                    data.setCheckpoint(player, platecheckpoint);
                    player.sendMessage(RefStrings.prefix + ChatColor.GOLD + "You have activated checkpoint number: " + platecheckpoint);
                }
            }
        }
    }

    @EventHandler
    public void onGoldPlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {
                Player player = event.getPlayer();
                //Finish Plate
                int checkpoint = BetterParkour.getInstance().getCheckpoint(player);
                if (checkpoint <= 999) {
                    player.sendMessage("Finish Reached!");
                }
            }
        }
    }

    @EventHandler
    public void onWoodPlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.WOOD_PLATE) {
                Player player = event.getPlayer();
                //Spawn Plate
            }
        }
    }

    @EventHandler
    public void onStonePlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.STONE_PLATE) {
                Player player = event.getPlayer();
                //Bonus Plate
            }
        }
    }

    @EventHandler
    public void onGoldBottomedPlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.IRON_PLATE) {
                int x = event.getClickedBlock().getLocation().getBlockX();
                int y = event.getClickedBlock().getLocation().getBlockY();
                int z = event.getClickedBlock().getLocation().getBlockX();
                World world = Bukkit.getServer().getWorlds().get(0);
                Block block = world.getBlockAt(x,y - 1, z);
                Player player = event.getPlayer();
                if (block.getType() == Material.GOLD_BLOCK) {

                }
            }
        }
    }

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (event.getItem().equals(wandItem)) {
                //Wand
                int checkpoint = BetterParkour.getInstance().getCheckpoint(player);
                Location moveCheckpoint = new Location(player.getWorld(), BetterParkour.getInstance().getCheckPointX(checkpoint), BetterParkour.getInstance().getCheckPointY(checkpoint), BetterParkour.getInstance().getCheckPointZ(checkpoint));
                try {
                    player.teleport(moveCheckpoint, PlayerTeleportEvent.TeleportCause.valueOf("ParkourWand"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (event.getItem().equals(adminItem)) {
                Block plate = event.getClickedBlock();
                if (plate.getType() == Material.IRON_PLATE || plate.getType() == Material.GOLD_PLATE || plate.getType() == Material.WOOD_PLATE || plate.getType() == Material.STONE_PLATE) {
                    if (plate.getType() == Material.IRON_PLATE) {
                        if (BetterParkour.getInstance().getRowCount() >= 0) {
                            BetterParkour.getInstance().setCheckpointXYZ(BetterParkour.getInstance().getRowCount() + 1, plate.getX(), plate.getY(), plate.getZ());
                        }
                    }
                    if (plate.getType() == Material.GOLD_PLATE) {
                        BetterParkour.getInstance().setCheckpointXYZ(999, plate.getX(), plate.getY(), plate.getZ());
                    }
                    if (plate.getType() == Material.WOOD_PLATE) {
                        BetterParkour.getInstance().setCheckpointXYZ(0, plate.getX(), plate.getY(), plate.getZ());
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "This block is not a pressure plate!");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().contains(wandItem)) {
            player.getInventory().addItem(wandItem);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (event.getItemDrop().getItemStack().equals(wandItem)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "The return wand is not drop-able!");
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getCursor().equals(wandItem)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(ChatColor.RED + "The return wand is not draggable!");
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getItem().equals(wandItem)) {
            event.setCancelled(true);
        }
    }

    public ItemStack loreItem(final ItemStack litem, final String lname, final String lore) {
        final ItemMeta lmeta = litem.getItemMeta();
        lmeta.setDisplayName(lname);
        final List<String> Lore = new ArrayList<String>();
        Lore.add(lore);
        lmeta.setLore((List)Lore);
        litem.setItemMeta(lmeta);
        return litem;
    }

    public ItemStack loreItem(final Material litem, final String lname, final String lore) {
        return this.loreItem(new ItemStack(litem), lname, lore);
    }


}
