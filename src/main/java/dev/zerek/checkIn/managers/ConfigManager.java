package dev.zerek.checkIn.managers;

import dev.zerek.checkIn.CheckIn;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final CheckIn plugin;
    Map<String,String> messages = new HashMap<>();

    public ConfigManager(CheckIn plugin) {
        this.plugin = plugin;
        ConfigurationSection messagesSection = plugin.getConfig().getConfigurationSection("messages");
        messagesSection.getKeys(false).forEach(key -> messages.put(key,messagesSection.getString(key)));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }
}
