package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent event) {
//        event.setDeathMessage(null);
        Main.instance.checkForWinner(event.getEntity());
    }
}
