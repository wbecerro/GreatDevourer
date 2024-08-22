package wbe.greatDevourer.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    private FileConfiguration config;

    public String noPermission;
    public String notEnoughArgs;
    public String reload;
    public String foodAdded;
    public String foodArguments;
    public String effectAdded;
    public String effectArguments;
    public String noEffectFound;
    public String soundAdded;
    public String soundArguments;
    public String noSoundFound;
    public List<String> get = new ArrayList<>();
    public String effectLine;
    public String noEffects;
    public String noSound;
    public List<String> help = new ArrayList<>();

    public Messages(FileConfiguration config) {
        this.config = config;

        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        reload = config.getString("Messages.reload").replace("&", "§");
        foodAdded = config.getString("Messages.foodAdded").replace("&", "§");
        foodArguments = config.getString("Messages.foodArguments").replace("&", "§");
        effectAdded = config.getString("Messages.effectAdded").replace("&", "§");
        effectArguments = config.getString("Messages.effectArguments").replace("&", "§");
        noEffectFound = config.getString("Messages.noEffectFound").replace("&", "§");
        soundAdded = config.getString("Messages.soundAdded").replace("&", "§");
        soundArguments = config.getString("Messages.soundArguments").replace("&", "§");
        noSoundFound = config.getString("Messages.noSoundFound").replace("&", "§");
        get = config.getStringList("Messages.get");
        effectLine = config.getString("Messages.effectLine").replace("&", "§");
        noEffects = config.getString("Messages.noEffects").replace("&", "§");
        noSound = config.getString("Messages.noSound").replace("&", "§");
        help = config.getStringList("Messages.help");
    }
}
