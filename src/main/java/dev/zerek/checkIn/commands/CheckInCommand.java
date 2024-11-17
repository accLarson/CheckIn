package dev.zerek.checkIn.commands;

import dev.zerek.checkIn.CheckIn;
import dev.zerek.checkIn.data.CheckInJob;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckInCommand implements CommandExecutor {

    private final CheckIn plugin;

    public CheckInCommand(CheckIn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Check if sender is a player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("error-not-player")));
            return true;
        }

        // Check permissions
        if (!player.hasPermission("checkin.admin")) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("error-no-permission")));
            return true;
        }

        // Handle invalid argument count
        if (args.length > 1) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("error-invalid-command")));
            return true;
        }

        // Handle commands with one argument
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {

                case "cancel":
                    if (plugin.getCheckInManager().hasActiveCheckIn(player)) {
                        plugin.getCheckInManager().removeActiveCheckIn(player);
                        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("checkin-cancelled")));
                    } else {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("error-no-checkin")));
                    }
                    return true;

                case "next":
                    if (!plugin.getCheckInManager().hasActiveCheckIn(player)) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("error-no-checkin")));
                        return true;
                    }
                    
                    Player nextPlayer = plugin.getCheckInManager().getNextPlayer(player);

                    TextComponent nextMessage = (TextComponent) MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("next-player"), Placeholder.unparsed("player",nextPlayer.getName()));
                    player.sendMessage(nextMessage);

                    player.teleport(nextPlayer);
                    if (plugin.getCheckInManager().isLastPlayer(player)) {
                        plugin.getCheckInManager().removeActiveCheckIn(player);
                        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("no-more-players")));
                    }
                    return true;

                default:
                    player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("error-invalid-command")));
                    return true;
            }
        }
        else {
            if (plugin.getCheckInManager().hasActiveCheckIn(player)) plugin.getCheckInManager().removeActiveCheckIn(player);
            ArrayList<Player> players = plugin.getServer().getOnlinePlayers().stream()
                    .filter(p -> !p.isOp() && !p.equals(player))
                    .collect(Collectors.toCollection(ArrayList::new));
            plugin.getCheckInManager().addActiveCheckIn(player, new CheckInJob(player, players));
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfigManager().getMessage("new-checkin")));
        }

        return true;
    }
}
