package rusplugins.neonukkitx.plugin.internal;

import java.util.Arrays;

/**
 * Внутренний модуль NEONKX-Internal.
 * Системные внутренние компоненты ядра.
 */
public class NEONKXInternalModule extends InternalModule {

    public NEONKXInternalModule() {
        super(
            "NEONKX-Internal",
            "1.0.0.0",
            "Internal systems module for NeoNukkitX",
            Arrays.asList("NeoNukkitX Team"),
            "https://github.com/NeoNukkitX"
        );
    }

    @Override
    public void onEnable() {
        getLogger().info("NEONKX-Internal module enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("NEONKX-Internal module disabled");
    }
}
