package io.github.Bennyboy1695.BetterParkour.Commands;

import io.github.Bennyboy1695.BetterParkour.BetterParkour;
import io.github.Bennyboy1695.BetterParkour.Handlers.EventHandlers;
import io.github.Bennyboy1695.BetterParkour.Utils.RefStrings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * io.github.Bennyboy1695.BetterParkour.Commands was created by Bennyboy1695 on 29/10/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandWand implements CommandExecutor {

    EventHandlers eventHandlers = new EventHandlers();

    ItemStack wandItem = eventHandlers.loreItem(Material.BLAZE_ROD, ChatColor.GOLD + "Return Stick", ChatColor.AQUA + "Use this to return to your last checkpoint!");
    ItemStack adminItem = eventHandlers.loreItem(Material.BLAZE_ROD, ChatColor.GOLD + "Admin Stick", ChatColor.AQUA + "Use this to return to set the checkpoints!");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args[0].equals("admin")) {
                try {
                    player.getInventory().addItem(adminItem);
                    BetterParkour.getInstance().getLogs().info("Given " + player.getName() + " the admin wand!");
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(RefStrings.prefix + ChatColor.RED + "We were unable to give you the admin wand due to an error, see console for more info!");
                    BetterParkour.getInstance().getLogs().info("Failed to give " + player.getName() + " the admin wand!");
                }
            } else {
                try {
                    player.getInventory().addItem(wandItem);
                    BetterParkour.getInstance().getLogs().info("Given " + player.getName() + " the Return wand!");
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(RefStrings.prefix + ChatColor.RED + "We were unable to give you the Return wand due to an error, see console for more info!");
                    BetterParkour.getInstance().getLogs().info("Failed to give " + player.getName() + " the Return wand!");
                }
            }
        } else {
            sender.sendMessage(RefStrings.prefix + ChatColor.RED + "You need to be a player to run this command!");
        }
        return true;
    }
}
