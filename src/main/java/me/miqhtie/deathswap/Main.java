package me.miqhtie.deathswap;

import me.miqhtie.commandmanager.CommandManager;
import me.miqhtie.deathswap.commands.*;
import me.miqhtie.deathswap.events.*;
import me.miqhtie.deathswap.tasks.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends JavaPlugin {

    public ArrayList<Player> playersInGame = new ArrayList<>();

    public static Main instance;

    public boolean running = false;
    public StartTask startTask = null;
    public SwapTask swapTask = null;
    // Give player client time to render in new stuff after swap (around 5 ticks)
    public Boolean renderLock = false;

    public int minSwapTime  = 0;
    public int maxSwapTime = 0;

    private File configFile = new File(getDataFolder(), "config.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);


    @Override
    public void onEnable() {

        if(!configFile.exists()) {
            saveResource("config.yml", false);
        }

        instance = this;
        CommandManager commandManager = new CommandManager(this);
        commandManager.registerCommand(new StartCommand("start"));
        commandManager.registerCommand(new EndCommand("end"));
        commandManager.registerCommand(new ConfigCommand("config"));
        commandManager.registerCommand(new ReviveCommand("revive"));

        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MoveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
    }

    public void swap(){
        renderLock = true;
        ArrayList<Player> openSpots = playersInGame;
        Collections.shuffle(openSpots);
        Location firstloc = openSpots.get(0).getLocation();
        for(int i = 0; i < openSpots.size(); i++) {
            if(i == openSpots.size() - 1) {
                openSpots.get(i).teleport(firstloc);
                break;
            }
            openSpots.get(i).teleport(openSpots.get(i + 1));
        }
    }

    public int genRnd(int i, int j) {
        return (int) ((Math.random() * ((j - i) + 1)) + i);
    }

    public boolean canBeStarted(){
        return !running && startTask == null && swapTask == null;
    }

    public void randomTeleport(World world) {
        int x = world.getSpawnLocation().getBlockX();
        int z = world.getSpawnLocation().getBlockZ();
        int minDistance = 20;
        int maxRange = 250;
        String players = "@a";
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), String.format("spreadplayers %s %s %s %s %s %s", x, z ,minDistance, maxRange, false, players));
    }

    public File getConfigFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            getConfig().save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkForWinner(Player player) {
        if(!Main.instance.playersInGame.contains(player) || !Main.instance.running) return;
        Main.instance.playersInGame.remove(player);
        if(Main.instance.playersInGame.size() == 1) {
            Main.instance.swapTask.cancel();
            Main.instance.swapTask = null;
            Main.instance.running = false;


            Player winner = Main.instance.playersInGame.get(0);
            Bukkit.broadcastMessage(ChatColor.GREEN + winner.getName() + " has won! GG");
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.teleport(p.getWorld().getSpawnLocation());
                p.setGameMode(GameMode.SURVIVAL);
                p.getInventory().clear();
                if(p != winner)
                    p.sendTitle(ChatColor.RED +  "" + ChatColor.BOLD + "GAME OVER.", ChatColor.GOLD + winner.getName() + " is victorious!", 0, 80, 0);
                else
                    p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "VICTORY", "", 0, 80, 0);
            }
            return;
        }
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.MAGIC + "aaa " + ChatColor.RESET + "" + ChatColor.RED + player.getName() + " has died. " + ChatColor.DARK_RED + "" + ChatColor.MAGIC + "aaa ");
        Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + Main.instance.playersInGame.size() + ChatColor.GOLD + " players remain.");

        player.setGameMode(GameMode.SPECTATOR);
    }

}
