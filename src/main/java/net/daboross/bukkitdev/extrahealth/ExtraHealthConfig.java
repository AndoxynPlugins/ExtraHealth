/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.extrahealth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class ExtraHealthConfig {

    private final JavaPlugin plugin;
    private final Map<String, Integer> permissionsMap = new HashMap<String, Integer>();
    private File configFile;
    private FileConfiguration config;

    public ExtraHealthConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveDefaultConfig() {
        plugin.saveResource("permissions.yml", false);
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "permissions.yml");
        }
        if (!configFile.exists()) {
            saveDefaultConfig();
        }
        config = new YamlConfiguration();
        config.options().pathSeparator('|');
        try {
            config.load(configFile);
        } catch (FileNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "Exception loading config", ex);
            return;
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Exception loading config", ex);
            return;
        } catch (InvalidConfigurationException ex) {
            plugin.getLogger().log(Level.SEVERE, "Exception loading config", ex);
            return;
        }
        ConfigurationSection permissions = config.getConfigurationSection("permissions");
        for (String key : permissions.getKeys(true)) {
            System.out.println("key:" + config.getInt("permissions|" + key));
        }
    }

    public Map<String, Integer> getPermissionsMap() {
        if (config == null) {
            reloadConfig();
        }
        return Collections.unmodifiableMap(permissionsMap);
    }
}
