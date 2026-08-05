package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandEnum;
import rusplugins.neonukkitx.command.data.CommandParamType;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.utils.TextFormat;

/**
 * Created on 2015/11/13 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class GamemodeCommand extends VanillaCommand {

    public GamemodeCommand(String name) {
        super(name, "%neonukkitx.command.gamemode.description", "%commands.gamemode.usage",
                new String[]{"gm"});
        this.setPermission("neonukkitx.command.gamemode.survival;" +
                "neonukkitx.command.gamemode.creative;" +
                "neonukkitx.command.gamemode.adventure;" +
                "neonukkitx.command.gamemode.spectator;" +
                "neonukkitx.command.gamemode.other");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("gameMode", CommandParamType.INT),
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        });
        this.commandParameters.put("byString", new CommandParameter[]{
                CommandParameter.newEnum("gameMode", CommandEnum.ENUM_GAMEMODE),
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return false;
        }

        int gameMode = Server.getGamemodeFromString(args[0]);
        if (gameMode == -1) {
            sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.gamemode.fail.invalid", args[0]));
            return true;
        }

        CommandSender target = sender;
        if (args.length > 1) {
            if (sender.hasPermission("neonukkitx.command.gamemode.other")) {
                target = sender.getServer().getPlayerExact(args[1].replace("@s", sender.getName()));
                if (target == null) {
                    sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                    return true;
                }
            } else {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.permission"));
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            return true;
        }

        if ((gameMode == 0 && !sender.hasPermission("neonukkitx.command.gamemode.survival")) ||
                (gameMode == 1 && !sender.hasPermission("neonukkitx.command.gamemode.creative")) ||
                (gameMode == 2 && !sender.hasPermission("neonukkitx.command.gamemode.adventure")) ||
                (gameMode == 3 && !sender.hasPermission("neonukkitx.command.gamemode.spectator"))) {
            sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.permission"));
            return true;
        }

        if (!((Player) target).setGamemode(gameMode)) {
            sender.sendMessage("Game mode update for " + target.getName() + " failed");
        } else {
            if (target.equals(sender)) {
                Command.broadcastCommandMessage(sender, new TranslationContainer("commands.gamemode.success.self", Server.getGamemodeString(gameMode)));
            } else {
                target.sendMessage(new TranslationContainer("gameMode.changed"));
                Command.broadcastCommandMessage(sender, new TranslationContainer("commands.gamemode.success.other", target.getName(), Server.getGamemodeString(gameMode)));
            }
        }

        return true;
    }
}
