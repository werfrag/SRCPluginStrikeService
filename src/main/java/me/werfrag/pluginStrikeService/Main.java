package me.werfrag.pluginStrikeService;

import me.werfrag.pluginStrikeService.Commands.acceptDuelCommand;
import me.werfrag.pluginStrikeService.Commands.duelCommand;
import me.werfrag.pluginStrikeService.Utils.ConfigManager;
import me.werfrag.pluginStrikeService.Utils.InviteSystem;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {
    public static ConfigManager configManager;

    @Override
    public void onEnable() {
        System.out.println("Il PluginStrikeService è stato attivato!");

        configManager = new ConfigManager(this);

        InviteSystem inviteSystem = new InviteSystem(this);

        Objects.requireNonNull(getCommand("duel")).setExecutor(new duelCommand(inviteSystem));
        Objects.requireNonNull(getCommand("acceptduel")).setExecutor(new acceptDuelCommand(inviteSystem));
        getServer().getPluginManager().registerEvents(new duelCommand(inviteSystem), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Il PluginStrikeService è stato disattivato!");
    }

    public static ConfigManager getFileManager() {
        return configManager;
    }
}
