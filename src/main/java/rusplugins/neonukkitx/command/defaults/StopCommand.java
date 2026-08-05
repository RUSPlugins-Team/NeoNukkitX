package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.lang.TranslationContainer;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class StopCommand extends VanillaCommand {

    public StopCommand(String name) {
        super(name, "%neonukkitx.command.stop.description", "%commands.stop.usage");
        this.setPermission("neonukkitx.command.stop");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        Command.broadcastCommandMessage(sender, new TranslationContainer("commands.stop.start"));

        sender.getServer().shutdown();

        return true;
    }
}
