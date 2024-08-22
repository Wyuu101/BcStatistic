package com.bcstatistic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class ConfigFile {
    private static com.bcstatistic.ConfigFile instance;
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private Logger logger;
    public class MyPair<K, V> {
        private K key;
        private V value;

        public MyPair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
    public static HashMap<String, MyPair<Integer,List<String>>> map= new HashMap<>();
    private  Set<String> nameList= new HashSet<>() ;
    public int failed=0;

    private ConfigFile(JavaPlugin plugin) {
        this.plugin = plugin;
        logger=plugin.getLogger();
        loadConfig();
    }

    public static com.bcstatistic.ConfigFile getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new com.bcstatistic.ConfigFile(plugin);
        }
        return instance;
    }

    public void loadConfig() {
        File pluginFolder = plugin.getDataFolder().getParentFile();
        File testFolder = new File(pluginFolder, "BcStatistic");
        if (!testFolder.exists()) {
            testFolder.mkdirs();
        }
        File configFile = new File(testFolder, "config.yml");
        //System.out.println(configFile);
        if (!configFile.exists()) {
            try {
                InputStream inputStream = plugin.getResource("config.yml");
                Files.copy(inputStream, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error releasing configuration file, please contact the author!");
            }
        }
        //plugin.reloadConfig();
        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(configFile);
        config = customConfig;
        logger.info("Processing configuration files");
        DealWithConfig();
    }

    public void DealWithConfig() {
        failed=0;
        if (!map.isEmpty()){
            map.clear();
        }
        if(!nameList.isEmpty()){
            nameList.clear();
        }
        ConfigurationSection parentSection = config.getConfigurationSection("StatisticServers");
        if (parentSection != null) {
            Set<String> childKeys = parentSection.getKeys(false);
            for (String key : childKeys) {
                ConfigurationSection keySection = config.getConfigurationSection("StatisticServers." + key);
                if (keySection != null) {
                    if(keySection.contains("name")) {
                        String placeName = keySection.getString("name");
                        if (!nameList.add(placeName)) {
                            logger.warning(String.format("Detected duplicate name placeholder %s in the configuration file, please check", placeName));
                            failed++;
                            continue;
                        } else {
                            if(keySection.contains("elements")) {
                                List<String> serverList = keySection.getStringList("elements");
                                int minValue = keySection.getInt("minValue", 0);
                                map.put(placeName, new MyPair(minValue, serverList));
                            }
                            else {
                                logger.warning(String.format("StatisticServers.%s missing necessary configuration information, placeholder registration failed, skipped",key));
                                failed++;
                                continue;
                            }
                        }
                    }
                    else {
                        logger.warning(String.format("StatisticServers.%s missing necessary configuration information, placeholder registration failed, skipped",key));
                        failed++;
                        continue;
                    }
                }
                else{
                    logger.warning(String.format("StatisticServers.%s missing necessary configuration information, placeholder registration failed, skipped",key));
                    failed++;
                    continue;
                }
            }
            logger.info(String.format("§aRegistration of custom variables completed! Success %d, Failure %d",map.size(),failed));
        }
        else {
            logger.severe("Error checking configuration file, variable registration failed, please check!");

        }
        logger.info("§7==========================================");
    }



    public FileConfiguration getConfig() {
        return config;
    }
}