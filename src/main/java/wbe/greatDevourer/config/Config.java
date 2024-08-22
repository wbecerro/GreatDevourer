package wbe.greatDevourer.config;

import org.bukkit.Registry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private FileConfiguration config;

    public boolean baseAlwaysValue;
    public float baseTimeValue;
    public float baseEffectChanceValue;
    public float baseVolumeValue;
    public float basePitchValue;

    public String foodLine;
    public String saturationLine;
    public String alwaysLine;
    public String timeLine;

    public List<PotionEffectType> effects = new ArrayList<>();

    public Config(FileConfiguration config) {
        this.config = config;

        baseAlwaysValue = config.getBoolean("Config.baseAlwaysValue");
        baseTimeValue = (float) config.getDouble("Config.baseTimeValue");
        baseEffectChanceValue = (float) config.getDouble("Config.baseEffectChanceValue");
        baseVolumeValue = (float) config.getDouble("Config.baseVolumeValue");
        basePitchValue = (float) config.getDouble("Config.basePitchValue");

        foodLine = config.getString("Items.foodLine").replace("&", "§");
        saturationLine = config.getString("Items.saturationLine").replace("&", "§");
        alwaysLine = config.getString("Items.alwaysLine").replace("&", "§");
        timeLine = config.getString("Items.timeLine").replace("&", "§");

        Registry.EFFECT.iterator().forEachRemaining(effects::add);
    }
}
