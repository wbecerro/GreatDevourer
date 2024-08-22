package wbe.greatDevourer.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.greatDevourer.GreatDevourer;

public class PlayerItemConsumeListeners implements Listener {
    private GreatDevourer plugin;

    public PlayerItemConsumeListeners(GreatDevourer plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playSoundOnEat(PlayerItemConsumeEvent event) {
        ItemStack consumed = event.getItem();
        ItemMeta meta = consumed.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey soundKey = new NamespacedKey(plugin, "foodSound");
        if(!meta.getPersistentDataContainer().has(soundKey)) {
            return;
        }

        NamespacedKey volumeKey = new NamespacedKey(plugin, "foodVolume");
        NamespacedKey pitchKey = new NamespacedKey(plugin, "foodPitch");

        Sound sound = Sound.valueOf(meta.getPersistentDataContainer().get(soundKey, PersistentDataType.STRING));
        float volume = meta.getPersistentDataContainer().get(volumeKey, PersistentDataType.FLOAT);
        float pitch = meta.getPersistentDataContainer().get(pitchKey, PersistentDataType.FLOAT);
        event.getPlayer().playSound(event.getPlayer().getLocation(), sound, volume, pitch);
    }
}
