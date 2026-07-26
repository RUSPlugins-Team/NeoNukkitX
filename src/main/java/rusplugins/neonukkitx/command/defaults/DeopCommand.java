package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.IPlayer;
import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandParamType;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.utils.TextFormat;

/**
 * Created on 2015/11/12 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class DeopCommand extends VanillaCommand {

    public DeopCommand(String name) {
        super(name, "%nukkit.command.deop.description", "%commands.deop.description");
        this.setPermission("nukkit.command.op.take");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.TARGET)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));

            return false;
        }

        String name = args[0].replace("@s", sender.getName());
        IPlayer player = sender.getServer().getOfflinePlayer(name);
        if (player instanceof Player) {
            player.setOp(false);
            ((Player) player).sendMessage(new TranslationContainer(TextFormat.GRAY + "%commands.deop.message"));
        } else {
            sender.getServer().removeOp(name);
        }

        Command.broadcastCommandMessage(sender, new TranslationContainer("commands.deop.success", new String[]{player.getName()}));
        return true;
    }
}
