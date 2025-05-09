package wbe.greatDevourer.util;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import wbe.greatDevourer.GreatDevourer;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    GreatDevourer plugin;

    public Utilities(GreatDevourer plugin) {
        this.plugin = plugin;
    }

    public void addFoodToItem(ItemStack item, int food, float saturation, boolean always, float time) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        FoodComponent foodComponent = meta.getFood();
        foodComponent.setNutrition(food);
        foodComponent.setSaturation(saturation);
        foodComponent.setCanAlwaysEat(always);
        foodComponent.setEatSeconds(time);
        meta.setFood(foodComponent);

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add("");
        lore.add(GreatDevourer.config.foodLine.replace("%value%", String.valueOf(food)));
        lore.add(GreatDevourer.config.saturationLine.replace("%saturation%", String.valueOf(saturation)));
        lore.add(GreatDevourer.config.alwaysLine.replace("%always%", always ? "Sí" : "No"));
        lore.add(GreatDevourer.config.timeLine.replace("%time%", String.valueOf(time)));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void showItemNutritionalInfo(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        FoodComponent foodComponent = meta.getFood();
        int food = foodComponent.getNutrition();
        float saturation = foodComponent.getSaturation();
        boolean always = foodComponent.canAlwaysEat();
        float time = foodComponent.getEatSeconds();
        List<FoodComponent.FoodEffect> effects = foodComponent.getEffects();
        NamespacedKey soundKey = new NamespacedKey(plugin, "foodSound");

        for(String line : GreatDevourer.messages.get) {
            line = line.replace("&", "§")
                    .replace("%value%", String.valueOf(food))
                    .replace("%saturation%", String.valueOf(saturation))
                    .replace("%always%", always ? "Sí" : "No")
                    .replace("%time%", String.valueOf(time));

            if(line.contains("%effect_line%")) {
                if(effects.size() < 1) {
                    player.sendMessage(GreatDevourer.messages.noEffects);
                } else {
                    showEffects(effects, player);
                }
            } else if(line.contains("%sound%")) {
                if(meta.getPersistentDataContainer().has(soundKey)) {
                    line = line.replace("%sound%", meta.getPersistentDataContainer().get(soundKey, PersistentDataType.STRING));
                    player.sendMessage(line);
                } else {
                    line = line.replace("%sound%", GreatDevourer.messages.noSound);
                    player.sendMessage(line);
                }
            } else {
                player.sendMessage(line);
            }
        }
    }

    public boolean addFoodEffect(ItemStack item, String effect, int duration, int level, float chance) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        FoodComponent foodComponent = meta.getFood();
        level = level - 1;
        duration = duration * 20;
        PotionEffectType potionEffectType = getEffectTypeByName(effect);
        if(potionEffectType == null) {
            return false;
        }

        PotionEffect potion = new PotionEffect(potionEffectType, duration, level);
        foodComponent.addEffect(potion, chance);
        meta.setFood(foodComponent);
        item.setItemMeta(meta);

        return true;
    }

    public boolean addFoodSound(ItemStack item, String sound, float volume, float pitch) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        sound = sound.toUpperCase();
        try {
            Sound.valueOf(sound);
        } catch(IllegalArgumentException ex) {
            return false;
        }

        NamespacedKey soundKey = new NamespacedKey(plugin, "foodSound");
        NamespacedKey volumeKey = new NamespacedKey(plugin, "foodVolume");
        NamespacedKey pitchKey = new NamespacedKey(plugin, "foodPitch");
        meta.getPersistentDataContainer().set(soundKey, PersistentDataType.STRING, sound);
        meta.getPersistentDataContainer().set(volumeKey, PersistentDataType.FLOAT, volume);
        meta.getPersistentDataContainer().set(pitchKey, PersistentDataType.FLOAT, pitch);
        item.setItemMeta(meta);

        return true;
    }

    private void showEffects(List<FoodComponent.FoodEffect> effects, Player player) {
        for(FoodComponent.FoodEffect effect : effects) {
            PotionEffect potionEffect = effect.getEffect();
            player.sendMessage(GreatDevourer.messages.effectLine.replace("&", "§")
                    .replace("%effect%", potionEffect.getType().getKey().getKey())
                    .replace("%duration%", String.valueOf(potionEffect.getDuration() / 20))
                    .replace("%level%", String.valueOf(potionEffect.getAmplifier() + 1))
                    .replace("%chance%", String.valueOf((int) (effect.getProbability() * 100))));
        }
    }

    private PotionEffectType getEffectTypeByName(String effect) {
        for(PotionEffectType potion : GreatDevourer.config.effects) {
            if(potion.getKey().getKey().equalsIgnoreCase(effect)) {
                return potion;
            }
        }

        return null;
    }
}
