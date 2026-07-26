package rusplugins.neonukkitx.network.protocol.types;

public enum AuthInteractionModel {

    TOUCH,
    CROSSHAIR,
    CLASSIC;

    private static final AuthInteractionModel[] VALUES = values();

    public static AuthInteractionModel fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
}
