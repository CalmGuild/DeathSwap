package me.miqhtie.deathswap.tasks;

import me.miqhtie.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class StartTask extends BukkitRunnable {
    @Override
    public void run() {
        final ArrayList<Player> onlinePlayers = (new ArrayList<>(Bukkit.getOnlinePlayers()));

        World world = null;
        for(Player p  : onlinePlayers) {
            world = p.getWorld();
            p.sendTitle(ChatColor.GREEN + "The game is now STARTING", "", 0, 40, 0);
            p.setHealth(20);
            p.setSaturation(0);
            p.setGameMode(GameMode.SURVIVAL);
        }

        if(world != null){
            world.getWorldBorder().setCenter(world.getSpawnLocation());
            world.getWorldBorder().setSize(1000);
        }

        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "time set 0");
        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "save-off");
        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "gamerule spectatorsGenerateChunks false");

        Main.instance.minSwapTime = Main.instance.getConfig().getInt("minSwapTime");
        Main.instance.maxSwapTime  = Main.instance.getConfig().getInt("maxSwapTime");
        Main.instance.randomTeleport(onlinePlayers.get(0).getWorld());

        Main.instance.playersInGame = onlinePlayers;

        Main.instance.swapTask = new SwapTask();
        Main.instance.swapTask.runTaskTimer(Main.instance, 0, 1);

        Main.instance.startTask = null;
        Main.instance.running = true;
        cancel();
    }
}
