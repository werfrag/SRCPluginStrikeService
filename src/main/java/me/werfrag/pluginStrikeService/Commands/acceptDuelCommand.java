package me.werfrag.pluginStrikeService.Commands;

import me.werfrag.pluginStrikeService.Utils.InviteSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class acceptDuelCommand implements CommandExecutor {

    private final InviteSystem inviteSystem;

    public acceptDuelCommand(InviteSystem inviteSystem) {
        this.inviteSystem = inviteSystem;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            inviteSystem.acceptInvite(player);
        }
        return true;
    }
}
