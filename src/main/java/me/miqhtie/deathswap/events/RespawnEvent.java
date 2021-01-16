package me.miqhtie.deathswap.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {
    @EventHandler
    public void respawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation());
    }
}
