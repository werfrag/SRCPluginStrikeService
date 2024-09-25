package me.werfrag.pluginStrikeService.Commands;

import me.werfrag.pluginStrikeService.Utils.InviteSystem;
import me.werfrag.pluginStrikeService.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class duelCommand implements CommandExecutor, Listener {
    private final InviteSystem inviteSystem;

    public duelCommand(InviteSystem inviteSystem) {
        this.inviteSystem = inviteSystem;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {

                if (!player.getName().equals(args[0])) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target != null) {
                        if (inviteSystem.playerInList(player) && inviteSystem.playerInList(target)) {
                            Inventory gui = Bukkit.createInventory(null, 27, "Duello " + target.getName());

                            ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
                            ItemMeta itemMeta = item.getItemMeta();
                            assert itemMeta != null;
                            itemMeta.setDisplayName(Utils.hex("#ff00b3Spada vs Spada"));
                            item.setItemMeta(itemMeta);

                            gui.setItem(13, item);

                            player.openInventory(gui);
                        } else {
                            player.sendMessage(Utils.hex("#e60000Il giocatore è già stato invitato o hai già inviato un invito."));
                        }
                    } else {
                        player.sendMessage(Utils.hex("#e60000LIl giocatore specificato non è online."));
                    }
                } else {
                    player.sendMessage(Utils.hex("#e60000Non puoi duellare te stesso."));
                }
            } else {
                player.sendMessage(Utils.hex("#e60000Utilizza /duel <giocatore>"));
            }
        }
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().startsWith("Duello ") && e.getSlotType() == InventoryType.SlotType.CONTAINER) {

            Player target = Bukkit.getPlayer(e.getView().getTitle().replace("Duello ", ""));

            if (target != null) {
                e.setCancelled(true);
                if (e.getRawSlot() == 13) {
                    inviteSystem.invitePlayer((Player) e.getWhoClicked(), target);
                    e.getWhoClicked().closeInventory();
                }
            }
        }
    }
}
