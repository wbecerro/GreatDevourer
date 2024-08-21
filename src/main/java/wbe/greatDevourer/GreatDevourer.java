package wbe.greatDevourer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.greatDevourer.commands.CommandListener;
import wbe.greatDevourer.config.Config;
import wbe.greatDevourer.config.Messages;
import wbe.greatDevourer.listeners.EventListeners;

import java.io.File;

public final class GreatDevourer extends JavaPlugin {

    private FileConfiguration configuration;

    private final CommandListener commandListener = new CommandListener(this);

    private final EventListeners eventListeners = new EventListeners(this);

    public static Config config;

    public static Messages messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("GreatDevourer enabled correctly");
        reloadConfiguration();
        config = new Config(configuration);

        getCommand("greatdevourer").setExecutor(this.commandListener);
        this.eventListeners.initializeListeners();
    }

    @Override
    public void onDisable() {
        reloadConfig();
        getLogger().info("GreatDevourer disabled correctly");
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
    }
}
