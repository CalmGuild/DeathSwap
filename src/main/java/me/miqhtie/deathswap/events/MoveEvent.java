package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
    @EventHandler
    public void move(PlayerMoveEvent event) {
        if(Main.instance.renderLock) event.setCancelled(true);
    }
}
