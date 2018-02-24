package io.github.Bennyboy1695.BetterParkour.Commands;

import io.github.Bennyboy1695.BetterParkour.BetterParkour;
import io.github.Bennyboy1695.BetterParkour.Utils.RefStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * io.github.Bennyboy1695.BetterParkour.Commands was created by Bennyboy1695 on 30/10/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandFakeCP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            BetterParkour.getInstance().setCheckpoint(player, 1);
        } else {
            sender.sendMessage(RefStrings.prefix + ChatColor.RED + "You need to be a player to run this command!");
        }
        return true;
    }
}
