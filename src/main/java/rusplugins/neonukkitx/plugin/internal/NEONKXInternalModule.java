package rusplugins.neonukkitx.plugin.internal;

import rusplugins.neonukkitx.NeoNukkitX;
import rusplugins.neonukkitx.plugin.internal.antiafk.AntiAFKSystem;
import rusplugins.neonukkitx.plugin.internal.antibrake.AntiBrakeSystem;
import rusplugins.neonukkitx.plugin.internal.antiddos.AntiDDoSSystem;
import rusplugins.neonukkitx.plugin.internal.antibot.AntiBotSystem;
import rusplugins.neonukkitx.plugin.internal.autorestart.AutoRestartSystem;
import rusplugins.neonukkitx.plugin.internal.autotest.AutoTestSystem;
import rusplugins.neonukkitx.plugin.internal.config.ModuleConfig;

import java.util.Arrays;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class NEONKXInternalModule extends InternalModule {

    private AntiAFKSystem antiAFK;
    private AntiBrakeSystem antiBrake;
    private AntiDDoSSystem antiDDoS;
    private AntiBotSystem antiBot;
    private AutoRestartSystem autoRestart;
    private AutoTestSystem autoTest;
    private ModuleConfig config;

    public NEONKXInternalModule() {
        super(
            "NEONKX-Internal",
            "1.1.0.0",
            "Internal systems module for NeoNukkitX",
            Arrays.asList("NeoNukkitX Team"),
            "https://github.com/NeoNukkitX"
        );
    }

    @Override
    public void onEnable() {
        this.config = new ModuleConfig(NeoNukkitX.DATA_PATH);

        if (!config.isModuleEnabled("NEONKX-Internal")) {
            getLogger().info("NEONKX-Internal module is disabled in neonukkitx-modules.yml");
            return;
        }

        if (config.isSystemEnabled("AntiAFK")) {
            this.antiAFK = new AntiAFKSystem(this);
            this.antiAFK.start();
            getLogger().info("AntiAFK System: active");
        } else {
            getLogger().info("AntiAFK System: disabled");
        }

        if (config.isSystemEnabled("AntiBrake")) {
            this.antiBrake = new AntiBrakeSystem(this);
            this.antiBrake.start();
            getLogger().info("AntiBrake System: active");
        } else {
            getLogger().info("AntiBrake System: disabled");
        }

        if (config.isSystemEnabled("AntiDDoS")) {
            this.antiDDoS = new AntiDDoSSystem(this);
            this.antiDDoS.start();
            getLogger().info("AntiDDoS System: active");
        } else {
            getLogger().info("AntiDDoS System: disabled");
        }

        if (config.isSystemEnabled("AntiBot")) {
            this.antiBot = new AntiBotSystem(this);
            this.antiBot.start();
            getLogger().info("AntiBot System: active");
        } else {
            getLogger().info("AntiBot System: disabled");
        }

        if (config.isSystemEnabled("AutoRestart")) {
            this.autoRestart = new AutoRestartSystem(this);
            this.autoRestart.start();
            getLogger().info("AutoRestart System: active (24h cycle)");
        } else {
            getLogger().info("AutoRestart System: disabled");
        }

        if (config.isSystemEnabled("AutoTest")) {
            this.autoTest = new AutoTestSystem(this);
            this.autoTest.start();
            getLogger().info("AutoTest System: active (Fridays 03:00)");
        } else {
            getLogger().info("AutoTest System: disabled");
        }

        getLogger().info("NEONKX-Internal module enabled");
    }

    @Override
    public void onDisable() {
        if (this.antiAFK != null) this.antiAFK.stop();
        if (this.antiBrake != null) this.antiBrake.stop();
        if (this.antiDDoS != null) this.antiDDoS.stop();
        if (this.antiBot != null) this.antiBot.stop();
        if (this.autoRestart != null) this.autoRestart.stop();
        if (this.autoTest != null) this.autoTest.stop();
        getLogger().info("NEONKX-Internal module disabled");
    }
}
