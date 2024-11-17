package dev.zerek.checkIn.listeners;

import dev.zerek.checkIn.CheckIn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final CheckIn plugin;

    public PlayerJoinListener(CheckIn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().isOp()) {
            plugin.getCheckInManager().addPendingPlayer(event.getPlayer());
        }
    }
}
