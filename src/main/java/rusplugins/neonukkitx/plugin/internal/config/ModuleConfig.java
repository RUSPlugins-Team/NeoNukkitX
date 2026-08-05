package rusplugins.neonukkitx.plugin.internal.config;

import java.io.*;
import java.util.Properties;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class ModuleConfig {

    private static final String FILE_NAME = "neonukkitx-modules.yml";
    private final Properties props = new Properties();
    private final File file;

    public ModuleConfig(String dataPath) {
        this.file = new File(dataPath, FILE_NAME);
        load();
    }

    private void load() {
        if (!file.exists()) {
            createDefault();
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("[ModuleConfig] Failed to load " + FILE_NAME + ": " + e.getMessage());
            createDefault();
        }
    }

    private void createDefault() {
        props.setProperty("module.NEONKX-Internal.enabled", "true");
        props.setProperty("module.NeoNukkitX-Core.enabled", "true");

        props.setProperty("system.AntiAFK.enabled", "true");
        props.setProperty("system.AntiBrake.enabled", "true");
        props.setProperty("system.AntiDDoS.enabled", "true");
        props.setProperty("system.AntiBot.enabled", "true");
        props.setProperty("system.AutoRestart.enabled", "true");
        props.setProperty("system.AutoTest.enabled", "true");

        save();
    }

    public void save() {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("# NeoNukkitX Module Configuration\n");
            fw.write("# Set any value to 'false' to disable the module or system\n\n");

            fw.write("# Modules\n");
            fw.write("module.NEONKX-Internal.enabled=" + props.getProperty("module.NEONKX-Internal.enabled", "true") + "\n");
            fw.write("module.NeoNukkitX-Core.enabled=" + props.getProperty("module.NeoNukkitX-Core.enabled", "true") + "\n\n");

            fw.write("# NEONKX-Internal Systems\n");
            fw.write("system.AntiAFK.enabled=" + props.getProperty("system.AntiAFK.enabled", "true") + "\n");
            fw.write("system.AntiBrake.enabled=" + props.getProperty("system.AntiBrake.enabled", "true") + "\n");
            fw.write("system.AntiDDoS.enabled=" + props.getProperty("system.AntiDDoS.enabled", "true") + "\n");
            fw.write("system.AntiBot.enabled=" + props.getProperty("system.AntiBot.enabled", "true") + "\n");
            fw.write("system.AutoRestart.enabled=" + props.getProperty("system.AutoRestart.enabled", "true") + "\n");
            fw.write("system.AutoTest.enabled=" + props.getProperty("system.AutoTest.enabled", "true") + "\n");
        } catch (IOException e) {
            System.err.println("[ModuleConfig] Failed to save " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public boolean isModuleEnabled(String name) {
        return Boolean.parseBoolean(props.getProperty("module." + name + ".enabled", "true"));
    }

    public boolean isSystemEnabled(String name) {
        return Boolean.parseBoolean(props.getProperty("system." + name + ".enabled", "true"));
    }

    public void setSystemEnabled(String name, boolean enabled) {
        props.setProperty("system." + name + ".enabled", String.valueOf(enabled));
        save();
    }
}
