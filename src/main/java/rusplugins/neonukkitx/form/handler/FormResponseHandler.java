package rusplugins.neonukkitx.form.handler;

import rusplugins.neonukkitx.Player;

import java.util.function.IntConsumer;

public interface FormResponseHandler {

    static FormResponseHandler withoutPlayer(IntConsumer formIDConsumer) {
        return (player, formID) -> formIDConsumer.accept(formID);
    }

    void handle(Player player, int formID);
}
