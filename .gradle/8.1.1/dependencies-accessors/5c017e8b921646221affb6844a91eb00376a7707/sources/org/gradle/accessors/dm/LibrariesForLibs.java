package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final FastutilLibraryAccessors laccForFastutilLibraryAccessors = new FastutilLibraryAccessors(owner);
    private final JlineLibraryAccessors laccForJlineLibraryAccessors = new JlineLibraryAccessors(owner);
    private final JoptLibraryAccessors laccForJoptLibraryAccessors = new JoptLibraryAccessors(owner);
    private final JunitLibraryAccessors laccForJunitLibraryAccessors = new JunitLibraryAccessors(owner);
    private final Log4jLibraryAccessors laccForLog4jLibraryAccessors = new Log4jLibraryAccessors(owner);
    private final TerminalLibraryAccessors laccForTerminalLibraryAccessors = new TerminalLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for blockstateupdater (org.cloudburstmc:block-state-updater)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBlockstateupdater() {
            return create("blockstateupdater");
    }

        /**
         * Creates a dependency provider for epoll (io.netty:netty-transport-native-epoll)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEpoll() {
            return create("epoll");
    }

        /**
         * Creates a dependency provider for gson (com.google.code.gson:gson)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("gson");
    }

        /**
         * Creates a dependency provider for guava (com.google.guava:guava)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGuava() {
            return create("guava");
    }

        /**
         * Creates a dependency provider for jwt (org.bitbucket.b_c:jose4j)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJwt() {
            return create("jwt");
    }

        /**
         * Creates a dependency provider for leveldb (org.iq80.leveldb:leveldb)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLeveldb() {
            return create("leveldb");
    }

        /**
         * Creates a dependency provider for leveldbjni (net.daporkchop:leveldb-mcpe-jni)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLeveldbjni() {
            return create("leveldbjni");
    }

        /**
         * Creates a dependency provider for lmbda (org.lanternpowered:lmbda)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLmbda() {
            return create("lmbda");
    }

        /**
         * Creates a dependency provider for lombok (org.projectlombok:lombok)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLombok() {
            return create("lombok");
    }

        /**
         * Creates a dependency provider for network (org.cloudburstmc.netty:netty-transport-raknet)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getNetwork() {
            return create("network");
    }

        /**
         * Creates a dependency provider for noise (net.daporkchop.lib:noise)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getNoise() {
            return create("noise");
    }

        /**
         * Creates a dependency provider for snakeyaml (org.yaml:snakeyaml)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSnakeyaml() {
            return create("snakeyaml");
    }

        /**
         * Creates a dependency provider for snappy (org.xerial.snappy:snappy-java)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSnappy() {
            return create("snappy");
    }

    /**
     * Returns the group of libraries at fastutil
     */
    public FastutilLibraryAccessors getFastutil() {
        return laccForFastutilLibraryAccessors;
    }

    /**
     * Returns the group of libraries at jline
     */
    public JlineLibraryAccessors getJline() {
        return laccForJlineLibraryAccessors;
    }

    /**
     * Returns the group of libraries at jopt
     */
    public JoptLibraryAccessors getJopt() {
        return laccForJoptLibraryAccessors;
    }

    /**
     * Returns the group of libraries at junit
     */
    public JunitLibraryAccessors getJunit() {
        return laccForJunitLibraryAccessors;
    }

    /**
     * Returns the group of libraries at log4j
     */
    public Log4jLibraryAccessors getLog4j() {
        return laccForLog4jLibraryAccessors;
    }

    /**
     * Returns the group of libraries at terminal
     */
    public TerminalLibraryAccessors getTerminal() {
        return laccForTerminalLibraryAccessors;
    }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class FastutilLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final FastutilIntLibraryAccessors laccForFastutilIntLibraryAccessors = new FastutilIntLibraryAccessors(owner);
        private final FastutilLongLibraryAccessors laccForFastutilLongLibraryAccessors = new FastutilLongLibraryAccessors(owner);

        public FastutilLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for fastutil (it.unimi.dsi:fastutil-core)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() {
                return create("fastutil");
        }

        /**
         * Returns the group of libraries at fastutil.int
         */
        public FastutilIntLibraryAccessors getInt() {
            return laccForFastutilIntLibraryAccessors;
        }

        /**
         * Returns the group of libraries at fastutil.long
         */
        public FastutilLongLibraryAccessors getLong() {
            return laccForFastutilLongLibraryAccessors;
        }

    }

    public static class FastutilIntLibraryAccessors extends SubDependencyFactory {
        private final FastutilIntShortLibraryAccessors laccForFastutilIntShortLibraryAccessors = new FastutilIntShortLibraryAccessors(owner);

        public FastutilIntLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at fastutil.int.short
         */
        public FastutilIntShortLibraryAccessors getShort() {
            return laccForFastutilIntShortLibraryAccessors;
        }

    }

    public static class FastutilIntShortLibraryAccessors extends SubDependencyFactory {

        public FastutilIntShortLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for maps (org.cloudburstmc.fastutil.maps:int-short-maps)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getMaps() {
                return create("fastutil.int.short.maps");
        }

    }

    public static class FastutilLongLibraryAccessors extends SubDependencyFactory {
        private final FastutilLongByteLibraryAccessors laccForFastutilLongByteLibraryAccessors = new FastutilLongByteLibraryAccessors(owner);

        public FastutilLongLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at fastutil.long.byte
         */
        public FastutilLongByteLibraryAccessors getByte() {
            return laccForFastutilLongByteLibraryAccessors;
        }

    }

    public static class FastutilLongByteLibraryAccessors extends SubDependencyFactory {

        public FastutilLongByteLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for maps (org.cloudburstmc.fastutil.maps:long-byte-maps)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getMaps() {
                return create("fastutil.long.byte.maps");
        }

    }

    public static class JlineLibraryAccessors extends SubDependencyFactory {
        private final JlineTerminalLibraryAccessors laccForJlineTerminalLibraryAccessors = new JlineTerminalLibraryAccessors(owner);

        public JlineLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for reader (org.jline:jline-reader)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getReader() {
                return create("jline.reader");
        }

        /**
         * Returns the group of libraries at jline.terminal
         */
        public JlineTerminalLibraryAccessors getTerminal() {
            return laccForJlineTerminalLibraryAccessors;
        }

    }

    public static class JlineTerminalLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public JlineTerminalLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for terminal (org.jline:jline-terminal)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() {
                return create("jline.terminal");
        }

            /**
             * Creates a dependency provider for jni (org.jline:jline-terminal-jni)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJni() {
                return create("jline.terminal.jni");
        }

    }

    public static class JoptLibraryAccessors extends SubDependencyFactory {

        public JoptLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for simple (net.sf.jopt-simple:jopt-simple)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getSimple() {
                return create("jopt.simple");
        }

    }

    public static class JunitLibraryAccessors extends SubDependencyFactory {
        private final JunitJupiterLibraryAccessors laccForJunitJupiterLibraryAccessors = new JunitJupiterLibraryAccessors(owner);

        public JunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at junit.jupiter
         */
        public JunitJupiterLibraryAccessors getJupiter() {
            return laccForJunitJupiterLibraryAccessors;
        }

    }

    public static class JunitJupiterLibraryAccessors extends SubDependencyFactory {

        public JunitJupiterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for api (org.junit.jupiter:junit-jupiter-api)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getApi() {
                return create("junit.jupiter.api");
        }

            /**
             * Creates a dependency provider for engine (org.junit.jupiter:junit-jupiter-engine)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getEngine() {
                return create("junit.jupiter.engine");
        }

    }

    public static class Log4jLibraryAccessors extends SubDependencyFactory {

        public Log4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for api (org.apache.logging.log4j:log4j-api)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getApi() {
                return create("log4j.api");
        }

            /**
             * Creates a dependency provider for core (org.apache.logging.log4j:log4j-core)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCore() {
                return create("log4j.core");
        }

    }

    public static class TerminalLibraryAccessors extends SubDependencyFactory {

        public TerminalLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for console (net.minecrell:terminalconsoleappender)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getConsole() {
                return create("terminal.console");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: fastutilmaps (8.5.15-SNAPSHOT)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFastutilmaps() { return getVersion("fastutilmaps"); }

            /**
             * Returns the version associated to this alias: jline (3.30.6)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJline() { return getVersion("jline"); }

            /**
             * Returns the version associated to this alias: junit (5.9.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJunit() { return getVersion("junit"); }

            /**
             * Returns the version associated to this alias: log4j (2.25.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLog4j() { return getVersion("log4j"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

            /**
             * Creates a dependency bundle provider for fastutilmaps which is an aggregate for the following dependencies:
             * <ul>
             *    <li>org.cloudburstmc.fastutil.maps:int-short-maps</li>
             *    <li>org.cloudburstmc.fastutil.maps:long-byte-maps</li>
             * </ul>
             * This bundle was declared in catalog libs.versions.toml
             */
            public Provider<ExternalModuleDependencyBundle> getFastutilmaps() {
                return createBundle("fastutilmaps");
            }

            /**
             * Creates a dependency bundle provider for junit which is an aggregate for the following dependencies:
             * <ul>
             *    <li>org.junit.jupiter:junit-jupiter-api</li>
             *    <li>org.junit.jupiter:junit-jupiter-engine</li>
             * </ul>
             * This bundle was declared in catalog libs.versions.toml
             */
            public Provider<ExternalModuleDependencyBundle> getJunit() {
                return createBundle("junit");
            }

            /**
             * Creates a dependency bundle provider for log4j which is an aggregate for the following dependencies:
             * <ul>
             *    <li>org.apache.logging.log4j:log4j-api</li>
             *    <li>org.apache.logging.log4j:log4j-core</li>
             * </ul>
             * This bundle was declared in catalog libs.versions.toml
             */
            public Provider<ExternalModuleDependencyBundle> getLog4j() {
                return createBundle("log4j");
            }

            /**
             * Creates a dependency bundle provider for terminal which is an aggregate for the following dependencies:
             * <ul>
             *    <li>org.jline:jline-terminal</li>
             *    <li>org.jline:jline-terminal-jni</li>
             *    <li>org.jline:jline-reader</li>
             *    <li>net.minecrell:terminalconsoleappender</li>
             * </ul>
             * This bundle was declared in catalog libs.versions.toml
             */
            public Provider<ExternalModuleDependencyBundle> getTerminal() {
                return createBundle("terminal");
            }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for git to the plugin id 'com.gorylenko.gradle-git-properties'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getGit() { return createPlugin("git"); }

            /**
             * Creates a plugin provider for shadow to the plugin id 'com.github.johnrengelman.shadow'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getShadow() { return createPlugin("shadow"); }

    }

}
