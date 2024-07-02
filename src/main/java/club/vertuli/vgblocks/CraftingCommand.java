package club.vertuli.vgblocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CraftingCommand implements CommandExecutor {
    private final vGblocks plugin;

    public CraftingCommand(vGblocks plugin) {
        this.plugin = plugin;
    }
    String title = "§e§lTWOJ§f§lSERWER.PL §7▪ §6§lCraftingi";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 54, title); // 6x9 GUI
            ItemStack whiteGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0); // Białe szkło
            ItemMeta metaGlass = whiteGlass.getItemMeta();
            metaGlass.setDisplayName(" ");
            whiteGlass.setItemMeta(metaGlass);

            // Zapełnienie GUI białym szkłem
            for(int i = 0; i < gui.getSize(); i++) {
                gui.setItem(i, whiteGlass);
            }

            ConfigurationSection slotsConfig = plugin.getConfig().getConfigurationSection("gui-slots");
            if (slotsConfig != null) {
                for (String key : slotsConfig.getKeys(false)) {
                    int slot = Integer.parseInt(key);
                    String materialName = slotsConfig.getString(key + ".material", "STAINED_GLASS_PANE"); // Domyślnie białe szkło
                    Material material = Material.valueOf(materialName.toUpperCase());
                    String name = slotsConfig.getString(key + ".name", " ");
                    List<String> lore = slotsConfig.getStringList(key + ".lore");

                    ItemStack item = new ItemStack(material, 1, material == Material.STAINED_GLASS_PANE ? (short) 0 : 0); // Ustawienie białego szkła, jeśli brak definicji
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                    if (!lore.isEmpty()) meta.setLore(lore);
                    item.setItemMeta(meta);

                    gui.setItem(slot, item);
                }
            }

            //Dodatkowe itemy

            //sandFarmer
            ItemStack sandfarmer = new ItemStack(Material.SAND,1);
            ItemMeta metaFarmer = sandfarmer.getItemMeta();
            metaFarmer.setDisplayName(ChatColor.DARK_PURPLE+"SandFarmer");
            sandfarmer.setItemMeta(metaFarmer);

            //boyFarmer
            ItemStack boyfarmer = new ItemStack(Material.OBSIDIAN,1);
            ItemMeta metaBoyka = boyfarmer.getItemMeta();
            metaBoyka.setDisplayName(ChatColor.DARK_GREEN+"BoyFarmer");
            boyfarmer.setItemMeta(metaBoyka);

            //kopaczFosy
            ItemStack kopaczfosy = new ItemStack(Material.SPONGE,1);
            ItemMeta metaKopacz = kopaczfosy.getItemMeta();
            metaKopacz.setDisplayName(ChatColor.DARK_PURPLE+"Kopacz Fosy");
            kopaczfosy.setItemMeta(metaKopacz);

            //exec
            gui.setItem(47,sandfarmer);
            gui.setItem(48,boyfarmer);
            gui.setItem(49,kopaczfosy);

            player.openInventory(gui);
            return true;
        }
        return false;
    }
}
