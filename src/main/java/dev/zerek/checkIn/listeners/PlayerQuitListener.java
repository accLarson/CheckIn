package dev.zerek.checkIn.listeners;

import dev.zerek.checkIn.CheckIn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final CheckIn plugin;

    public PlayerQuitListener(CheckIn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove player from any active check-ins they might be running
        plugin.getCheckInManager().removeActiveCheckIn(event.getPlayer());

        // Remove player from pending lists in other check-ins
        plugin.getCheckInManager().removePendingPlayer(event.getPlayer());
    }
}
