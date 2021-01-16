package me.miqhtie.deathswap.tasks;

import me.miqhtie.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SwapTask extends BukkitRunnable{

    int countTicks = 0;
    int count = 0;
    int renderLock = 0;
    int delay = Main.instance.genRnd(Main.instance.minSwapTime, Main.instance.maxSwapTime);

    @Override
    public void run() {
        if(Main.instance.renderLock) {
            if(renderLock == 5) {
                renderLock = 0;
                Main.instance.renderLock = false;
            } else {
                renderLock++;
            }
        }
        if(countTicks < 20) {
            countTicks++;
            return;
        } else {
            countTicks = 0;
            count++;
        } if (!(count >= delay - 5)) return;

        if(count != delay) {
            for(Player p : Main.instance.playersInGame) {
                p.playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, 10, 1);
                p.sendMessage(ChatColor.GOLD + "Swapping in " + ChatColor.RED + "" + ChatColor.BOLD  + (delay - count));
            }
        } else {
            Main.instance.playersInGame.removeIf(p -> !p.isOnline());
            Main.instance.swap();
            countTicks = 0;
            count = 0;
            int delay = Main.instance.genRnd(Main.instance.minSwapTime, Main.instance.maxSwapTime);
        }
    }

}