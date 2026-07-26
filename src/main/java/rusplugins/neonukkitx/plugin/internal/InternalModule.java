package rusplugins.neonukkitx.plugin.internal;

import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.PluginBase;
import rusplugins.neonukkitx.plugin.PluginDescription;
import rusplugins.neonukkitx.plugin.PluginLoader;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Базовый класс внутреннего модуля ядра NeoNukkitX.
 * Не требует файловой системы (нет jar, нет dataFolder).
 */
public abstract class InternalModule extends PluginBase {

    private final InternalPluginDescription internalDescription;

    public InternalModule(String name, String version, String description, java.util.List<String> authors, String website) {
        this.internalDescription = new InternalPluginDescription(name, version, description, authors, website);
    }

    /**
     * Инициализация внутреннего модуля без PluginLoader и файлов.
     */
    public final void initInternal(Server server) {
        try {
            Field descField = PluginBase.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(this, this.internalDescription);

            Field serverField = PluginBase.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(this, server);

            Field initField = PluginBase.class.getDeclaredField("initialized");
            initField.setAccessible(true);
            initField.set(this, true);

            Field loggerField = PluginBase.class.getDeclaredField("logger");
            loggerField.setAccessible(true);
            loggerField.set(this, new rusplugins.neonukkitx.plugin.PluginLogger(this));

            Field dataFolderField = PluginBase.class.getDeclaredField("dataFolder");
            dataFolderField.setAccessible(true);
            dataFolderField.set(this, null);

            Field fileField = PluginBase.class.getDeclaredField("file");
            fileField.setAccessible(true);
            fileField.set(this, null);

            Field loaderField = PluginBase.class.getDeclaredField("loader");
            loaderField.setAccessible(true);
            loaderField.set(this, null);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize internal module: " + internalDescription.getName(), e);
        }
    }

    @Override
    public final Server getServer() {
        try {
            Field serverField = PluginBase.class.getDeclaredField("server");
            serverField.setAccessible(true);
            return (Server) serverField.get(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final String getName() {
        return internalDescription.getName();
    }

    @Override
    public final File getFile() {
        return null;
    }

    @Override
    public final PluginLoader getPluginLoader() {
        return null;
    }

    @Override
    public final java.io.InputStream getResource(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    @Override
    public final boolean saveResource(String filename) {
        return false;
    }

    @Override
    public final boolean saveResource(String filename, boolean replace) {
        return false;
    }

    @Override
    public final boolean saveResource(String filename, String outputName, boolean replace) {
        return false;
    }

    @Override
    public final rusplugins.neonukkitx.utils.Config getConfig() {
        return null;
    }

    @Override
    public final void saveConfig() {
    }

    @Override
    public final void saveDefaultConfig() {
    }

    @Override
    public final void reloadConfig() {
    }
}
