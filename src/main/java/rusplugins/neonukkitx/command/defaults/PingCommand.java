package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.utils.TextFormat;

public class PingCommand extends VanillaCommand {

    public PingCommand(String name) {
        super(name, "%neonukkitx.command.ping.description", "%neonukkitx.command.ping.usage");
        this.setPermission("neonukkitx.command.ping");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "This command can only be used in-game!");
            return true;
        }

        Player player = (Player) sender;
        int ping = player.getPing();

        String color;
        if (ping <= 50) {
            color = TextFormat.GREEN.toString();
        } else if (ping <= 100) {
            color = TextFormat.YELLOW.toString();
        } else if (ping <= 200) {
            color = TextFormat.GOLD.toString();
        } else {
            color = TextFormat.RED.toString();
        }

        sender.sendMessage(TextFormat.GOLD + "╔══════════════════════════════════╗");
        sender.sendMessage(TextFormat.GOLD + "║" + TextFormat.AQUA + "          Your Ping               " + TextFormat.GOLD + "║");
        sender.sendMessage(TextFormat.GOLD + "╠══════════════════════════════════╣");
        sender.sendMessage(TextFormat.GOLD + "║ " + TextFormat.YELLOW + "Ping: " + color + ping + TextFormat.WHITE + " ms");
        sender.sendMessage(TextFormat.GOLD + "╚══════════════════════════════════╝");

        return true;
    }
}
