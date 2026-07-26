package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandParamType;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.network.protocol.ProtocolInfo;
import rusplugins.neonukkitx.plugin.Plugin;
import rusplugins.neonukkitx.plugin.PluginDescription;
import rusplugins.neonukkitx.utils.TextFormat;
import rusplugins.neonukkitx.NeoNukkitX;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created on 2015/11/12 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project NeoNukkitX .
 */
public class VersionCommand extends VanillaCommand {

    public VersionCommand(String name) {
        super(name,
                "%neonukkitx.command.version.description",
                "%neonukkitx.command.version.usage",
                new String[]{"ver", "about"}
        );
        this.setPermission("neonukkitx.command.version");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("pluginName", true, CommandParamType.STRING)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        
        if (args.length == 0 || !sender.hasPermission("neonukkitx.command.version.plugins")) {
            // Красивый вывод NeoNukkitX
            String firstStart = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(NeoNukkitX.START_TIME));
            String lastStart = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
            
            sender.sendMessage(TextFormat.GREEN + "╔══════════════════════════════════════╗");
            sender.sendMessage(TextFormat.GREEN + "║" + TextFormat.AQUA + "     Information NeoNukkitX Server    " + TextFormat.GREEN + "║");
            sender.sendMessage(TextFormat.GREEN + "╠══════════════════════════════════════╣");
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "Server: " + TextFormat.WHITE + sender.getServer().getMotd());
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "API: " + TextFormat.WHITE + sender.getServer().getApiVersion());
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "Version: " + TextFormat.WHITE + sender.getServer().getNukkitVersion());
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "Version Minecraft: " + TextFormat.WHITE + ProtocolInfo.MINECRAFT_VERSION);
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "Protocol: " + TextFormat.WHITE + ProtocolInfo.CURRENT_PROTOCOL);
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "Author: " + TextFormat.WHITE + "RUSPlugins-Team LLC");
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "First start: " + TextFormat.WHITE + firstStart);
            sender.sendMessage(TextFormat.GREEN + "║ " + TextFormat.YELLOW + "Last start: " + TextFormat.WHITE + lastStart);
            sender.sendMessage(TextFormat.GREEN + "╚══════════════════════════════════════╝");
        } else {
            // ... остальной код для плагинов без изменений ...
            StringBuilder pluginName = new StringBuilder();
            for (String arg : args) pluginName.append(arg).append(' ');
            pluginName = new StringBuilder(pluginName.toString().trim());
            final boolean[] found = {false};
            final Plugin[] exactPlugin = {sender.getServer().getPluginManager().getPlugin(pluginName.toString())};

            if (exactPlugin[0] == null) {
                pluginName = new StringBuilder(pluginName.toString().toLowerCase(Locale.ROOT));
                final String finalPluginName = pluginName.toString();
                sender.getServer().getPluginManager().getPlugins().forEach((s, p) -> {
                    if (s.toLowerCase(Locale.ROOT).contains(finalPluginName)) {
                        exactPlugin[0] = p;
                        found[0] = true;
                    }
                });
            } else {
                found[0] = true;
            }

            if (found[0]) {
                PluginDescription desc = exactPlugin[0].getDescription();
                sender.sendMessage(TextFormat.DARK_GREEN + desc.getName() + TextFormat.WHITE + " version " + TextFormat.DARK_GREEN + desc.getVersion());
                if (desc.getDescription() != null) {
                    sender.sendMessage(desc.getDescription());
                }
                if (desc.getWebsite() != null) {
                    sender.sendMessage("Website: " + desc.getWebsite());
                }
                List<String> authors = desc.getAuthors();
                final String[] authorsString = {""};
                authors.forEach((s) -> authorsString[0] += s);
                if (authors.size() == 1) {
                    sender.sendMessage("Author: " + authorsString[0]);
                } else if (authors.size() >= 2) {
                    sender.sendMessage("Authors: " + String.join(", ", authors));
                }
            } else {
                sender.sendMessage(new TranslationContainer("neonukkitx.command.version.noSuchPlugin"));
            }
        }
        return true;
    }
}
