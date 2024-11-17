package dev.zerek.checkIn.managers;

import dev.zerek.checkIn.CheckIn;
import dev.zerek.checkIn.data.CheckInJob;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.HashMap;

public class CheckInManager {
    private final CheckIn checkIn;
    private final Map<Player, CheckInJob> activeCheckIns;

    public CheckInManager(CheckIn checkIn) {
        this.checkIn = checkIn;
        this.activeCheckIns = new HashMap<>();
    }

    public void addActiveCheckIn(Player admin, CheckInJob checkInJob) {
        this.activeCheckIns.put(admin, checkInJob);
    }

    public boolean hasActiveCheckIn(Player admin) {
        return this.activeCheckIns.containsKey(admin);
    }

    public void removeActiveCheckIn(Player admin) {
        this.activeCheckIns.remove(admin);
    }

    public void removePendingPlayer(Player player) {
        for (CheckInJob checkInJob : activeCheckIns.values()) {
            checkInJob.getPendingPlayers().remove(player);
        }
    }

    public void addPendingPlayer(Player player) {
        for (CheckInJob checkInJob : activeCheckIns.values()) {
            if (!checkInJob.getViewedPlayers().contains(player)) checkInJob.getPendingPlayers().add(player);
        }
    }

    public boolean isLastPlayer(Player admin) {
        CheckInJob job = activeCheckIns.get(admin);
        return job != null && job.getPendingPlayers().isEmpty();
    }

    public Player getNextPlayer(Player admin) {
        CheckInJob job = activeCheckIns.get(admin);
        if (job == null || job.getPendingPlayers().isEmpty()) return null;

        int randomIndex = (int) (Math.random() * job.getPendingPlayers().size());
        Player selected = job.getPendingPlayers().get(randomIndex);
        
        job.getPendingPlayers().remove(selected);
        job.getViewedPlayers().add(selected);
        
        return selected;
    }
}
