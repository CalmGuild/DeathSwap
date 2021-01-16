package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {
    @EventHandler
    public void blockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        if(event.getPlayer().isOp()) return;
        if(!Main.instance.running) {
            event.setCancelled(true);
            return;
        }

        if (!Main.instance.playersInGame.contains(event.getPlayer())) {
            event.setCancelled(true);
        }

    }
}
