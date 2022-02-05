package com.Apacheli.BukkitHook;

import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

import static com.Apacheli.BukkitHook.Webhook.executeWebhook;
import static org.bukkit.Bukkit.broadcastMessage;

public class RaidEventHandlers implements Listener {
    String webhookUrl;

    public RaidEventHandlers(String url) {
        webhookUrl = url;
    }

    @EventHandler
    public void onRaidTriggerEvent(RaidTriggerEvent event) {
        String playerName = event.getPlayer().getDisplayName();

        Raid raid = event.getRaid();

        Location location = raid.getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String message = String.format("%s started a raid started at XYZ: %s / %s / %s", playerName, x, y, z);

        broadcastMessage(message);

        EmbedBuilder embed = new EmbedBuilder()
                .author(message)
                .color(0x919299)
                .footer("Level: " + raid.getBadOmenLevel());

        executeWebhook(webhookUrl, embed.build());
    }
}
