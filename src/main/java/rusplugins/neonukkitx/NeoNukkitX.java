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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 * @author NeoNukkitX Project & RUSPlugins-Team LLC
 */
@Log4j2
public class NeoNukkitX {

    public final static Properties GIT_INFO = getGitInfo();
    public final static String VERSION = getVersion();
    public final static String API_VERSION = "1.1.0";
    public final static String PATH = System.getProperty("user.dir") + '/';
    public final static String DATA_PATH = System.getProperty("user.dir") + '/';
    public final static String PLUGIN_PATH = DATA_PATH + "plugins";
    public final static long START_TIME = System.currentTimeMillis();
    public final static String FIRST_START = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date(START_TIME));
    public static boolean TITLE = true;
    public static int DEBUG = 1;

    private static final String STARTUP_FILE = DATA_PATH + "startup.conf";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static final Map<String, Map<String, String>> LANG = new HashMap<>();
    private static final String[] QUESTION_KEYS = {
        "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9", "q10"
    };

    static {
        initLanguages();
    }

    private static void initLanguages() {
        Map<String, String> eng = new HashMap<>();
        eng.put("welcome", "Welcome to NeoNukkitX!");
        eng.put("thanks", "Thank you for downloading NeoNukkitX core.");
        eng.put("author", "Author: RUSPlugins-Team & NeoNukkitX Entertainment");
        eng.put("version", "You have downloaded version 1.1.0.0");
        eng.put("select_lang", "Please select your interface language:");
        eng.put("lang_1", "1. English");
        eng.put("lang_2", "2. Russian");
        eng.put("lang_3", "3. Chinese");
        eng.put("lang_4", "4. Japanese");
        eng.put("enter_choice", "Enter your choice (1-4): ");
        eng.put("invalid_choice", "Invalid choice. Defaulting to English.");
        eng.put("answer_prompt", "Answer (Y/N): ");
        eng.put("thank_you", "Thank you for answering all our questions.");
        eng.put("compat_check", "Checking server compatibility...");
        eng.put("compat_ok", "Your core is compatible with the server.");
        eng.put("compat_active", "Active");
        eng.put("compat_fail", "Your core is NOT compatible with the server.");
        eng.put("compat_error", "ERROR");
        eng.put("compat_reason", "Reason: ");
        eng.put("compat_reason_java", "Unsupported Java version. Required: 21+, Found: ");
        eng.put("compat_reason_memory", "Insufficient memory. Required: 1024MB+, Found: ");
        eng.put("q1", "Do you agree to the End User License Agreement (EULA)?");
        eng.put("q2", "Do you consent to anonymous data collection for analytics?");
        eng.put("q3", "Do you agree to automatic error and crash reporting?");
        eng.put("q4", "Do you confirm you are at least 13 years old?");
        eng.put("q5", "Do you agree not to redistribute this software without permission?");
        eng.put("q6", "Do you accept that modifications to the server are at your own risk?");
        eng.put("q7", "Do you agree to player activity logging for moderation purposes?");
        eng.put("q8", "Do you consent to automatic update checks?");
        eng.put("q9", "Do you acknowledge this software is provided without warranties?");
        eng.put("q10", "Do you agree to all terms and conditions of NeoNukkitX?");
        LANG.put("eng", eng);

        Map<String, String> rus = new HashMap<>();
        rus.put("welcome", "Добро пожаловать в NeoNukkitX!");
        rus.put("thanks", "Благодарим за загрузку ядра NeoNukkitX.");
        rus.put("author", "Автор: RUSPlugins-Team & NeoNukkitX Entertainment");
        rus.put("version", "Вы загрузили версию 1.1.0.0");
        rus.put("select_lang", "Выберите язык интерфейса:");
        rus.put("lang_1", "1. English");
        rus.put("lang_2", "2. Русский");
        rus.put("lang_3", "3. 中文");
        rus.put("lang_4", "4. 日本語");
        rus.put("enter_choice", "Введите номер (1-4): ");
        rus.put("invalid_choice", "Неверный выбор. Установлен английский.");
        rus.put("answer_prompt", "Ответ (Y/N): ");
        rus.put("thank_you", "Thank you for answering all our questions.");
        rus.put("compat_check", "Проверка совместимости сервера...");
        rus.put("compat_ok", "Ваше ядро совместимо с сервером.");
        rus.put("compat_active", "Active");
        rus.put("compat_fail", "Ваше ядро НЕ совместимо с сервером.");
        rus.put("compat_error", "ERROR");
        rus.put("compat_reason", "Причина: ");
        rus.put("compat_reason_java", "Неподдерживаемая версия Java. Требуется: 21+, Найдено: ");
        rus.put("compat_reason_memory", "Недостаточно памяти. Требуется: 1024MB+, Найдено: ");
        rus.put("q1", "Вы согласны с Лицензионным соглашением (EULA)?");
        rus.put("q2", "Вы согласны на анонимный сбор данных для аналитики?");
        rus.put("q3", "Вы согласны на автоматическую отправку отчетов об ошибках?");
        rus.put("q4", "Вы подтверждаете, что вам не менее 13 лет?");
        rus.put("q5", "Вы согласны не распространять это ПО без разрешения?");
        rus.put("q6", "Вы принимаете, что модификации сервера — на ваш страх и риск?");
        rus.put("q7", "Вы согласны на логирование активности игроков для модерации?");
        rus.put("q8", "Вы согласны на автоматическую проверку обновлений?");
        rus.put("q9", "Вы подтверждаете, что это ПО предоставляется без гарантий?");
        rus.put("q10", "Вы согласны со всеми условиями NeoNukkitX?");
        LANG.put("rus", rus);

        Map<String, String> chn = new HashMap<>();
        chn.put("welcome", "欢迎使用 NeoNukkitX！");
        chn.put("thanks", "感谢您下载 NeoNukkitX 核心。");
        chn.put("author", "作者：RUSPlugins-Team & NeoNukkitX Entertainment");
        chn.put("version", "您已下载版本 1.1.0.0");
        chn.put("select_lang", "请选择界面语言：");
        chn.put("lang_1", "1. English");
        chn.put("lang_2", "2. Русский");
        chn.put("lang_3", "3. 中文");
        chn.put("lang_4", "4. 日本語");
        chn.put("enter_choice", "输入选项 (1-4)：");
        chn.put("invalid_choice", "无效选项。默认使用英语。");
        chn.put("answer_prompt", "回答 (Y/N)：");
        chn.put("thank_you", "Thank you for answering all our questions.");
        chn.put("compat_check", "正在检查服务器兼容性...");
        chn.put("compat_ok", "您的核心与服务器兼容。");
        chn.put("compat_active", "Active");
        chn.put("compat_fail", "您的核心与服务器不兼容。");
        chn.put("compat_error", "ERROR");
        chn.put("compat_reason", "原因：");
        chn.put("compat_reason_java", "不支持的 Java 版本。需要：21+，当前：");
        chn.put("compat_reason_memory", "内存不足。需要：1024MB+，当前：");
        chn.put("q1", "您是否同意最终用户许可协议（EULA）？");
        chn.put("q2", "您是否同意匿名数据收集用于分析？");
        chn.put("q3", "您是否同意自动错误和崩溃报告？");
        chn.put("q4", "您是否确认您已年满13岁？");
        chn.put("q5", "您是否同意未经许可不重新分发本软件？");
        chn.put("q6", "您是否接受服务器修改风险自负？");
        chn.put("q7", "您是否同意记录玩家活动以进行审核？");
        chn.put("q8", "您是否同意自动检查更新？");
        chn.put("q9", "您是否确认本软件按原样提供，无任何担保？");
        chn.put("q10", "您是否同意NeoNukkitX的所有条款和条件？");
        LANG.put("chn", chn);

        Map<String, String> jap = new HashMap<>();
        jap.put("welcome", "NeoNukkitX へようこそ！");
        jap.put("thanks", "NeoNukkitX コアをダウンロードしていただきありがとうございます。");
        jap.put("author", "作者：RUSPlugins-Team & NeoNukkitX Entertainment");
        jap.put("version", "バージョン 1.1.0.0 をダウンロードしました");
        jap.put("select_lang", "インターフェース言語を選択してください：");
        jap.put("lang_1", "1. English");
        jap.put("lang_2", "2. Русский");
        jap.put("lang_3", "3. 中文");
        jap.put("lang_4", "4. 日本語");
        jap.put("enter_choice", "選択肢を入力してください (1-4)：");
        jap.put("invalid_choice", "無効な選択です。デフォルトで英語が設定されます。");
        jap.put("answer_prompt", "回答 (Y/N)：");
        jap.put("thank_you", "Thank you for answering all our questions.");
        jap.put("compat_check", "サーバーの互換性をチェックしています...");
        jap.put("compat_ok", "コアはサーバーと互換性があります。");
        jap.put("compat_active", "Active");
        jap.put("compat_fail", "コアはサーバーと互換性がありません。");
        jap.put("compat_error", "ERROR");
        jap.put("compat_reason", "理由：");
        jap.put("compat_reason_java", "サポートされていない Java バージョン。必要：21+、現在：");
        jap.put("compat_reason_memory", "メモリ不足。必要：1024MB+、現在：");
        jap.put("q1", "エンドユーザーライセンス契約（EULA）に同意しますか？");
        jap.put("q2", "分析のための匿名データ収集に同意しますか？");
        jap.put("q3", "自動エラーおよびクラッシュ報告に同意しますか？");
        jap.put("q4", "あなたは13歳以上ですか？");
        jap.put("q5", "許可なくこのソフトウェアを再配布しないことに同意しますか？");
        jap.put("q6", "サーバーの改造は自己責任であることを受け入れますか？");
        jap.put("q7", "モデレーションのためにプレイヤーアクティビティのログ記録に同意しますか？");
        jap.put("q8", "自動更新チェックに同意しますか？");
        jap.put("q9", "このソフトウェアは保証なしで提供されることを確認しますか？");
        jap.put("q10", "NeoNukkitXのすべての条項に同意しますか？");
        LANG.put("jap", jap);
    }

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        System.setProperty("log4j.skipJansi", "false");
        System.getProperties().putIfAbsent("io.netty.allocator.type", "unpooled");
        System.setProperty("leveldb.mmap", "true");

        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        OptionSpec<Void> helpSpec = parser.accepts("help", "Shows this page").forHelp();
        OptionSpec<Void> titleSpec = parser.accepts("enable-title", "Enables title at the top of the window");
        OptionSpec<String> vSpec = parser.accepts("v", "Set verbosity of logging").withRequiredArg().ofType(String.class);
        OptionSpec<String> verbositySpec = parser.accepts("verbosity", "Set verbosity of logging").withRequiredArg().ofType(String.class);
        OptionSpec<String> languageSpec = parser.accepts("language", "Set a predefined language").withOptionalArg().ofType(String.class);
        OptionSpec<Void> acceptEulaSpec = parser.accepts("accept-eula", "Accept the license agreement automatically");

        OptionSet options = parser.parse(args);

        printLogo();
        printWelcome();

        String interfaceLang = "eng";
        boolean alreadyCompleted = hasCompletedStartup();

        if (alreadyCompleted) {
            interfaceLang = loadLanguageFromFile();
            if (interfaceLang == null) interfaceLang = "eng";
        } else {
            if (options.has(acceptEulaSpec)) {
                interfaceLang = options.valueOf(languageSpec);
                if (interfaceLang == null) interfaceLang = "eng";
                if (!LANG.containsKey(interfaceLang)) interfaceLang = "eng";
                boolean[] fakeAnswers = new boolean[10];
                for (int i = 0; i < 10; i++) fakeAnswers[i] = true;
                saveStartupConfig(interfaceLang, fakeAnswers);
            } else {
                interfaceLang = promptLanguage();
                boolean[] answers = askQuestions(interfaceLang);
                System.out.println();
                System.out.println(ANSI_CYAN + getString(interfaceLang, "thank_you") + ANSI_RESET);
                saveStartupConfig(interfaceLang, answers);
            }
        }

        System.out.println();
        System.out.println(getString(interfaceLang, "compat_check"));
        if (!runCompatibilityCheck(interfaceLang)) {
            System.out.println();
            System.out.println(ANSI_RED + getString(interfaceLang, "compat_fail") + ANSI_RESET);
            System.out.println();
            System.out.print("Press Enter to exit...");
            try {
                new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)).readLine();
            } catch (IOException ignored) {}
            return;
        }

        System.out.println(ANSI_GREEN + getString(interfaceLang, "compat_ok") + " [" + getString(interfaceLang, "compat_active") + "]" + ANSI_RESET);
        System.out.println();

        if (options.has(helpSpec)) {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException ignored) {}
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
            } catch (Exception ignored) {}
        }

        String serverLanguage = options.valueOf(languageSpec);
        if (serverLanguage == null) {
            serverLanguage = interfaceLang;
        }

        try {
            if (TITLE) {
                System.out.print((char) 0x1b + "]0;NeoNukkitX " + getVersion() + (char) 0x07);
            }
            new Server(PATH, DATA_PATH, PLUGIN_PATH, serverLanguage);
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

    private static void printLogo() {
        System.out.println();
        System.out.println("    _   __           _   __      __   __   _ __ _  __");
        System.out.println("   ╱ │ ╱ ╱__  ____  ╱ │ ╱ ╱_  __╱ ╱__╱ ╱__(_) ╱│ │╱ ╱");
        System.out.println("  ╱  │╱ ╱ _ ╲╱ __ ╲╱  │╱ ╱ ╱ ╱ ╱ ╱╱_╱ ╱╱_╱ ╱ __╱   ╱ ");
        System.out.println(" ╱ ╱│  ╱  __╱ ╱_╱ ╱ ╱│  ╱ ╱_╱ ╱ ,< ╱ ,< ╱ ╱ ╱_╱   │  ");
        System.out.println("╱_╱ │_╱╲___╱╲____╱_╱ │_╱╲__,_╱_╱│_╱_╱│_╱_╱╲__╱_╱│_│  ");
        System.out.println();
    }

    private static void printWelcome() {
        System.out.println(ANSI_CYAN + "  " + getString("eng", "welcome") + ANSI_RESET);
        System.out.println();
        System.out.println("  " + getString("eng", "thanks"));
        System.out.println("  " + getString("eng", "author"));
        System.out.println("  " + getString("eng", "version"));
        System.out.println();
    }

    private static String promptLanguage() {
        System.out.println(getString("eng", "select_lang"));
        System.out.println("  " + getString("eng", "lang_1"));
        System.out.println("  " + getString("eng", "lang_2"));
        System.out.println("  " + getString("eng", "lang_3"));
        System.out.println("  " + getString("eng", "lang_4"));
        System.out.println();
        System.out.print(getString("eng", "enter_choice"));

        String input = readLine().trim();
        switch (input) {
            case "1": return "eng";
            case "2": return "rus";
            case "3": return "chn";
            case "4": return "jap";
            default:
                System.out.println(ANSI_YELLOW + getString("eng", "invalid_choice") + ANSI_RESET);
                return "eng";
        }
    }

    private static boolean[] askQuestions(String lang) {
        boolean[] answers = new boolean[10];
        System.out.println();
        for (int i = 0; i < QUESTION_KEYS.length; i++) {
            System.out.println(getString(lang, QUESTION_KEYS[i]));
            System.out.print(getString(lang, "answer_prompt"));
            String input = readLine().trim();
            answers[i] = input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
        }
        return answers;
    }

    private static boolean runCompatibilityCheck(String lang) {
        boolean ok = true;
        String reason = null;

        int javaVersion = Runtime.version().feature();
        if (javaVersion < 21) {
            ok = false;
            reason = getString(lang, "compat_reason_java") + javaVersion;
        }

        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        if (maxMemory < 1024) {
            ok = false;
            reason = getString(lang, "compat_reason_memory") + maxMemory + "MB";
        }

        if (!ok) {
            System.out.println(ANSI_RED + getString(lang, "compat_error") + ANSI_RESET);
            System.out.println(getString(lang, "compat_reason") + reason);
        }

        return ok;
    }

    private static boolean hasCompletedStartup() {
        File file = new File(STARTUP_FILE);
        if (!file.exists()) return false;
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
            return "true".equalsIgnoreCase(props.getProperty("completed", "false"));
        } catch (IOException e) {
            return false;
        }
    }

    private static String loadLanguageFromFile() {
        File file = new File(STARTUP_FILE);
        if (!file.exists()) return null;
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
            return props.getProperty("language", null);
        } catch (IOException e) {
            return null;
        }
    }

    private static void saveStartupConfig(String language, boolean[] answers) {
        Properties props = new Properties();
        props.setProperty("completed", "true");
        props.setProperty("language", language);
        for (int i = 0; i < answers.length; i++) {
            props.setProperty("q" + (i + 1), String.valueOf(answers[i]));
        }
        try (FileOutputStream fos = new FileOutputStream(STARTUP_FILE)) {
            props.store(fos, "NeoNukkitX Startup Configuration");
        } catch (IOException e) {
            System.err.println("Failed to save startup configuration: " + e.getMessage());
        }
    }

    private static String getString(String lang, String key) {
        Map<String, String> map = LANG.getOrDefault(lang, LANG.get("eng"));
        return map.getOrDefault(key, key);
    }

    private static String readLine() {
        try {
            return new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)).readLine();
        } catch (IOException e) {
            return "";
        }
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
        if (GIT_INFO != null) {
            String describe = GIT_INFO.getProperty("git.commit.id.describe");
            if (describe != null && !describe.isEmpty()) {
                return version.append(describe).toString();
            }
            String abbrev = GIT_INFO.getProperty("git.commit.id.abbrev");
            if (abbrev != null && !abbrev.isEmpty()) {
                return version.append(abbrev).toString();
            }
        }
        String manifestVersion = NeoNukkitX.class.getPackage().getImplementationVersion();
        if (manifestVersion != null && !manifestVersion.isEmpty() && !manifestVersion.equals("null")) {
            return version.append(manifestVersion).toString();
        }
        return version.append("1.1.0.0").toString();
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
