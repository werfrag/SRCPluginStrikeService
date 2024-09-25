package me.werfrag.pluginStrikeService.Utils;

import me.werfrag.pluginStrikeService.Main;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class InviteSystem {
    private final Main plugin;
    private BukkitTask inviteCooldown;
    private final HashMap<Player, Player> invites;

    public InviteSystem(Main plugin) {
        this.plugin = plugin;
        this.invites = new HashMap<>();
    }

    public boolean playerInList(Player player) {
        return !invites.containsKey(player);
    }

    public void invitePlayer(Player player, Player invitato) {
        invites.put(player, invitato);
        TextComponent message = new TextComponent(Utils.hex("#05e600Hai ricevuto un invito per un duello da parte di " + player.getName() + ". " +
                "Clicca il messaggio oppure fai /acceptduel per unirsi. Il duello scade tra 1 minuto."));
        message.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/acceptduel"));
        invitato.spigot().sendMessage(message);

        this.inviteCooldown = new BukkitRunnable() {
            @Override
            public void run() {
                invites.remove(player);
                invitato.sendMessage(Utils.hex("#e60000L'invito per un duello da parte di " + player.getName() + " è scaduto."));
                this.cancel();
            }
        }.runTaskLater(plugin, 60 * 20L);
    }

    public void acceptInvite(Player player) {
        if (invites.containsKey(player)) {
            Player coluiInvitato = invites.get(player);
            invites.remove(player);
            inviteCooldown.cancel();

            player.sendMessage(Utils.hex("#05e600Hai accettato l'invito per un duello da parte di " + coluiInvitato.getName() + "."));

            FileConfiguration config = Main.getFileManager().getConfig();

            int x = config.getInt("coordinates.red_team.x");
            int y = config.getInt("coordinates.red_team.y");
            int z = config.getInt("coordinates.red_team.z");

            String worldName = config.getString("coordinates.red_team.world");

            assert worldName != null;
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                player.sendMessage("Il mondo " + worldName + " non è stato trovato.");
                return;
            }

            Location redTeamLocation = new Location(world, x, y, z);

            int xBlue = config.getInt("coordinates.blue_team.x");
            int yBlue = config.getInt("coordinates.blue_team.y");
            int zBlue = config.getInt("coordinates.blue_team.z");

            String worldNameBlue = config.getString("coordinates.blue_team.world");

            assert worldNameBlue != null;
            World worldBlue = Bukkit.getWorld(worldName);
            if (worldBlue == null) {
                player.sendMessage("Il mondo " + worldName + " non è stato trovato.");
                return;
            }

            Location blueTeamLocation = new Location(worldBlue, xBlue, yBlue, zBlue);

            coluiInvitato.teleport(redTeamLocation);
            player.teleport(blueTeamLocation);
        } else {
            player.sendMessage(Utils.hex("#e60000Non sei stato invitato da nessun giocatore."));
        }
    }
}
