package me.miqhtie.deathswap.commands;

import me.miqhtie.commandmanager.utils.SubCommand;
import me.miqhtie.deathswap.Main;
import me.miqhtie.deathswap.tasks.StartTask;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand extends SubCommand {
    public StartCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!sender.hasPermission("deathswap.start")) {
            sender.sendMessage(ChatColor.RED + "Missing permissions");
            return true;
        }

        if(!Main.instance.canBeStarted()) {
            sender.sendMessage(ChatColor.RED + "The game can not be started now.");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "Starting...");
        Main.instance.startTask = new StartTask();
        Main.instance.startTask.runTask(Main.instance);
        return false;
    }
}
