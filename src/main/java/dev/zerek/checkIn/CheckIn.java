package dev.zerek.checkIn;

import dev.zerek.checkIn.managers.CheckInManager;
import dev.zerek.checkIn.managers.ConfigManager;
import dev.zerek.checkIn.listeners.PlayerJoinListener;
import dev.zerek.checkIn.listeners.PlayerQuitListener;
import dev.zerek.checkIn.commands.CheckInCommand;
import dev.zerek.checkIn.commands.CheckInTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CheckIn extends JavaPlugin {

    private ConfigManager configManager;
    private CheckInManager checkInManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        this.checkInManager = new CheckInManager(this);
        
        // Register command and tab completer
        this.getCommand("checkin").setExecutor(new CheckInCommand(this));
        this.getCommand("checkin").setTabCompleter(new CheckInTabCompleter());

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CheckInManager getCheckInManager() {
        return checkInManager;
    }

}
