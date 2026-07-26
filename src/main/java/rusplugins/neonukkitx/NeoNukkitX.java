package rusplugins.neonukkitx;

import rusplugins.neonukkitx.utils.ServerKiller;
import com.google.common.base.Preconditions;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/*
    _   __           _   __      __   __   _ __ _  __
   ╱ │ ╱ ╱__  ____  ╱ │ ╱ ╱_  __╱ ╱__╱ ╱__(_) ╱│ │╱ ╱
  ╱  │╱ ╱ _ ╲╱ __ ╲╱  │╱ ╱ ╱ ╱ ╱ ╱╱_╱ ╱╱_╱ ╱ __╱   ╱ 
 ╱ ╱│  ╱  __╱ ╱_╱ ╱ ╱│  ╱ ╱_╱ ╱ ,< ╱ ,< ╱ ╱ ╱_╱   │  
╱_╱ │_╱╲___╱╲____╱_╱ │_╱╲__,_╱_╱│_╱_╱│_╱_╱╲__╱_╱│_│  
                                                     
 *                                              
 *  NeoNukkitX - Nuclear powered server software
 *  for Minecraft Bedrock Edition
 *  Author: RUSPlugins-Team LLC
 */

/**
 * The launcher class of NeoNukkitX, including the {@code main} function
 *
 * @author MagicDroidX(code) @ Nukkit Project
 * @author 粉鞋大妈(javadoc) @ Nukkit Project
 * @author RUSPlugins-Team LLC @ NeoNukkitX Project
 */
@Log4j2
public class NeoNukkitX {

    public final static Properties GIT_INFO = getGitInfo();
    public final static String VERSION = getVersion();
    public final static String API_VERSION = "1.1.0";
    public final static String PATH = System.getProperty("user.dir") + '/';
    public final static String DATA_PATH = System.getProperty("user.dir") + '/';
    public final static String PLUGIN_PATH = DATA_PATH + "plugins";
    /**
     * Server start time
     */
    public final static long START_TIME = System.currentTimeMillis();
    /**
     * Formatted first start date
     */
    public final static String FIRST_START = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(START_TIME));
    /**
     * Console title enabled
     */
    public static boolean TITLE = true;
    /**
     * Debug logging level
     */
    public static int DEBUG = 1;

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        System.setProperty("log4j.skipJansi", "false");

        // Disable memory pooling unless specified
        System.getProperties().putIfAbsent("io.netty.allocator.type", "unpooled");

        // Force Mapped ByteBuffers for LevelDB till fixed
        System.setProperty("leveldb.mmap", "true");

        // Define args
        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        OptionSpec<Void> helpSpec = parser.accepts("help", "Shows this page").forHelp();
        OptionSpec<Void> titleSpec = parser.accepts("enable-title", "Enables title at the top of the window");
        OptionSpec<String> vSpec = parser.accepts("v", "Set verbosity of logging").withRequiredArg().ofType(String.class);
        OptionSpec<String> verbositySpec = parser.accepts("verbosity", "Set verbosity of logging").withRequiredArg().ofType(String.class);
        OptionSpec<String> languageSpec = parser.accepts("language", "Set a predefined language").withOptionalArg().ofType(String.class);

        // Parse arguments
        OptionSet options = parser.parse(args);

        if (options.has(helpSpec)) {
            try {
                // Display help page
                parser.printHelpOn(System.out);
            } catch (IOException ignored) {
            }
            return;
        }

        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);

        TITLE = options.has(titleSpec);

        String verbosity = options.valueOf(vSpec);
        if (verbosity == null) {
            verbosity = options.valueOf(verbositySpec);
        }
        if (verbosity != null) {

            try {
                Level level = Level.valueOf(verbosity);
                setLogLevel(level);
            } catch (Exception ignored) {
            }
        }

        String language = options.valueOf(languageSpec);

        try {
            if (TITLE) {
                System.out.print((char) 0x1b + "]0;NeoNukkitX " + getVersion() + (char) 0x07);
            }
            new Server(PATH, DATA_PATH, PLUGIN_PATH, language);
        } catch (Throwable t) {
            log.throwing(t);
        }

        if (TITLE) {
            System.out.print((char) 0x1b + "]0;Stopping Server..." + (char) 0x07);
        }
        log.info("Stopping other threads...");

        for (Thread thread : java.lang.Thread.getAllStackTraces().keySet()) {
            if (!(thread instanceof InterruptibleThread)) {
                continue;
            }
            log.debug("Stopping {} thread", thread.getClass().getSimpleName());
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }

        ServerKiller killer = new ServerKiller(10);
        killer.start();

        if (TITLE) {
            System.out.print((char) 0x1b + "]0;Server Stopped" + (char) 0x07);
        }
        System.exit(0);
    }

    private static Properties getGitInfo() {
        InputStream gitFileStream = NeoNukkitX.class.getClassLoader().getResourceAsStream("git.properties");
        if (gitFileStream == null) {
            log.debug("Unable to find git.properties");
            return null;
        }
        Properties properties = new Properties();
        try {
            properties.load(gitFileStream);
        } catch (IOException e) {
            log.debug("Unable to load git.properties", e);
            return null;
        }
        return properties;
    }

    private static String getVersion() {
        StringBuilder version = new StringBuilder();
        version.append("git-");
        String commitId;
        if (GIT_INFO == null || (commitId = GIT_INFO.getProperty("git.commit.id.abbrev")) == null || commitId.isEmpty()) {
            return version.append("null").toString();
        }
        return version.append(commitId).toString();
    }

    public static void setLogLevel(Level level) {
        Preconditions.checkNotNull(level, "level");
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        LoggerConfig loggerConfig = ctx.getConfiguration().getLoggerConfig(org.apache.logging.log4j.LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(level);
        ctx.updateLoggers();
    }

    public static Level getLogLevel() {
        return ((LoggerContext) LogManager.getContext(false)).getConfiguration().getLoggerConfig(org.apache.logging.log4j.LogManager.ROOT_LOGGER_NAME).getLevel();
    }
}
