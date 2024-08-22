package com.bcstatistic;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;


public class MyPlaceholderExpansion extends PlaceholderExpansion {
    private static com.bcstatistic.MyPlaceholderExpansion instance;
    private static ConfigFile mangerConInstance;
    public final Logger logger= getLogger();





    public static com.bcstatistic.MyPlaceholderExpansion getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new com.bcstatistic.MyPlaceholderExpansion("bcstatistic");
            mangerConInstance = ConfigFile.getInstance(plugin);
        }
        return instance;
    }



    private String identifier;

    public MyPlaceholderExpansion(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getAuthor() {
        return "X_32mx";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        // 处理占位符的请求
        if (mangerConInstance.map.containsKey(identifier)) {
            ConfigFile.MyPair<Integer, List<String>> pair = mangerConInstance.map.get(identifier);
            int total = pair.getKey();
            List<String> serversList = pair.getValue();
            for (String servername : serversList) {
                String parsedPlaceholder = PlaceholderAPI.setPlaceholders(player, String.format("%%bungee_%s%%", servername));
                total = total + Integer.parseInt(parsedPlaceholder);
            }
            return String.valueOf(total);
        } else {
            return null;
        }
    }




}