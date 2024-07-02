package club.vertuli.vgblocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomCraftings implements Listener {

    private JavaPlugin plugin;

    public CustomCraftings(JavaPlugin plugin) {
        this.plugin = plugin;
        createSandfarmerRecipe();
        createBoyfarmerRecipe();
        createKopaczFosyRecipe();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private void createSandfarmerRecipe() {
        ItemStack sandfarmerItem = new ItemStack(Material.SAND);
        ItemMeta meta = sandfarmerItem.getItemMeta();
        meta.setDisplayName("§5Sandfarmer");
        List<String> lore = new ArrayList<>();
        lore.add("§7To magiczne cudo");
        lore.add("§7zmienia ziemie w piasek.");
        lore.add("§7Uwazaj z tym!");
        meta.setLore(lore);
        sandfarmerItem.setItemMeta(meta);

        ShapedRecipe sandfarmerRecipe = new ShapedRecipe(sandfarmerItem);
        sandfarmerRecipe.shape("SSS", "SDS", "SSS");
        sandfarmerRecipe.setIngredient('S', Material.SAND);
        sandfarmerRecipe.setIngredient('D', Material.DIAMOND_SPADE);
        Bukkit.addRecipe(sandfarmerRecipe);
    }

    private void createKopaczFosyRecipe() {
        ItemStack kopaczFosyItem = new ItemStack(Material.SPONGE);
        ItemMeta meta = kopaczFosyItem.getItemMeta();
        meta.setDisplayName("§5Kopacz fosy");
        List<String> lore = new ArrayList<>();
        lore.add("§7To magiczne cudo");
        lore.add("§7usuwa ziemie pod Toba.");
        lore.add("§7Uwazaj z tym!");
        meta.setLore(lore);
        kopaczFosyItem.setItemMeta(meta);

        ShapedRecipe kopaczFosyRecipe = new ShapedRecipe(kopaczFosyItem);
        kopaczFosyRecipe.shape("SSS", "SDS", "SSS");
        kopaczFosyRecipe.setIngredient('S', Material.STONE);
        kopaczFosyRecipe.setIngredient('D', Material.DIAMOND_PICKAXE);
        Bukkit.addRecipe(kopaczFosyRecipe);
    }

    private void createBoyfarmerRecipe() {
        ItemStack boyfarmerItem = new ItemStack(Material.OBSIDIAN);
        ItemMeta meta = boyfarmerItem.getItemMeta();
        meta.setDisplayName("§5Boyfarmer");
        List<String> lore = new ArrayList<>();
        lore.add("§7To magiczne cudo");
        lore.add("§7zmienia ziemie w obsydian.");
        lore.add("§7Uwazaj z tym!");
        meta.setLore(lore);
        boyfarmerItem.setItemMeta(meta);

        ShapedRecipe boyfarmerRecipe = new ShapedRecipe(boyfarmerItem);
        boyfarmerRecipe.shape("SSS", "SDS", "SSS");
        boyfarmerRecipe.setIngredient('S', Material.OBSIDIAN);
        boyfarmerRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(boyfarmerRecipe);
    }

    private boolean isSandfarmer(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return false;
        }
        List<String> lore = meta.getLore();
        return lore.contains("§7To magiczne cudo") &&
                lore.contains("§7zmienia ziemie w piasek.") &&
                lore.contains("§7Uwazaj z tym!");
    }

    private boolean isKopaczFosy(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return false;
        }
        List<String> lore = meta.getLore();
        return lore.contains("§7To magiczne cudo") &&
                lore.contains("§7usuwa ziemie pod Toba.") &&
                lore.contains("§7Uwazaj z tym!");
    }

    private boolean isBoyfarmer(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return false;
        }
        List<String> lore = meta.getLore();
        return lore.contains("§7To magiczne cudo") &&
                lore.contains("§7zmienia ziemie w obsydian.") &&
                lore.contains("§7Uwazaj z tym!");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Block placedBlock = event.getBlockPlaced();

        if (isSandfarmer(item)) {
            replaceBlocksBelow(placedBlock, Material.SAND);
            event.getBlockPlaced().setType(Material.AIR);
        } else if (isKopaczFosy(item)) {
            replaceBlocksBelow(placedBlock, Material.AIR);
            event.getBlockPlaced().setType(Material.AIR);
        } else if (isBoyfarmer(item)) {
            replaceBlocksBelow(placedBlock, Material.OBSIDIAN);
            event.getBlockPlaced().setType(Material.AIR);
        }
    }

    private void replaceBlocksBelow(Block block, Material material) {
        for (int y = block.getY() - 1; y >= 0; y--) {
            Block blockBelow = block.getWorld().getBlockAt(block.getX(), y, block.getZ());
            if (blockBelow.getType() != Material.BEDROCK) {
                blockBelow.setType(material);
            } else {
                // Przerwij pętlę, gdy natrafisz na bedrock
                break;
            }
        }
    }

}
