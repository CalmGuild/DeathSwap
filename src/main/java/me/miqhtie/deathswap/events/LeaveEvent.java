package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {
    @EventHandler
    public void leave(PlayerQuitEvent event) {
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
        Main.instance.playersInGame.remove(event.getPlayer());
        event.setQuitMessage(ChatColor.GOLD + "[Leave] >> " + event.getPlayer().getName());
        Main.instance.checkForWinner(event.getPlayer());
    }
}
