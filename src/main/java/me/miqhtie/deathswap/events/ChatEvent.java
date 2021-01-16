package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if(Main.instance.running && !Main.instance.playersInGame.contains(event.getPlayer()) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Spectators can not talk.");
        }
    }
}
