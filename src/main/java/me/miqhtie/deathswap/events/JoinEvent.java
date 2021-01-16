package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GOLD + "[Join] >> " + event.getPlayer().getName());
        if(!Main.instance.running) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
            return;
        }

        if(!Main.instance.playersInGame.contains(event.getPlayer())) {
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
            event.getPlayer().sendMessage(ChatColor.GOLD +"Sorry! There is already a game going on. Please wait till next round!");
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
