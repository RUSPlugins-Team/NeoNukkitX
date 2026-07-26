package rusplugins.neonukkitx.entity;

import rusplugins.neonukkitx.Player;

public interface EntityControllable {

    void onPlayerInput(Player player, double strafe, double forward);

    default void onJump(Player player, int duration) {
    }
}
