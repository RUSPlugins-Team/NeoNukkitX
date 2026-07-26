package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandEnum;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.level.Level;

import java.util.Locale;

/**
 * Created on 2015/11/13 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class SaveCommand extends VanillaCommand {

    public SaveCommand(String name) {
        super(name, "%nukkit.command.save.description", "%commands.save.usage");
        this.setPermission("nukkit.command.save.perform");
        this.setAliases(new String[]{"save-all"});
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("mode", true, new CommandEnum("SaveMode", "on", "off", "hold", "resume"))
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length > 0) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "on":
                    sender.getServer().setAutoSave(true);
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.save.enabled"));
                    return true;
                case "off":
                    sender.getServer().setAutoSave(false);
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.save.disabled"));
                    return true;
                case "hold":
                    sender.getServer().holdWorldSave = true;
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.save.hold-on"));
                    return true;
                case "resume":
                    sender.getServer().holdWorldSave = false;
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.save.hold-off"));
                    return true;
                default:
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                    return false;
            }
        }

        Command.broadcastCommandMessage(sender, new TranslationContainer("commands.save.start"));

        for (Player player : sender.getServer().getOnlinePlayers().values()) {
            player.save();
        }

        for (Level level : sender.getServer().getLevels().values()) {
            level.save(true);
        }

        Command.broadcastCommandMessage(sender, new TranslationContainer("commands.save.success"));
        return true;
    }
}
