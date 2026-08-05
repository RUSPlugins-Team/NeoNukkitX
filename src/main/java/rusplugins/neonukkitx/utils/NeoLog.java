package rusplugins.neonukkitx.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NeoLog {
    private static final File LOG_DIR = new File("logs");
    private static final File LOG_FILE = new File(LOG_DIR, "neolog.yml");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        if (!LOG_DIR.exists()) {
            LOG_DIR.mkdirs();
            System.out.println("[NeoLog] Creating file log " + LOG_FILE.getPath());
        }
        if (!LOG_FILE.exists()) {
            try {
                LOG_FILE.createNewFile();
                info("Log file initialized");
            } catch (IOException e) {
                System.err.println("[NeoLog] Failed to create log file: " + e.getMessage());
            }
        }
    }

    public static void info(String message) {
        write("INFO", message);
    }

    public static void fatal(String message, String file, int line) {
        write("FATAL", message + " in " + file + ":" + line);
    }

    public static void fatal(String message) {
        write("FATAL", message);
    }

    private static synchronized void write(String level, String message) {
        String timestamp = DATE_FORMAT.format(new Date());
        String line = "[" + timestamp + "] [" + level + "] " + message;
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(line);
        } catch (IOException e) {
            System.err.println("[NeoLog] Failed to write log: " + e.getMessage());
        }
    }
}
