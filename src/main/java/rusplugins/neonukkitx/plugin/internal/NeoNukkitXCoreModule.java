package rusplugins.neonukkitx.plugin.internal;

import java.util.Arrays;

/**
 * Внутренний модуль NeoNukkitX-Core.
 * Ядро сервера как внутренний модуль.
 */
public class NeoNukkitXCoreModule extends InternalModule {

    public NeoNukkitXCoreModule() {
        super(
            "NeoNukkitX-Core",
            "1.0.0.0",
            "Core internal module of NeoNukkitX server software",
            Arrays.asList("NeoNukkitX Team"),
            "https://github.com/NeoNukkitX"
        );
    }

    @Override
    public void onEnable() {
        getLogger().info("NeoNukkitX-Core module enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("NeoNukkitX-Core module disabled");
    }
}
