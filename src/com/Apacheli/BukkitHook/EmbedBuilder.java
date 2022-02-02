package com.Apacheli.BukkitHook;

import org.json.JSONObject;

public class EmbedBuilder {
    private JSONObject json = new JSONObject();

    public EmbedBuilder author(String name) {
        JSONObject author = new JSONObject();
        author.put("name", name);

        json.put("author", author);

        return this;
    }

    public EmbedBuilder author(String name, String iconUrl) {
        JSONObject author = new JSONObject();
        author.put("name", name);
        author.put("icon_url", iconUrl);

        json.put("author", author);

        return this;
    }

    public EmbedBuilder color(int color) {
        json.put("color", color);

        return this;
    }

    public EmbedBuilder footer(String text) {
        JSONObject footer = new JSONObject();
        footer.put("text", text);

        json.put("footer", footer);

        return this;
    }

    public JSONObject build() {
        JSONObject data = new JSONObject();
        data.append("embeds", json);

        return data;
    }
}
