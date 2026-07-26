package rusplugins.neonukkitx.network.session;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.network.CompressionProvider;
import rusplugins.neonukkitx.network.protocol.DataPacket;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public interface NetworkPlayerSession {

    void sendPacket(DataPacket packet);
    void sendImmediatePacket(DataPacket packet, Runnable callback);

    void disconnect(String reason);

    Player getPlayer();

    void setCompression(CompressionProvider compression);
    CompressionProvider getCompression();

    default void setEncryption(SecretKey encryptionKey, Cipher encryptionCipher, Cipher decryptionCipher) {

    }

    default long getPing() {
        return -1;
    }
}
