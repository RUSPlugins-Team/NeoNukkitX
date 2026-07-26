package rusplugins.neonukkitx.entity;

import rusplugins.neonukkitx.Player;

/**
 * @author BeYkeRYkt
 * Nukkit Project
 */
public interface EntityOwnable {

    String getOwnerName();

    void setOwnerName(String playerName);

    Player getOwner();
}
