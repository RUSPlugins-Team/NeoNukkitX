package rusplugins.neonukkitx.player;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.network.protocol.SetTitlePacket;
import rusplugins.neonukkitx.utils.TextFormat;

/**
 * Extended Player API for Titles, ActionBars, etc.
 * Does not modify Player class — uses composition.
 */
public class PlayerAPI {
    private final Player player;

    public PlayerAPI(Player player) {
        this.player = player;
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        // Set animation times first
        SetTitlePacket times = new SetTitlePacket();
        times.type = SetTitlePacket.TYPE_ANIMATION_TIMES;
        times.fadeInTime = fadeIn;
        times.stayTime = stay;
        times.fadeOutTime = fadeOut;
        player.dataPacket(times);

        // Send title
        SetTitlePacket pk = new SetTitlePacket();
        pk.type = SetTitlePacket.TYPE_TITLE;
        pk.text = TextFormat.colorize(title);
        player.dataPacket(pk);

        // Send subtitle if present
        if (subtitle != null && !subtitle.isEmpty()) {
            SetTitlePacket sub = new SetTitlePacket();
            sub.type = SetTitlePacket.TYPE_SUBTITLE;
            sub.text = TextFormat.colorize(subtitle);
            player.dataPacket(sub);
        }
    }

    public void sendActionBar(String message) {
        SetTitlePacket pk = new SetTitlePacket();
        pk.type = SetTitlePacket.TYPE_ACTION_BAR;
        pk.text = TextFormat.colorize(message);
        player.dataPacket(pk);
    }

    public void clearTitle() {
        SetTitlePacket pk = new SetTitlePacket();
        pk.type = SetTitlePacket.TYPE_CLEAR;
        player.dataPacket(pk);
    }
}
