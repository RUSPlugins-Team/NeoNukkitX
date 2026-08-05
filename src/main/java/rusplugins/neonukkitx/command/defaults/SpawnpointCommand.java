package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandParamType;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.level.Position;
import rusplugins.neonukkitx.utils.TextFormat;

import java.text.DecimalFormat;

/**
 * Created on 2015/12/13 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class SpawnpointCommand extends VanillaCommand {

    public SpawnpointCommand(String name) {
        super(name, "%neonukkitx.command.spawnpoint.description", "%commands.spawnpoint.usage");
        this.setPermission("neonukkitx.command.spawnpoint");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", true, CommandParamType.TARGET),
                CommandParameter.newType("spawnPos", true, CommandParamType.POSITION),
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        Player target;
        if (args.length == 0) {
            if (sender instanceof Player) {
                target = (Player) sender;
            } else {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            }
        } else {
            target = sender.getServer().getPlayerExact(args[0].replace("@s", sender.getName()));
            if (target == null) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.player.notFound"));
                return true;
            }
        }
        Level level = target.getLevel();
        DecimalFormat round2 = new DecimalFormat("##0.00");
        if (args.length == 4) {
            if (level != null) {
                int x;
                int y;
                int z;
                try {
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                    z = Integer.parseInt(args[3]);
                } catch (NumberFormatException e1) {
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                    return true;
                }
                if (y < target.getLevel().getMinBlockY()) y = target.getLevel().getMinBlockY();
                if (y > target.getLevel().getMaxBlockY()) y = target.getLevel().getMaxBlockY();
                target.setSpawn(new Position(x, y, z, level));
                Command.broadcastCommandMessage(sender, new TranslationContainer("commands.spawnpoint.success", target.getName(),
                        round2.format(x),
                        round2.format(y),
                        round2.format(z)));
                return true;
            }
        } else if (args.length <= 1) {
            if (sender instanceof Player) {
                Position pos = (Position) sender;
                target.setSpawn(pos);
                Command.broadcastCommandMessage(sender, new TranslationContainer("commands.spawnpoint.success", target.getName(),
                        round2.format(pos.x),
                        round2.format(pos.y),
                        round2.format(pos.z)));
                return true;
            } else {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            }
        }
        sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
        return true;
    }
}