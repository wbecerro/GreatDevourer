package wbe.greatDevourer.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import wbe.greatDevourer.GreatDevourer;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    GreatDevourer plugin;

    public Utilities(GreatDevourer plugin) {
        this.plugin = plugin;
    }

    public void addFoodToItem(ItemStack item, int food, int saturation, boolean always, float time) {
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
}
