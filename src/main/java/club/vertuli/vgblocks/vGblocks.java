package club.vertuli.vgblocks;

import org.bukkit.plugin.java.JavaPlugin;

public final class vGblocks extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getLogger().info("Loaded");
        getServer().getPluginManager().registerEvents(new GuiClickListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomCraftings(this), this);
        this.getCommand("craftingi").setExecutor(new CraftingCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Unloaded");
    }
}
