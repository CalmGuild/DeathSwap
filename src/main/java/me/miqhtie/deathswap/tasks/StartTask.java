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

        for(Player p  : onlinePlayers) {
            p.sendTitle(ChatColor.GREEN + "The game is now STARTING", "", 0, 40, 0);
            p.setHealth(20);
            p.setSaturation(0);
            p.setGameMode(GameMode.SURVIVAL);
        }

        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "worldborder set 500");
        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "time set 0");
        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "save-off");
        Main.instance.getServer().dispatchCommand( Main.instance.getServer().getConsoleSender(), "gamerule spectatorsGenerateChunks false");

        Main.instance.randomTeleport(onlinePlayers.get(0).getWorld());

        Main.instance.playersInGame = onlinePlayers;

        Main.instance.swapTask = new SwapTask();
        Main.instance.swapTask.runTaskTimer(Main.instance, 0, 1);

        Main.instance.startTask = null;
        Main.instance.running = true;
        cancel();
    }
}
