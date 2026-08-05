package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.plugin.Plugin;
import rusplugins.neonukkitx.utils.TextFormat;

import java.util.Map;

/**
 * Created on 2015/11/12 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class PluginsCommand extends VanillaCommand {

    public PluginsCommand(String name) {
        super(name, "%neonukkitx.command.plugins.description", "%neonukkitx.command.plugins.usage");
        this.setPermission("neonukkitx.command.plugins");
        this.setAliases(new String[]{"pl"});
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        sendPluginList(sender);
        return true;
    }

    private static void sendPluginList(CommandSender sender) {
        StringBuilder list = new StringBuilder();
        Map<String, Plugin> plugins = sender.getServer().getPluginManager().getPlugins();
        for (Plugin plugin : plugins.values()) {
            if (list.length() > 0) {
                list.append(TextFormat.WHITE + ", ");
            }
            list.append(plugin.isEnabled() ? TextFormat.GREEN : TextFormat.RED + "*");
            list.append(plugin.getDescription().getFullName());
        }

        sender.sendMessage(new TranslationContainer("neonukkitx.command.plugins.success", String.valueOf(plugins.size()), list.toString()));
    }
}
