package me.miqhtie.deathswap.commands;

import me.miqhtie.commandmanager.utils.SubCommand;
import me.miqhtie.deathswap.Main;
import me.miqhtie.deathswap.tasks.StartTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndCommand extends SubCommand {
    public EndCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!sender.hasPermission("deathswap.end")) {
            sender.sendMessage(ChatColor.RED + "Missing permissions");
            return true;
        }

        if(Main.instance.canBeStarted()) {
            sender.sendMessage(ChatColor.RED + "The game can not be ended now.");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "Ending...");
        if(Main.instance.startTask != null) {
            Main.instance.startTask.cancel();
            Main.instance.startTask = null;
        }

        if(Main.instance.swapTask != null) {
            Main.instance.swapTask.cancel();
            Main.instance.swapTask = null;
        }

        Main.instance.running = false;

        for(Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(p.getWorld().getSpawnLocation());
            p.setGameMode(GameMode.SURVIVAL);
            p.sendTitle(ChatColor.RED + "Game Over.", "", 0, 60, 5);
            p.getInventory().clear();
        }



        return false;
    }
}
