package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.Command;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.utils.TextFormat;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ReloadCommand extends VanillaCommand {

    public ReloadCommand(String name) {
        super(name, "%neonukkitx.command.reload.description", "%commands.reload.usage");
        this.setPermission("neonukkitx.command.reload");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        Command.broadcastCommandMessage(sender, new TranslationContainer(TextFormat.YELLOW + "%neonukkitx.command.reload.reloading" + TextFormat.WHITE));

        sender.getServer().reload();

        Command.broadcastCommandMessage(sender, new TranslationContainer(TextFormat.YELLOW + "%neonukkitx.command.reload.reloaded" + TextFormat.WHITE));

        return true;
    }
}
