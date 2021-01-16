package me.miqhtie.deathswap.commands;

import me.miqhtie.commandmanager.utils.SubCommand;
import me.miqhtie.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand extends SubCommand {
    public ReviveCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!sender.hasPermission("deathswap.revive")) {
           sender.sendMessage(ChatColor.RED + "Missing permissions.");
           return true;
        }

        if(args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments.");
            return true;
        }

        Player player = Bukkit.getServer().getPlayer(args[0]);
        if(player == null || !player.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Could not find player with name " + args[0]);
            return true;
        }

        if(Main.instance.playersInGame.contains(player)) {
            sender.sendMessage(ChatColor.RED + "Player already alive.");
            return true;
        }

        Main.instance.playersInGame.add(player);
        player.setGameMode(GameMode.SURVIVAL);
        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " WAS REVIVED.");
        return false;
    }
}
