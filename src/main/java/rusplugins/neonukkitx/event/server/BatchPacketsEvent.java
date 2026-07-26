package rusplugins.neonukkitx.event.server;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.event.Cancellable;
import rusplugins.neonukkitx.event.HandlerList;
import rusplugins.neonukkitx.network.protocol.DataPacket;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class BatchPacketsEvent extends ServerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player[] players;
    private final DataPacket[] packets;

    public BatchPacketsEvent(Player[] players, DataPacket[] packets, boolean forceSync) {
        this.players = players;
        this.packets = packets;
    }

    public Player[] getPlayers() {
        return players;
    }

    public DataPacket[] getPackets() {
        return packets;
    }

    @Deprecated
    public boolean isForceSync() {
        return true;
    }
}
