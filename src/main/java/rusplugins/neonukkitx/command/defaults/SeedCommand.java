package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.lang.TranslationContainer;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class SeedCommand extends VanillaCommand {

    public SeedCommand(String name) {
        super(name, "%neonukkitx.command.seed.description", "%commands.seed.usage");
        this.setPermission("neonukkitx.command.seed");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        long seed;
        if (sender instanceof Player) {
            seed = ((Player) sender).getLevel().getSeed();
        } else {
            seed = sender.getServer().getDefaultLevel().getSeed();
        }

        sender.sendMessage(new TranslationContainer("commands.seed.success", String.valueOf(seed)));

        return true;
    }
}
