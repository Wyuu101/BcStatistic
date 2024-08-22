package com.bcstatistic;

import com.bcstatistic.Metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class BcStatistic extends JavaPlugin {
    private ConfigFile pluginConfig;
    private MyPlaceholderExpansion placeholderExpansion;
    @Override
    public void onEnable() {
        // Plugin startup logic
        // Plugin startup logic
        this.getLogger().info("§7============§aBcStatistic Enabled§7============");
        this.getLogger().info("§bAuthor:X_32mx");
        this.getLogger().info("§dNice to see you! :D");
        pluginConfig= ConfigFile.getInstance(this);
        placeholderExpansion = MyPlaceholderExpansion.getInstance(this);
        placeholderExpansion.register();
        //placeholderExpansion.DealWithConfig();
        getCommand("bcstatistic").setExecutor(new MyCommand(this));

        int pluginId = 21362; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
