package me.miqhtie.deathswap.events;

import me.miqhtie.deathswap.Main;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && Main.instance.renderLock) event.setCancelled(true);
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player && !Main.instance.getConfig().getBoolean("friendlyFire")) event.setCancelled(true);
    }
}
