package wbe.greatDevourer.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.greatDevourer.GreatDevourer;

public class EventListeners {

    private GreatDevourer plugin;

    public EventListeners(GreatDevourer plugin) {
        this.plugin = plugin;
    }

    public void initializeListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerItemConsumeListeners(plugin), plugin);
    }
}
