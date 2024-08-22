package wbe.greatDevourer.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wbe.greatDevourer.GreatDevourer;
import wbe.greatDevourer.util.Utilities;

public class CommandListener implements CommandExecutor {

    private GreatDevourer plugin;

    private FileConfiguration config;

    private Utilities utilities;

    public CommandListener(GreatDevourer plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();
        this.utilities = new Utilities(plugin);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("GreatDevourer")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }
            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("greatdevourer.command.help")) {
                    sender.sendMessage(GreatDevourer.messages.noPermission);
                    return false;
                }

                for(String line : GreatDevourer.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("greatdevourer.command.reload")) {
                    sender.sendMessage(GreatDevourer.messages.noPermission);
                    return false;
                }

                plugin.reloadConfiguration();
                sender.sendMessage(GreatDevourer.messages.reload);
            } else if(args[0].equalsIgnoreCase("food")) {
                if(!sender.hasPermission("greatdevourer.command.food")) {
                    sender.sendMessage(GreatDevourer.messages.noPermission);
                    return false;
                }

                if(args.length < 3) {
                    sender.sendMessage(GreatDevourer.messages.notEnoughArgs);
                    sender.sendMessage(GreatDevourer.messages.foodArguments);
                    return false;
                }

                int foodValue = Integer.valueOf(args[1]);
                float saturationValue = Float.valueOf(args[2]);
                boolean always = GreatDevourer.config.baseAlwaysValue;
                float time = GreatDevourer.config.baseTimeValue;
                if(args.length == 5) {
                    always = Boolean.valueOf(args[3]);
                    time = Float.valueOf(args[4]);
                } else if(args.length == 4) {
                    always = Boolean.valueOf(args[3]);
                }

                utilities.addFoodToItem(player.getInventory().getItemInMainHand(), foodValue, saturationValue,
                        always, time);
                player.updateInventory();
            } else if(args[0].equalsIgnoreCase("show")) {
                if(!sender.hasPermission("greatdevourer.command.show")) {
                    sender.sendMessage(GreatDevourer.messages.noPermission);
                    return false;
                }

                utilities.showItemNutritionalInfo(player.getInventory().getItemInMainHand(), player);
            } else if(args[0].equalsIgnoreCase("effect")) {
                if(!sender.hasPermission("greatdevourer.command.effect")) {
                    sender.sendMessage(GreatDevourer.messages.noPermission);
                    return false;
                }

                if(args.length < 4) {
                    sender.sendMessage(GreatDevourer.messages.notEnoughArgs);
                    sender.sendMessage(GreatDevourer.messages.effectArguments);
                    return false;
                }

                float effectChance = GreatDevourer.config.baseEffectChanceValue;
                if(args.length == 5) {
                    effectChance = Float.valueOf(args[4]);
                }

                boolean added = utilities.addFoodEffect(player.getInventory().getItemInMainHand(), args[1],
                        Integer.valueOf(args[2]), Integer.valueOf(args[3]), effectChance);
                if(added) {
                    player.sendMessage(GreatDevourer.messages.effectAdded);
                } else {
                    player.sendMessage(GreatDevourer.messages.noEffectFound.replace("%effect%", args[1]));
                }
            } else if(args[0].equalsIgnoreCase("sound")) {
                if(!sender.hasPermission("greatdevourer.command.sound")) {
                    sender.sendMessage(GreatDevourer.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(GreatDevourer.messages.notEnoughArgs);
                    sender.sendMessage(GreatDevourer.messages.soundArguments);
                    return false;
                }

                float volume = GreatDevourer.config.baseVolumeValue;
                float pitch = GreatDevourer.config.basePitchValue;
                if(args.length == 4) {
                    volume = Float.valueOf(args[2]);
                    pitch = Float.valueOf(args[3]);
                } else if(args.length == 3) {
                    volume = Float.valueOf(args[2]);
                }

                boolean added = utilities.addFoodSound(player.getInventory().getItemInMainHand(),
                        args[1], volume, pitch);
                if(added) {
                    player.sendMessage(GreatDevourer.messages.soundAdded);
                } else {
                    player.sendMessage(GreatDevourer.messages.noSoundFound.replace("%sound%", args[1]));
                }
            }
        }
        return true;
    }
}
