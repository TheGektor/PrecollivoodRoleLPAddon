package ru.precollivood;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PrecollivoodRoleLPAddon extends JavaPlugin {
    private RoleLinkStorage roleLinkStorage;

    @Override
    public void onEnable() {
        // Создаём data папку, если не существует
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdirs();
        // Инициализация хранилища связок
        this.roleLinkStorage = new RoleLinkStorage(this);
        // LuckPerms API
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            getLogger().severe("LuckPerms API не найден! Плагин отключается.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        LuckPerms luckPerms = provider.getProvider();
        // Регистрируем слушатель синхронизации ролей
        new RoleSyncListener(this, luckPerms, roleLinkStorage);
        // Регистрация команды
        this.getCommand("ds-role").setExecutor(new DsRoleCommandExecutor(this, roleLinkStorage));
        getLogger().info("PrecollivoodRoleLPAddon enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PrecollivoodRoleLPAddon disabled!");
    }
} 