package ru.precollivood;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class DsRoleCommandExecutor implements CommandExecutor {
    private final PrecollivoodRoleLPAddon plugin;
    private final RoleLinkStorage roleLinkStorage;

    public DsRoleCommandExecutor(PrecollivoodRoleLPAddon plugin, RoleLinkStorage roleLinkStorage) {
        this.plugin = plugin;
        this.roleLinkStorage = roleLinkStorage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/ds-role add <название> <ID_роли_в_Discord>");
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length != 3) {
                sender.sendMessage(ChatColor.RED + "Использование: /ds-role add <название> <ID_роли_в_Discord>");
                return true;
            }
            String name = args[1].toLowerCase();
            String roleId = args[2];
            roleLinkStorage.setRole(name, roleId); // сохраняем связку
            sender.sendMessage(ChatColor.GREEN + "Связка добавлена: " + name + " -> " + roleId);
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Неизвестная подкоманда. Используйте /ds-role add ...");
        return true;
    }
} 