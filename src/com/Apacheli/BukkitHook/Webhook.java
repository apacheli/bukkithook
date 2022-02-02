package com.Apacheli.BukkitHook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.bukkit.Bukkit.getConsoleSender;

public class Webhook {
    public static void executeWebhook(String spec, JSONObject json) {
        if (spec == "WEBHOOK_URL_HERE") {
            getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Missing webhook URL. Not sending a message!");
            return;
        }
        ConsoleCommandSender consoleSender = getConsoleSender();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(spec);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json; utf-8");
            connection.setRequestProperty("Content-Type", "application/json");
            String userAgent = String.format("DiscordBot (%s, %s)", "https://github.com/apacheli/bukkithook", "0.0.1");
            connection.setRequestProperty("User-Agent", userAgent);

            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            consoleSender.sendMessage(json.toString());

            int status = connection.getResponseCode();
            InputStream stream = status < 400 ? connection.getInputStream() : connection.getErrorStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"))) {
                String body = "";
                String line = null;
                while ((line = reader.readLine()) != null) {
                    body += line;
                }
                consoleSender.sendMessage(ChatColor.AQUA + "status: " + status + " | body: " + body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
}
