package rusplugins.neonukkitx.entity;

import rusplugins.neonukkitx.Player;

/**
 * @author Adam Matthew
 */
public interface EntityInteractable {

    String getInteractButtonText();

    default String getInteractButtonText(Player player) {
        return this.getInteractButtonText();
    }

    boolean canDoInteraction();
}
