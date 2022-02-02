package com.Apacheli.BukkitHook;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static com.Apacheli.BukkitHook.Webhook.executeWebhook;

public class BukkitHook extends JavaPlugin {
    String webhookUrl;

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Disabled BukkitHook.");

        EmbedBuilder embed = new EmbedBuilder()
                .author("The server is now closed")
                .color(0x23B1E4);

        executeWebhook(webhookUrl, embed.build());
    }

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.addDefault("webhook_url", "WEBHOOK_URL_HERE");

        webhookUrl = config.getString("webhook_url");

        Server server = getServer();

        server.getPluginManager().registerEvents(new EventHandlers(webhookUrl), this);

        server.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabled BukkitHook.");

        EmbedBuilder embed = new EmbedBuilder()
                .author("The server is now open")
                .color(0x23B1E4);

        executeWebhook(webhookUrl, embed.build());
    }
}
