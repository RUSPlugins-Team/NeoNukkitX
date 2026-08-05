package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandEnum;
import rusplugins.neonukkitx.command.data.CommandParamType;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.utils.TextFormat;

import java.util.Locale;

/**
 * Created on 2015/11/12 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class WhitelistCommand extends VanillaCommand {

    public WhitelistCommand(String name) {
        super(name, "%neonukkitx.command.whitelist.description", "%commands.whitelist.usage");
        this.setPermission(
                "neonukkitx.command.whitelist.reload;" +
                        "neonukkitx.command.whitelist.enable;" +
                        "neonukkitx.command.whitelist.disable;" +
                        "neonukkitx.command.whitelist.list;" +
                        "neonukkitx.command.whitelist.add;" +
                        "neonukkitx.command.whitelist.remove"
        );
        this.commandParameters.clear();
        this.commandParameters.put("1arg", new CommandParameter[]{
                CommandParameter.newEnum("action", new CommandEnum("WhitelistAction", "on", "off", "list", "reload"))
        });
        this.commandParameters.put("2args", new CommandParameter[]{
                CommandParameter.newEnum("action", new CommandEnum("WhitelistPlayerAction", "add", "remove")),
                CommandParameter.newType("player", CommandParamType.TARGET)
        });
    }


    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return true;
        }

        if (args.length == 1) {
            if (badPerm(sender, args[0].toLowerCase(Locale.ROOT))) {
                return false;
            }
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "reload":
                    sender.getServer().reloadWhitelist();
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.whitelist.reloaded"));
                    return true;
                case "on":
                    sender.getServer().setPropertyBoolean("white-list", true);
                    sender.getServer().whitelistEnabled = true;
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.whitelist.enabled"));
                    return true;
                case "off":
                    sender.getServer().setPropertyBoolean("white-list", false);
                    sender.getServer().whitelistEnabled = false;
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.whitelist.disabled"));
                    return true;
                case "list":
                    StringBuilder result = new StringBuilder();
                    int count = 0;
                    for (String player : sender.getServer().getWhitelist().getAll().keySet()) {
                        result.append(player).append(", ");
                        ++count;
                    }
                    sender.sendMessage(new TranslationContainer("commands.whitelist.list", String.valueOf(count), String.valueOf(count)));
                    sender.sendMessage(result.length() > 0 ? result.substring(0, result.length() - 2) : "");

                    return true;

                case "add":
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", "%commands.whitelist.add.usage"));
                    return true;

                case "remove":
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", "%commands.whitelist.remove.usage"));
                    return true;
            }
        } else {
            if (badPerm(sender, args[0].toLowerCase(Locale.ROOT))) {
                return false;
            }
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "add":
                    sender.getServer().getOfflinePlayer(args[1]).setWhitelisted(true);
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.whitelist.add.success", args[1]));

                    return true;
                case "remove":
                    sender.getServer().getOfflinePlayer(args[1]).setWhitelisted(false);
                    Command.broadcastCommandMessage(sender, new TranslationContainer("commands.whitelist.remove.success", args[1]));

                    return true;
            }
        }

        return true;
    }

    private static boolean badPerm(CommandSender sender, String perm) {
        if (!sender.hasPermission("neonukkitx.command.whitelist." + perm)) {
            sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.permission"));

            return true;
        }

        return false;
    }
}
