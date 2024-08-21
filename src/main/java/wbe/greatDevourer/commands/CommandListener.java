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

                int foodValue = 0;
                int saturationValue = 0;
                boolean always = GreatDevourer.config.baseAlwaysValue;
                float time = GreatDevourer.config.baseTimeValue;
                if(args.length == 5) {
                    foodValue = Integer.valueOf(args[1]);
                    saturationValue = Integer.valueOf(args[2]);
                    always = Boolean.valueOf(args[3]);
                    time = Float.valueOf(args[4]);
                } else if(args.length == 4) {
                    foodValue = Integer.valueOf(args[1]);
                    saturationValue = Integer.valueOf(args[2]);
                    always = Boolean.valueOf(args[3]);
                } else {
                    foodValue = Integer.valueOf(args[1]);
                    saturationValue = Integer.valueOf(args[2]);
                }

                utilities.addFoodToItem(player.getInventory().getItemInMainHand(), foodValue, saturationValue,
                        always, time);
                player.updateInventory();
            }
        }
        return true;
    }
}
