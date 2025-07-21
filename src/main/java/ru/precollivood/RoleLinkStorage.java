package ru.precollivood;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoleLinkStorage {
    private final JavaPlugin plugin;
    private final File file;
    private final Map<String, String> roleMap = new HashMap<>();

    public RoleLinkStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "roles.yml");
        load();
    }

    public void load() {
        roleMap.clear();
        if (!file.exists()) return;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            roleMap.put(key, config.getString(key));
        }
    }

    public void save() {
        YamlConfiguration config = new YamlConfiguration();
        for (Map.Entry<String, String> entry : roleMap.entrySet()) {
            config.set(entry.getKey(), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Не удалось сохранить roles.yml: " + e.getMessage());
        }
    }

    public void setRole(String name, String roleId) {
        roleMap.put(name, roleId);
        save();
    }

    public String getRoleId(String name) {
        return roleMap.get(name);
    }

    public Map<String, String> getRoleMap() {
        return new HashMap<>(roleMap);
    }
} 