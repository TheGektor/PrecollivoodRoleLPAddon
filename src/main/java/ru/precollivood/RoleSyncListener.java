package ru.precollivood;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Role;

public class RoleSyncListener {
    private final JavaPlugin plugin;
    private final RoleLinkStorage storage;
    private final LuckPerms luckPerms;

    public RoleSyncListener(JavaPlugin plugin, LuckPerms luckPerms, RoleLinkStorage storage) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
        this.storage = storage;
        register();
    }

    private void register() {
        EventBus bus = luckPerms.getEventBus();
        bus.subscribe(plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
    }

    private void onUserDataRecalculate(UserDataRecalculateEvent event) {
        User user = event.getUser();
        UUID uuid = user.getUniqueId();
        Map<String, String> roleMap = storage.getRoleMap();
        for (Map.Entry<String, String> entry : roleMap.entrySet()) {
            String perm = "dsync.role." + entry.getKey();
            boolean has = user.getCachedData().getPermissionData().checkPermission(perm).asBoolean();
            String discordRoleId = entry.getValue();
            syncDiscordRole(uuid, discordRoleId, has);
        }
    }

    private void syncDiscordRole(UUID uuid, String discordRoleId, boolean give) {
        String discordId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(uuid);
        if (discordId == null) return;
        DiscordSRV srv = DiscordSRV.getPlugin();
        String guildId = srv.getMainGuild().getId();
        Guild guild = srv.getJda().getGuildById(guildId);
        if (guild == null) return;
        Role role = guild.getRoleById(discordRoleId);
        if (role == null) return;
        long discordIdLong;
        try {
            discordIdLong = Long.parseLong(discordId);
        } catch (NumberFormatException e) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (give) {
                    guild.addRoleToMember(discordIdLong, role).queue();
                } else {
                    guild.removeRoleFromMember(discordIdLong, role).queue();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
} 