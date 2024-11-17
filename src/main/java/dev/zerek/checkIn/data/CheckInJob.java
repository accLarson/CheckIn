package dev.zerek.checkIn.data;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class CheckInJob {
    private final Player admin;
    private final List<Player> viewedPlayers;
    private final List<Player> pendingPlayers;

    public CheckInJob(Player admin, List<Player> pendingPlayers) {
        this.admin = admin;
        this.viewedPlayers = new ArrayList<>();
        this.pendingPlayers = pendingPlayers;
    }

    public Player getAdmin() {
        return admin;
    }

    public List<Player> getViewedPlayers() {
        return viewedPlayers;
    }

    public List<Player> getPendingPlayers() {
        return pendingPlayers;
    }
}
