package com.Apacheli.BukkitHook;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.Apacheli.BukkitHook.Webhook.executeWebhook;
import static org.bukkit.Bukkit.getServer;

public class EventHandlers implements Listener {
    String webhookUrl;

    public EventHandlers(String url) {
        webhookUrl = url;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        String displayName = event.getPlayer().getDisplayName();
        Server server = getServer();
        int onlinePlayers = server.getOnlinePlayers().size();
        int maxPlayers = server.getMaxPlayers();

        EmbedBuilder embed = new EmbedBuilder()
                .author(displayName + " joined the game", "https://minotar.net/helm/" + displayName)
                .color(0x55BA3C)
                .footer(onlinePlayers + "/" + maxPlayers + " players are online");

        executeWebhook(webhookUrl, embed.build());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        String displayName = event.getPlayer().getDisplayName();
        Server server = getServer();
        int onlinePlayers = server.getOnlinePlayers().size() - 1;
        int maxPlayers = server.getMaxPlayers();

        EmbedBuilder embed = new EmbedBuilder()
                .author(displayName + " left the game", "https://minotar.net/helm/" + displayName)
                .color(0xA83725)
                .footer(onlinePlayers + "/" + maxPlayers + " players are online");

        executeWebhook(webhookUrl, embed.build());
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        String displayName = event.getPlayer().getDisplayName();

        JSONObject allowedMentions = new JSONObject();
        allowedMentions.put("parse", new JSONArray());

        JSONObject json = new JSONObject();
        json.put("allowed_mentions", allowedMentions);
        json.put("avatar_url", "https://minotar.net/helm/" + displayName);
        json.put("content", event.getMessage());
        json.put("username", displayName);

        executeWebhook(webhookUrl, json);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Location location = player.getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        EmbedBuilder embed = new EmbedBuilder()
                .author(event.getDeathMessage(), "https://minotar.net/helm/" + player.getDisplayName())
                .color(0xE6CB1C)
                .footer(String.format("Died at XYZ: %s / %s / %s", x, y, z));

        executeWebhook(webhookUrl, embed.build());
    }
}
