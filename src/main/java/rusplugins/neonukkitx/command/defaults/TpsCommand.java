package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.utils.TextFormat;

public class TpsCommand extends VanillaCommand {
    
    public TpsCommand(String name) {
        super(name, "%neonukkitx.command.tps.description", "%neonukkitx.command.tps.usage");
        this.setPermission("neonukkitx.command.tps");
    }
    
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) return true;
        
        float tps = sender.getServer().getTicksPerSecond();
        String color;
        if (tps >= 18.0) color = TextFormat.GREEN.toString();
        else if (tps >= 15.0) color = TextFormat.YELLOW.toString();
        else color = TextFormat.RED.toString();
        
        sender.sendMessage(TextFormat.GOLD + "╔══════════════════════════════════╗");
        sender.sendMessage(TextFormat.GOLD + "║" + TextFormat.AQUA + "     Server Performance          " + TextFormat.GOLD + "║");
        sender.sendMessage(TextFormat.GOLD + "╠══════════════════════════════════╣");
        sender.sendMessage(TextFormat.GOLD + "║ " + TextFormat.YELLOW + "TPS: " + color + String.format("%.2f", tps) + TextFormat.WHITE + " / 20.00");
        sender.sendMessage(TextFormat.GOLD + "║ " + TextFormat.YELLOW + "Load: " + color + String.format("%.1f%%", (20.0 - Math.min(tps, 20.0)) * 5.0));
        sender.sendMessage(TextFormat.GOLD + "╚══════════════════════════════════╝");
        
        return true;
    }
}
