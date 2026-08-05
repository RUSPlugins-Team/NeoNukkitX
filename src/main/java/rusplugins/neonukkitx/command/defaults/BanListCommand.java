package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandEnum;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.permission.BanEntry;
import rusplugins.neonukkitx.permission.BanList;

import java.util.Iterator;
import java.util.Locale;

/**
 * Created on 2015/11/11 by xtypr.
 * Package rusplugins.neonukkitx.command.defaults in project Nukkit .
 */
public class BanListCommand extends VanillaCommand {

    public BanListCommand(String name) {
        super(name, "%neonukkitx.command.banlist.description", "%commands.banlist.usage");
        this.setPermission("neonukkitx.command.ban.list");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("type", true, new CommandEnum("BanListType", "ips", "players"))
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        BanList list;
        boolean ips = false;
        if (args.length > 0) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "ips":
                    list = sender.getServer().getIPBans();
                    ips = true;
                    break;
                case "players":
                    list = sender.getServer().getNameBans();
                    break;
                default:
                    sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
                    return false;
            }
        } else {
            list = sender.getServer().getNameBans();
        }

        StringBuilder builder = new StringBuilder();
        Iterator<BanEntry> itr = list.getEntires().values().iterator();
        while (itr.hasNext()) {
            builder.append(itr.next().getName());
            if (itr.hasNext()) {
                builder.append(", ");
            }
        }

        if (ips) {
            sender.sendMessage(new TranslationContainer("commands.banlist.ips", String.valueOf(list.getEntires().size())));
        } else {
            sender.sendMessage(new TranslationContainer("commands.banlist.players", String.valueOf(list.getEntires().size())));
        }
        sender.sendMessage(builder.toString());
        return true;
    }
}
