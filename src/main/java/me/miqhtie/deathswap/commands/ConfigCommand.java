package me.miqhtie.deathswap.commands;

import me.miqhtie.commandmanager.utils.SubCommand;
import me.miqhtie.deathswap.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.InputMismatchException;

public class ConfigCommand extends SubCommand {

    public ConfigCommand(String name) {
        super(name);
    }            int minSwapTime = Main.instance.getConfig().getInt("minSwapTime");
    int maxSwapTime = Main.instance.getConfig().getInt("maxSwapTime");

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!(sender.hasPermission("deathswap.config"))) {
            sender.sendMessage(ChatColor.RED + "Missing permissions");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments\n[minswaptime, maxswaptime, friendlyfire]");
            return true;
        }

        int minSwapTime = Main.instance.getConfig().getInt("minSwapTime");
        int maxSwapTime = Main.instance.getConfig().getInt("maxSwapTime");
        boolean friendlyFire = Main.instance.getConfig().getBoolean("friendlyFire");


        if(args[0].equalsIgnoreCase("minswaptime")) {

            if(args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Invalid arguments. Missing number");
                return true;
            }

            int newValue = 0;
            try {
                newValue = Integer.parseInt(args[1]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Invalid number -> " + args[1]);
                return true;
            }

            if(newValue > maxSwapTime) {
                sender.sendMessage(ChatColor.RED + String.format("Error: Minimum Swap Time (%s) is higher then the Maximum Swap Time (%s)", newValue, maxSwapTime));
                return true;
            }

            if(newValue < 0) {
                sender.sendMessage(ChatColor.RED + "Number must not be below 0");
                return true;
            }

            Main.instance.getConfig().set("minSwapTime", newValue);
            Main.instance.saveConfig();

            sender.sendMessage(String.format("%sUpdated Minimum Swap time. (%s --> %s)", ChatColor.GREEN, minSwapTime, newValue));
            return true;
        }

        if(args[0].equalsIgnoreCase("maxswaptime")) {

            if(args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Invalid arguments. Missing number");
                return true;
            }

            int newValue = 0;
            try {
                newValue = Integer.parseInt(args[1]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Invalid number -> " + args[1]);
                return true;
            }

            if(newValue < minSwapTime) {
                sender.sendMessage(ChatColor.RED + String.format("Error: Maximum Swap Time (%s) is lower than the Minimum Swap Time (%s)", newValue, minSwapTime));
                return true;
            }

            if(newValue < 0) {
                sender.sendMessage(ChatColor.RED + "Number must not be below 0");
                return true;
            }

            Main.instance.getConfig().set("maxSwapTime", newValue);
            Main.instance.saveConfig();

            sender.sendMessage(String.format("%sUpdated Maximum Swap Time Swap time. (%s --> %s)", ChatColor.GREEN, maxSwapTime, newValue));
            return true;
        }

        if(args[0].equalsIgnoreCase("friendlyfire")) {
            if(args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Invalid arguments. True/False");
                return true;
            }

            boolean newValue = false;
            try {
                newValue = Boolean.parseBoolean(args[1]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Invalid boolean (either true or false) -> " + args[1]);
            }

            Main.instance.getConfig().set("friendlyFire", newValue);
            sender.sendMessage(String.format("%sUpdated Friendly Fire (%s --> %s)", ChatColor.GREEN, friendlyFire, newValue));
            return true;
        }

        return false;
    }
}
