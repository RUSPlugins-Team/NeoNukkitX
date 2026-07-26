package rusplugins.neonukkitx.command.defaults;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.command.CommandSender;
import rusplugins.neonukkitx.command.data.CommandParamType;
import rusplugins.neonukkitx.command.data.CommandParameter;
import rusplugins.neonukkitx.lang.TranslationContainer;
import rusplugins.neonukkitx.level.Level;
import rusplugins.neonukkitx.math.Vector3;
import rusplugins.neonukkitx.network.protocol.PlaySoundPacket;

public class PlaySoundCommand extends VanillaCommand {

    public PlaySoundCommand(String name) {
        super(name, "%nukkit.command.playsound.description", "%commands.playsound.usage");
        this.setPermission("nukkit.command.playsound");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("sound", CommandParamType.STRING, false),
                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("%commands.playsound.usage", this.usageMessage));
            return false;
        }

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(new TranslationContainer("commands.generic.ingame"));
                return true;
            }

            Player p = (Player) sender;

            addSound(p.getLevel(), p, args[0], p);
            p.sendMessage(new TranslationContainer("commands.playsound.success", args[0], p.getName()));
            
            return true;
        }

        if (args[1].equalsIgnoreCase("@a")) {
            for (Player p : Server.getInstance().getOnlinePlayers().values()) {
                addSound(p.getLevel(), p, args[0], p);
            }

            sender.sendMessage(new TranslationContainer("commands.playsound.success", args[0], "@a"));

            return true;
        }

        if (args[1].equalsIgnoreCase("@s") && sender instanceof Player) {
            Player p = (Player) sender;

            addSound(p.getLevel(), p, args[0], p);
            sender.sendMessage(new TranslationContainer("commands.playsound.success", args[0], p.getName()));

            return true;
        }

        Player p = Server.getInstance().getPlayerExact(args[1]);

        if (p == null) {
            sender.sendMessage(new TranslationContainer("commands.generic.player.notFound"));
            return true;
        }

        addSound(p.getLevel(), p, args[0], p);
        sender.sendMessage(new TranslationContainer("commands.playsound.success", args[0], p.getName()));

        return true;
    }

    private static void addSound(Level level, Vector3 pos, String sound, Player... players) {
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = sound;
        packet.volume = 1f;
        packet.pitch = 1f;
        packet.x = pos.getFloorX();
        packet.y = pos.getFloorY();
        packet.z = pos.getFloorZ();

        if (players == null || players.length == 0) {
            level.addChunkPacket(pos.getChunkX(), pos.getChunkZ(), packet);
        } else {
            Server.broadcastPacket(players, packet);
        }
    }
}
