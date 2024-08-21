package wbe.greatDevourer.config;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private FileConfiguration config;

    public boolean baseAlwaysValue;
    public float baseTimeValue;
    public double baseVolumeValue;
    public double basePitchValue;

    public String foodLine;
    public String saturationLine;
    public String alwaysLine;
    public String timeLine;

    public Config(FileConfiguration config) {
        this.config = config;

        baseAlwaysValue = config.getBoolean("Config.baseAlwaysValue");
        baseTimeValue = (float) config.getDouble("Config.baseTimeValue");
        baseVolumeValue = config.getDouble("Config.baseVolumeValue");
        basePitchValue = config.getDouble("Config.basePitchValue");

        foodLine = config.getString("Items.foodLine").replace("&", "§");
        saturationLine = config.getString("Items.saturationLine").replace("&", "§");
        alwaysLine = config.getString("Items.alwaysLine").replace("&", "§");
        timeLine = config.getString("Items.timeLine").replace("&", "§");
    }
}
