package rusplugins.neonukkitx.plugin.internal.autotest;

import rusplugins.neonukkitx.Player;
import rusplugins.neonukkitx.Server;
import rusplugins.neonukkitx.plugin.internal.NEONKXInternalModule;
import rusplugins.neonukkitx.scheduler.NeoScheduler;
import rusplugins.neonukkitx.scheduler.TaskHandler;
import rusplugins.neonukkitx.network.protocol.PlayerListPacket;
import rusplugins.neonukkitx.entity.data.Skin;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
public class AutoTestSystem implements Runnable {

    private final NEONKXInternalModule module;
    private TaskHandler task;

    private static final int CHECK_INTERVAL = 20 * 60 * 60;
    private static final long TEST_DURATION = 10 * 60 * 1000;
    private static final long COOLDOWN_DURATION = 2 * 60 * 1000;
    private static final String STATE_FILE = "autotest.state";

    private Phase phase = Phase.IDLE;
    private long phaseStart = 0;
    private PrintWriter reportWriter;
    private final List<UUID> fakeUuids = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    private enum Phase { IDLE, RUNNING, COOLDOWN }

    public AutoTestSystem(NEONKXInternalModule module) {
        this.module = module;
    }

    public void start() {
        this.task = NeoScheduler.runRepeating(module, this, CHECK_INTERVAL);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
        cleanupFakePlayers();
        closeReport();
    }

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (phase == Phase.IDLE) {
            if (day == Calendar.FRIDAY && hour == 3 && !isTestedThisWeek()) {
                startTest();
            }
        } else if (phase == Phase.RUNNING) {
            if (System.currentTimeMillis() - phaseStart >= TEST_DURATION) {
                endTest();
            } else {
                continueTest();
            }
        } else if (phase == Phase.COOLDOWN) {
            if (System.currentTimeMillis() - phaseStart >= COOLDOWN_DURATION) {
                finishCooldown();
            }
        }
    }

    private boolean isTestedThisWeek() {
        long last = loadLastTestTime();
        if (last == 0) return false;
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTimeInMillis(last);
        Calendar now = Calendar.getInstance();
        return lastCal.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR)
                && lastCal.get(Calendar.YEAR) == now.get(Calendar.YEAR);
    }

    private long loadLastTestTime() {
        File f = new File(STATE_FILE);
        if (!f.exists()) return 0;
        try (Scanner sc = new Scanner(f)) {
            return sc.nextLong();
        } catch (Exception e) {
            return 0;
        }
    }

    private void saveLastTestTime(long time) {
        try (FileWriter fw = new FileWriter(STATE_FILE)) {
            fw.write(String.valueOf(time));
        } catch (IOException ignored) {}
    }

    private void startTest() {
        phase = Phase.RUNNING;
        phaseStart = System.currentTimeMillis();
        saveLastTestTime(phaseStart);

        module.getLogger().info("[AutoTest] Core Activate");

        String fileName = "autotest-report-" + dateFormat.format(new Date()) + ".log";
        try {
            reportWriter = new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            module.getLogger().error("[AutoTest] Failed to create report file", e);
        }

        report("=== NeoNukkitX AutoTest Started ===");
        report("Timestamp: " + new Date());

        runPingTest();
        runPortTest();
        runConfigTest();
        runFakeOnlineTest();
    }

    private void continueTest() {
        long elapsed = System.currentTimeMillis() - phaseStart;
        if (elapsed % (60 * 1000) < (CHECK_INTERVAL * 50)) {
            runLagDetection();
        }
    }

    private void endTest() {
        report("=== Test Phase Complete ===");
        runLagDetection();
        cleanupFakePlayers();
        phase = Phase.COOLDOWN;
        phaseStart = System.currentTimeMillis();
    }

    private void finishCooldown() {
        report("=== AutoTest Finished ===");
        closeReport();
        phase = Phase.IDLE;
    }

    private void runPingTest() {
        report("[Ping Test]");
        try {
            InetAddress target = InetAddress.getByName("127.0.0.1");
            for (int i = 1; i <= 5; i++) {
                long start = System.nanoTime();
                boolean reachable = target.isReachable(3000);
                long ms = (System.nanoTime() - start) / 1_000_000;
                report("  Ping " + i + ": " + ms + "ms, reachable=" + reachable);
            }
        } catch (Exception e) {
            report("  ERROR: " + e.getMessage());
        }
    }

    private void runPortTest() {
        report("[Port Test]");
        Server server = Server.getInstance();
        int port = server.getPort();
        String ip = server.getIp();
        if (ip == null || ip.isEmpty()) ip = "127.0.0.1";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 5000);
            report("  Port " + port + " on " + ip + ": OPEN");
        } catch (Exception e) {
            report("  Port " + port + " on " + ip + ": CLOSED (" + e.getMessage() + ")");
        }
    }

    private void runConfigTest() {
        report("[Config Test]");
        Server server = Server.getInstance();
        report("  MOTD: " + server.getMotd());
        report("  Max Players: " + server.getMaxPlayers());
        report("  Port: " + server.getPort());
        report("  IP: " + (server.getIp().isEmpty() ? "0.0.0.0" : server.getIp()));
        report("  Online Players: " + server.getOnlinePlayers().size());
        report("  TPS: " + server.getTicksPerSecond());
    }

    private void runFakeOnlineTest() {
        report("[Fake Online Stress Test]");
        Server server = Server.getInstance();
        int max = server.getMaxPlayers();
        int fakeCount = Math.min(max, 50);

        report("  Injecting " + fakeCount + " fake players into tab list");

        PlayerListPacket addPacket = new PlayerListPacket();
        addPacket.type = PlayerListPacket.TYPE_ADD;

        List<PlayerListPacket.Entry> entries = new ArrayList<>();
        for (int i = 0; i < fakeCount; i++) {
            UUID uuid = UUID.randomUUID();
            fakeUuids.add(uuid);
            Skin skin = new Skin();
            PlayerListPacket.Entry entry = new PlayerListPacket.Entry(
                uuid,
                1000000L + i,
                "TestBot_" + i,
                skin,
                ""
            );
            entries.add(entry);
        }

        addPacket.entries = entries.toArray(new PlayerListPacket.Entry[0]);

        for (Player player : server.getOnlinePlayers().values()) {
            player.dataPacket(addPacket);
        }

        report("  Fake players injected. Monitoring TPS...");
    }

    private void runLagDetection() {
        report("[Lag Detection]");
        Server server = Server.getInstance();
        float tps = server.getTicksPerSecond();
        long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long used = total - free;

        report("  TPS: " + tps);
        report("  Memory: " + used + "MB / " + total + "MB");

        if (tps < 15.0f) {
            report("  WARNING: Low TPS detected!");
        }
        if (used > total * 0.9) {
            report("  WARNING: High memory usage!");
        }

        Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
        report("  Active Threads: " + stacks.size());
    }

    private void cleanupFakePlayers() {
        if (fakeUuids.isEmpty()) return;

        report("[Cleanup] Removing fake players");
        Server server = Server.getInstance();

        PlayerListPacket removePacket = new PlayerListPacket();
        removePacket.type = PlayerListPacket.TYPE_REMOVE;

        List<PlayerListPacket.Entry> entries = new ArrayList<>();
        for (UUID uuid : fakeUuids) {
            entries.add(new PlayerListPacket.Entry(uuid));
        }

        removePacket.entries = entries.toArray(new PlayerListPacket.Entry[0]);

        for (Player player : server.getOnlinePlayers().values()) {
            player.dataPacket(removePacket);
        }

        fakeUuids.clear();
    }

    private void report(String line) {
        if (reportWriter != null) {
            reportWriter.println(line);
            reportWriter.flush();
        }
    }

    private void closeReport() {
        if (reportWriter != null) {
            reportWriter.close();
            reportWriter = null;
        }
    }
}
