package me.werfrag.pluginStrikeService.Utils;

import me.werfrag.pluginStrikeService.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final FileConfiguration config;

    private Main instance;

    public ConfigManager(Main plugin) {
        this.instance = plugin;
        this.config = saveConfig();
    }

    public void saveFile(FileConfiguration configuration, File file) {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileConfiguration saveConfig() {
        File file = new File(instance.getDataFolder(), "config.yml");

        if (!file.exists()) {
            instance.saveResource("config.yml", false);
        }

        return loadConfig(file);
    }

    public FileConfiguration loadConfig(File file) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
        return (FileConfiguration)configuration;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
