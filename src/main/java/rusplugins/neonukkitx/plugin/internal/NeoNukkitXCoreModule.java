package rusplugins.neonukkitx.plugin.internal;

import rusplugins.neonukkitx.NeoNukkitX;
import rusplugins.neonukkitx.plugin.internal.config.ModuleConfig;

import java.util.Arrays;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class NeoNukkitXCoreModule extends InternalModule {

    public NeoNukkitXCoreModule() {
        super(
            "NeoNukkitX-Core",
            "1.1.0.0",
            "Core internal module of NeoNukkitX server software",
            Arrays.asList("NeoNukkitX Team"),
            "https://github.com/NeoNukkitX"
        );
    }

    @Override
    public void onEnable() {
        ModuleConfig config = new ModuleConfig(NeoNukkitX.DATA_PATH);
        if (!config.isModuleEnabled("NeoNukkitX-Core")) {
            getLogger().info("NeoNukkitX-Core module is disabled in neonukkitx-modules.yml");
            return;
        }
        getLogger().info("NeoNukkitX-Core module enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("NeoNukkitX-Core module disabled");
    }
}
