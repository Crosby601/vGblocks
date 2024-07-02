package club.vertuli.vgblocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class GuiClickListener implements Listener {
    private final vGblocks plugin;

    public GuiClickListener(vGblocks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Inventory gui = event.getInventory();
        String title = "§e§lTWOJ§f§lSERWER.PL §7▪ §6§lCraftingi";
        if (!event.getView().getTitle().equals(title)) return;

        int clickedSlot = event.getSlot();
        if (clickedSlot >= 0 && clickedSlot <= 53) {
            event.setCancelled(true); // Aby zapobiec przemieszczaniu itemów
            updateGuiSlots(gui, clickedSlot);
        }
    }

    private void updateGuiSlots(Inventory gui, int clickedSlot) {
        ConfigurationSection blocksSection = plugin.getConfig().getConfigurationSection("gui-slots." + clickedSlot + ".blocks");
        if (blocksSection == null) return;

        for (String key : blocksSection.getKeys(false)) {
            ConfigurationSection blockSection = blocksSection.getConfigurationSection(key);
            if (blockSection == null) continue;

            int slotToUpdate = Integer.parseInt(key);
            String materialName = blockSection.getString("material", "STAINED_GLASS_PANE");
            Material material = Material.valueOf(materialName.toUpperCase());
            String name = blockSection.getString("name", "Custom Name");
            List<String> lore = blockSection.getStringList("lore");

            ItemStack item = createItemStack(material, name, lore);

            gui.setItem(slotToUpdate, item);
        }
    }


    private ItemStack createItemStack(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if (lore != null && !lore.isEmpty()) {
            List<String> coloredLore = lore.stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .collect(Collectors.toList());
            meta.setLore(coloredLore);
        }
        item.setItemMeta(meta);
        return item;
    }

}
