<p align="center">
  <img src="img/neologo.png" alt="NeoNukkitX Logo" width="220"/>
</p>

<h1 align="center">NeoNukkitX Core</h1>

<p align="center">
  <strong>Modern Minecraft: Bedrock Edition Server Software</strong>
</p>

<p align="center">
  <a href="#-base-specifications"><img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/></a>
  <a href="#-base-specifications"><img src="https://img.shields.io/badge/Gradle-8.5-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle 8.5"/></a>
  <a href="#-project-stats"><img src="https://img.shields.io/badge/Version-1.1.0.0-blue?style=for-the-badge" alt="Version 1.1.0.0"/></a>
  <a href="#-branding--identity"><img src="https://img.shields.io/badge/API-1.1.0-green?style=for-the-badge" alt="API 1.1.0"/></a>
  <a href="#-license"><img src="https://img.shields.io/badge/LICENSE-LGPL-blue?style=for-the-badge" alt="LGPL License"/></a>
  <a href="https://t.me/rusteamoff"><img src="https://img.shields.io/badge/Telegram-@rusteamoff-26A5E4?style=for-the-badge&logo=telegram&logoColor=white" alt="Telegram"/></a>
  <a href="#-project-stats"><img src="https://img.shields.io/badge/STATUS-STABLE-brightgreen?style=for-the-badge" alt="Status Stable"/></a>
</p>

<br/>

<div align="center">

<table>
<tr>
<td>

<div style="background-color:#d4edda; border:2px solid #28a745; border-radius:16px; padding:18px 26px; color:#155724; max-width:640px; text-align:left;">
  <strong>✅ Partial Compatibility</strong><br/><br/>
  Plugins written for <strong>PowerNukkitX</strong> and <strong>Nukkit</strong> itself may be compatible with <strong>NeoNukkitX</strong>.
</div>

</td>
</tr>
</table>

<br/>

<table>
<tr>
<td>

<div style="background-color:#f8d7da; border:2px solid #dc3545; border-radius:16px; padding:18px 26px; color:#721c24; max-width:640px; text-align:left;">
  <strong>❌ Incompatible</strong><br/><br/>
  Plugins from <strong>PocketMine-MP (PMMP)</strong> and its forks are <strong>completely incompatible</strong> and will <strong>not work</strong>.
</div>

</td>
</tr>
</table>

<br/>

<table>
<tr>
<td>

<div style="background-color:#fff3cd; border:2px solid #ffc107; border-radius:16px; padding:18px 26px; color:#856404; max-width:640px; text-align:left;">
  <strong>⚠️ Warning</strong><br/><br/>
  On <strong>old or weak servers</strong>, the core may log warnings during <strong>chunk rendering</strong> and <strong>chunk generation</strong>. This issue will be <strong>fixed in the next update</strong>.
</div>

</td>
</tr>
</table>

</div>

<br/>

## 🔗 Quick Links

<p align="center">
  <a href="#-overview">📖 Overview</a> &nbsp;•&nbsp;
  <a href="#-whats-new-in-v1100">🆕 What's New</a> &nbsp;•&nbsp;
  <a href="#-base-specifications">⚙️ Specifications</a> &nbsp;•&nbsp;
  <a href="#-branding--identity">🎨 Branding</a> &nbsp;•&nbsp;
  <a href="#-internal-modules">🛡 Modules</a> &nbsp;•&nbsp;
  <a href="#-entity-ai-system">🧠 AI System</a> &nbsp;•&nbsp;
  <a href="#-custom-mob--sulfur-cube">🟡 Sulfur Cube</a> &nbsp;•&nbsp;
  <a href="#-natural-spawn-system">🌱 Mob Spawner</a> &nbsp;•&nbsp;
  <a href="#-optimizations">🚀 Optimizations</a> &nbsp;•&nbsp;
  <a href="#-world-generation">🌍 World Gen</a> &nbsp;•&nbsp;
  <a href="#-join-testing">💬 Testing</a> &nbsp;•&nbsp;
  <a href="#-building-from-source">🔨 Build</a> &nbsp;•&nbsp;
  <a href="#-license">📄 License</a>
</p>

---

## ⚠️ Compatibility Notice

NeoNukkitX is a **standalone server core** with a fully separate plugin API. Compatibility with existing plugin ecosystems is as follows:

- ✅ **PowerNukkitX** and **Nukkit** plugins — *may* be compatible (the API surface is close).
- ❌ **PocketMine-MP (PMMP)** and its forks — **not compatible**, plugins will **not work**.

The core uses an entirely separate `rusplugins.neonukkitx` namespace and ships its own plugin API (`v1.1.0`).

---

## 📑 Table of Contents

- [Overview](#-overview)
- [What's New in v1.1.0.0](#-whats-new-in-v1100)
- [Base Specifications](#-base-specifications)
- [System Requirements](#-system-requirements)
- [Branding & Identity](#-branding--identity)
- [Infrastructure Changes](#-infrastructure-changes)
- [Internal Modules](#-internal-modules)
- [Built-in Systems](#-built-in-systems)
- [Bug Fixes (from original Nukkit)](#-bug-fixes-from-original-nukkit)
- [Core Commands](#-core-commands)
- [Entity AI System](#-entity-ai-system)
- [Custom Mob — Sulfur Cube](#-custom-mob--sulfur-cube)
- [Natural Spawn System](#-natural-spawn-system)
- [Optimizations](#-optimizations)
- [World Generation](#-world-generation)
- [New World Populators](#-new-world-populators)
- [Launcher & i18n](#-launcher--i18n)
- [Networking & Encryption](#-networking--encryption)
- [Module Configuration](#-module-configuration)
- [Launch Scripts](#-launch-scripts)
- [Project Stats](#-project-stats)
- [Architectural Rules](#-architectural-rules)
- [Building from Source](#-building-from-source)
- [Join Testing](#-join-testing)
- [License](#-license)
- [Ownership & Credits](#-ownership--credits)

---

## 🧭 Overview

**NeoNukkitX** is a private **Minecraft Bedrock Edition** server core written in **Java 21**. It is a complete rebrand and refactor of the original Nukkit project under the new identity `NeoNukkitX` with a fully new namespace (`rusplugins.neonukkitx`). The core is built with production-grade optimizations, custom world generation, an Entity AI state machine, and a built-in security suite.

> *"Nuclear powered server software."*

---

## 🆕 What's New in v1.1.0.0

This release ships **6 built-in protection & automation systems**, an interactive launcher supporting **4 languages** and **10 EULA agreements**, plus full per-module configurability for every subsystem.

> 🛡️ **Anti-cheat and stability are now baked into the core** — no need for extra plugins.

| # | System | Highlights |
|---|---|---|
| 1 | 🛡️ **AntiAFK** | 5-min timeout, ActionBar warning 30 sec before kick |
| 2 | 🚨 **AntiBrake** | 3 TPS levels, sliding window, active GC, AI manager, cluster detection |
| 3 | 🛑 **AntiDDoS** | 5-conn/IP/min limit, auto-ban |
| 4 | 🤖 **AntiBot** | Nickname heuristics, 30-sec bot mode |
| 5 | 🔄 **AutoRestart** | 24h cycle, ActionBar warning 5 min before |
| 6 | 🧪 **AutoTest** | 5 self-tests every Friday at 03:00 |
| 7 | 🌍 **Launcher** | ASCII art, 4 languages, 10 EULA questions |
| 8 | 🌐 **Xbox Fallback** | Auto-offline when Xbox Live is unreachable |
| 9 | ⚙️ **ModuleConfig** | Granular per-module / per-system on/off |

📝 **Full changelog & file list** → see [RELEASE_NOTES.md](RELEASE_NOTES.md)

---

## ⚙️ Base Specifications

| Parameter         | Value                                                       |
|-------------------|-------------------------------------------------------------|
| Language          | Java 21                                                     |
| Build System      | Gradle 8.5                                                  |
| Build Plugin      | ShadowJar                                                   |
| Version Control   | Git Version Integration                                     |
| JVM Flags         | `--add-opens`, G1GC, CompressedOops, UseStringDeduplication |
| Compression       | Snappy (Level 7)                                            |
| Network Stack     | Netty (pooled allocator, leak detection OFF)                |
| Garbage Collector | G1GC (Max Pause 100ms, DisableExplicitGC)                   |

---

## 🛠 System Requirements

| Parameter | Minimum | Recommended |
|---|---|---|
| **Java** | 21 | 21 (LTS) |
| **RAM** | 1024 MB | 4096+ MB |
| **Minecraft Bedrock** | 1.26.30 | 1.26.30+ |
| **OS** | Linux / Windows / macOS | Linux x86_64 |
| **Network** | 1 Gbit/s | 1 Gbit/s |

---

## 🎨 Branding & Identity

| Attribute        | Value                              |
|------------------|------------------------------------|
| Name             | Nukkit → **NeoNukkitX**            |
| Main Class       | `rusplugins.neonukkitx.NeoNukkitX` |
| Namespace        | `rusplugins.neonukkitx.*`          |
| Old Namespace    | `cn.nukkit.*` (fully removed)      |
| Maven `groupId`  | `rusplugins.neonukkitx`            |
| Core Version     | `1.1.0.0`                          |
| Plugin API       | `1.1.0`                            |
| Config File      | `neonukkitx.yml`                   |
| ASCII Logo       | New                                |
| Sub-MOTD         | New                                |
| Language Keys    | New                                |
| Tagline          | *"Nuclear powered server software"* |

---

## 🛠 Infrastructure Changes

- ✅ Upgraded to **Java 21**
- ✅ Migrated to **Gradle 8.5**
- ✅ Adopted **ShadowJar** for fat-jar packaging
- ✅ Integrated **Git Version Integration** (build version reflects commit state)
- ✅ Added JVM **`--add-opens`** flags for modern module access
- ✅ Fully replaced `cn.nukkit.*` with `rusplugins.neonukkitx.*`
- ✅ Version bump: `1.0.0.0` → `1.1.0.0` across `build.gradle.kts`, `NeoNukkitX.java`, `NEONKXInternalModule`, `NeoNukkitXCoreModule`

---

## 🛡 Internal Modules

NeoNukkitX ships its own internal modules under the core itself.

### NeoNukkitX-Core

| Field    | Value                                |
|----------|--------------------------------------|
| Type     | Internal core module                 |
| Role     | Core liveness indicator (heartbeat)  |
| Version  | `1.1.0.0`                            |
| Author   | NeoNukkitX Team                      |

### NEONKX-Internal

| Field    | Value                          |
|----------|--------------------------------|
| Type     | Internal core module           |
| Version  | `1.1.0.0`                      |
| Purpose  | Built-in server protection suite |

#### Protection Subsystems

- 🛡 **Anti-DDoS System** — DDoS attack mitigation
- 🤖 **Anti-Bot System** — bot connection filtering
- 🪑 **Anti-AFK System** — AFK exploit protection
- 🚫 **Anti-Cheat System** — cheat detection
- 💥 **Anti-Brake System** — server-brake protection
- 📦 **Anti-Dupe System** — duplication exploit prevention

> 💡 Every subsystem is independently toggleable in `neonukkitx-modules.yml`.

---

## 🎯 Built-in Systems (v1.1.0.0)

### 🛡️ AntiAFK
> 📂 `rusplugins.neonukkitx.plugin.internal.antiafk.AntiAFKSystem`

- ⏱️ Timeout: **5 minutes** (6000 ticks)
- ⚠️ Warning **30 seconds** before kick via ActionBar
- 🧹 Automatic cleanup of disconnected players
- 💬 Kick message: `You have been kicked for inactivity (AFK)`

### 🚨 AntiBrake (Anti-Lag)
> 📂 `rusplugins.neonukkitx.plugin.internal.antibrake.AntiBrakeSystem`

- 📊 **Multi-level TPS thresholds:** 🟡 Warning `18.0` / 🟠 Critical `15.0` / 🔴 Emergency `10.0`
- 📈 Sliding window of 10 samples for smooth detection
- 🗑️ **Active GC** triggered on critical/emergency state
- 👾 Entity tracker for suspicious teleports / movements
- 🧊 **Dense chunk detection** — freezes/kills entities when `>50 entities/chunk`
- 🐑 **Cluster detection** — radius 5.0 blocks, threshold `>20 entities`
- 🤖 **Mob AI toggle** — auto-disables at `<100` players, re-enables at `≥100`
- 🆘 **Emergency actions:** clear drops, freeze mobs, kick idle players, shutdown after **12 consecutive emergency ticks**

### 🛑 AntiDDoS
> 📂 `rusplugins.neonukkitx.plugin.internal.antiddos.AntiDDoSSystem`

- ⏱️ Check every **10 seconds** (200 ticks)
- 🔢 Max connections per IP: **5 within a 60-second window**
- 🚫 Auto-ban via `Server.getIPBans()`
- 💥 Instant kick of all sessions from the banned IP

### 🤖 AntiBot
> 📂 `rusplugins.neonukkitx.plugin.internal.antibot.AntiBotSystem`

- 🕐 Attack detection: **10 logins within 5 seconds** → bot mode for 30 seconds
- 🔍 Suspicious nickname heuristics (length, digits, repeats, vowels, ping)
- ⚡ Auto-kick during bot mode

### 🔄 AutoRestart
> 📂 `rusplugins.neonukkitx.plugin.internal.autorestart.AutoRestartSystem`

- ⏰ Cycle: **24 hours**
- 📢 Warning **5 minutes** in advance via ActionBar
- 🛑 Graceful kick + `Server.shutdown()`

### 🧪 AutoTest
> 📂 `rusplugins.neonukkitx.plugin.internal.autotest.AutoTestSystem`

- 📅 Runs: **every Friday at 03:00**
- ⏱️ Duration: **10 minutes + 2 minutes cooldown**
- 📝 Report: `autotest-report-YYYY-MM-DD_HH-mm-ss.log`
- ✅ Tests: Ping / Port / Config / Fake-online stress / Lag detection
- 📊 Console output: `[AutoTest] Core Activate`

---

## 🐛 Bug Fixes (from original Nukkit)

| Bug                                          | Status              |
|----------------------------------------------|---------------------|
| `/tps` command broken                        | ✅ Fixed            |
| Pre-loading of startup chunks                | ✅ Fixed            |
| Persistent player UUIDs                      | ✅ Fixed            |
| Cover blocks appearing above water           | ✅ Fixed            |
| Mountain generation                          | ✅ Fixed            |
| Creeper explosion delay                      | ✅ Added (30 ticks) |
| **Xbox Live unreachable → server crash**     | ✅ **Fixed in v1.1.0.0** (fallback to OFFLINE_MODE) |

---

## 🎮 Core Commands

| Command              | Description                          |
|----------------------|--------------------------------------|
| `/version` / `/ver`  | Show server / core information       |
| `/tps`               | Show current server TPS              |
| `/ping`              | Show server ping                     |
| `/plugins` / `/pl`   | List plugins and internal modules    |

---

## 🧠 Entity AI System

A custom-built AI state machine attached to every `EntityLiving` (except players).

### Supported States

| State     | Tick Rate | Description                            |
|-----------|-----------|----------------------------------------|
| `IDLE`    | 20 ticks  | Standing still                         |
| `WANDER`  | 5 ticks   | Roaming within an 8-block radius       |
| `CHASE`   | 1 tick    | Pursuing the nearest target            |
| `ATTACK`  | —         | Performing an attack action            |
| `FLEE`    | —         | Retreating from a threat               |

### Highlights

- 🧊 AI **sleeps** when no player is within **48 blocks** — saving CPU.
- 🎯 **Nearest-player cache** — no per-tick scans of all players.
- ⏱ **Dynamic tick rate per state** — idle mobs cost almost nothing.
- 🌍 `WANDER` is hard-capped to an **8-block radius** from spawn position.
- 🚫 Players are excluded — AI never auto-attaches to a `Player`.

---

## 🟡 Custom Mob — Sulfur Cube

| Parameter    | Value                  |
|--------------|------------------------|
| `NETWORK_ID` | `153`                  |
| Type         | `CustomEntity`         |
| Drops        | `ItemSulfur` (ID `851`)|
| Status       | Fully registered       |

---

## 🌱 Natural Spawn System (`MobSpawnerTask`)

| Setting           | Value                          |
|-------------------|--------------------------------|
| Run interval      | Every **10 seconds**           |
| Daytime spawns    | Passive mobs                   |
| Nighttime spawns  | Hostile mobs                   |
| Spawn radius      | **16–48 blocks** from a player |
| Per-player cap    | **12 mobs**                    |
| World cap         | **80 mobs**                    |

---

## 🚀 Optimizations

### CPU
- AI sleeps beyond 48 blocks
- Nearest-player cache
- Chunk Tick Radius: `2`
- Chunks Per Tick: `20`

### RAM
- Autosave every `12000` ticks
- `UseStringDeduplication` enabled
- `CompressedOops` enabled

### Disk
- Max **8 chunks** saved per tick

### Network
- Snappy Compression (Level 7)
- Netty pooled allocator
- Leak Detection: **OFF**

### JVM
- G1GC, Max Pause `100ms`
- `DisableExplicitGC`

---

## 🌍 World Generation

### Modified Biomes

- `CoveredBiome`
- `SnowyBiome`
- `ExtremeHillsBiome`
- `ExtremeHillsPlusBiome`
- `ForestBiome`
- `PlainsBiome`

### Generation Improvements

- ❄️ Snow on high-altitude mountains
- 🧗 Vertical cliffs
- ⛰ Taller, more dramatic peaks
- 🌲 Denser forests
- 🌾 Improved plains
- 🧩 New cover system: `getCoverId(x, y, z)`

---

## 🗺 New World Populators

### Water / Shore

| Populator            | Description  |
|----------------------|--------------|
| `PopulatorBeach`     | Beaches      |
| `PopulatorLake`      | Lakes        |
| `PopulatorRiver`     | Rivers       |
| `PopulatorWaterfall` | Waterfalls   |
| `PopulatorRavine`    | Ravines      |

### Land

| Populator           | Description |
|---------------------|-------------|
| `PopulatorCrater`   | Craters     |
| `PopulatorVolcano`  | Volcanoes   |

### Ocean

| Populator                | Description        |
|--------------------------|--------------------|
| `PopulatorOceanFloor`    | Ocean floor        |
| `PopulatorUnderwaterCave`| Underwater caves   |
| `PopulatorCoral`         | Coral reefs        |
| `PopulatorOceanRuin`     | Ocean ruins        |
| `PopulatorKelp`          | Kelp               |

---

## 🌍 Launcher & i18n

### Interactive Startup Dialog
> 📂 `rusplugins.neonukkitx.NeoNukkitX`

On first launch, an **ASCII logo** is displayed along with a greeting containing the author and version.

### 🌐 Language Support
| Language | Code |
|---|---|
| 🇬🇧 English | `en` |
| 🇷🇺 Русский | `ru` |
| 🇨🇳 中文 | `zh` |
| 🇯🇵 日本語 | `ja` |

Pick via the dialog or pass the `--language=en` flag.

### 📋 10 EULA Agreements
1. ✅ EULA acceptance
2. ✅ Anonymous data collection
3. ✅ Crash report submission
4. ✅ Age confirmation (13+)
5. ✅ Redistribution prohibition
6. ✅ Modification risks
7. ✅ Activity logging
8. ✅ Update checks
9. ✅ Warranty disclaimer
10. ✅ Full terms acceptance

Results are saved to `agreement.properties`. For headless environments: `--accept-eula`.

### 🔍 Compatibility Check
- ☕ **Java 21+** — required
- 💾 **1024 MB+ memory** — required
- Server will not start if requirements are not met.

---

## 🌐 Networking & Encryption

### Offline-Mode Fallback for Xbox Authentication
> 📂 `rusplugins.neonukkitx.network.encryption.EncryptionUtils`

- ✅ Server **no longer crashes** when Xbox services are unreachable
- ✅ Automatic fallback to `OFFLINE_MODE = true`
- 📝 Log: `Xbox authentication services are unreachable. Server running in offline-compatible mode.`
- 🎮 Players with `AuthType.SELF_SIGNED` can join
- ⚠️ `validateToken()` throws a proper error for FULL authentication in offline mode

---

## ⚙️ Module Configuration

File: **`neonukkitx-modules.yml`** (auto-generated in the server root).

```yaml
# Enable/disable modules
module:
  NEONKX-Internal:
    enabled: true
  NeoNukkitX-Core:
    enabled: true

# Enable/disable systems within NEONKX-Internal
system:
  AntiAFK:
    enabled: true
  AntiBrake:
    enabled: true
  AntiDDoS:
    enabled: true
  AntiBot:
    enabled: true
  AutoRestart:
    enabled: true
  AutoTest:
    enabled: true
```

> 💡 **Disabled systems** log `SystemName: disabled` and don't start. Fully disabling a module disables all its subsystems via `onDisable()`.

---

## 📜 Launch Scripts

- `start.sh` — fully optimized launch script with all required JVM flags, G1GC tuning, Netty, and Snappy settings pre-applied.

---

## 📊 Project Stats

| Metric                                          | Value                  |
|-------------------------------------------------|------------------------|
| Core Version                                    | `1.1.0.0`              |
| Plugin API                                      | `1.1.0`                |
| Status                                          | **Stable**             |
| Total major changes (vs. original Nukkit)       | **73**                 |
| Current focus                                   | World generation improvements |

---

## 🏛 Architectural Rules

These rules are non-negotiable in the NeoNukkitX codebase:

- ❌ **`cn.nukkit.*` is forbidden** — fully removed.
- ❌ **No plugin development in this repository** — core only.
- ❌ **No compatibility with PocketMine / PMMP / EndStone / LeviLamina / Bukkit / Spigot / Paper.**
- ⚠️ **No active compatibility work for Nukkit / NukkitX / PowerNukkit** — they *may* work if the API surface matches, but no guarantees and no shims are shipped.
- ✅ **All decisions must follow the NeoNukkitX architecture.**
- ✅ **Minimal, safe changes** — surgical refactors only.
- ✅ **Production-level code quality.**
- ✅ **CPU and RAM optimization** at every level.

---

## 🔨 Building from Source

```bash
# Clone the repository
git clone https://github.com/NeoNukkitX-Intertainment/NeoNukkitX.git
# or
git clone https://github.com/RUSPlugins-Team/NeoNukkitX.git

cd NeoNukkitX

# Build the fat jar (via Gradle + ShadowJar)
./gradlew shadowJar

# Run the server
java -jar build/libs/NeoNukkitX-1.1.0.0.jar
```

The default Gradle target produces a fully shaded `*.jar` in `build/libs/`.

### Headless / Docker
```bash
# Skip the interactive EULA dialog
java -jar NeoNukkitX-1.1.0.0.jar --accept-eula --language en
```

---

## 💬 Join Testing

> 🧪 **We're inviting everyone to help test the core with us!**
> Join our Telegram channel — that's where we publish builds, discuss bugs, run stress tests, and validate new features before stable release.

### ✨ Why Join Testing?

| What you get | What you give the project |
|---|---|
| 🚀 **Early access** to new builds and features | 🐛 **Bug reproductions** and feedback |
| 💬 **Direct line** to core developers | 📊 **Real-world load** scenarios |
| 🏆 **Credits mention** for active contributors | 🌍 **Diverse configs** (OS, hardware, player count) |
| 🎯 **Influence** on development priorities | ✅ **Stability confirmation** before release |

### 📲 How to Join?

1. 👉 Head over to our Telegram channel: **[t.me/rusteamoff](https://t.me/rusteamoff)**
2. 📝 Read the pinned message with testing rules
3. 🧪 Download the test build (link in the channel)
4. 🐛 Found a bug? → report it in the channel or open an [Issue](https://github.com/RUSPlugins-Team/NeoNukkitX/issues) with the `test-build` tag
5. 💡 Got a feature idea? → discuss it in the channel — the best ones make it into the roadmap

### 🎯 What Are We Testing Right Now?

- 🛡️ AntiBrake under `>500` players load
- 🤖 AntiBot against real bot attacks
- 🧪 AutoTest in production-like environments
- 🌐 Xbox-fallback across different regions

> 💜 **The more testers, the more stable the release.** Even a simple "TPS holds 19.8 on my 200-player server" is a valuable contribution!

---

## 📞 Support

- 💬 **Telegram channel:** [t.me/rusteamoff](https://t.me/rusteamoff) — chat, news, test builds
- 🐛 **Bugs:** [Issues](https://github.com/RUSPlugins-Team/NeoNukkitX/issues)
- 💡 **Questions & discussions:** [Discussions](https://github.com/RUSPlugins-Team/NeoNukkitX/discussions)
- 📖 **Wiki:** [github.com/RUSPlugins-Team/NeoNukkitX/wiki](https://github.com/RUSPlugins-Team/NeoNukkitX/wiki)
- 💼 **Commercial support:** RUSPlugins-Team LLC

---

## 📄 License

This project is licensed under the **GNU Lesser General Public License v3.0 (LGPL-3.0)** — see the [LICENSE](LICENSE) file for details.

```
LGPL-3.0 — Copyright (c) 2026 NeoNukkitX Project & RUSPlugins-Team LLC

This is free software. You can redistribute it and/or modify it under
the terms of the LGPL-3.0 as published by the Free Software Foundation.
Full text: https://www.gnu.org/licenses/lgpl-3.0
```

> 📌 LGPL-3.0 allows using the core in proprietary projects, provided the library itself can be replaced/modified (dynamic linking or open-sourcing the modified LGPL portion).

---

## 🏢 Ownership & Credits

### NeoNukkitX Core

NeoNukkitX is owned, developed, and maintained by:

- **🏢 NeoNukkitX-Intertainment** — [github.com/NeoNukkitX-Intertainment](https://github.com/NeoNukkitX-Intertainment)
- **🏛️ RUSPlugins-Team LLC** — [github.com/RUSPlugins-Team](https://github.com/RUSPlugins-Team)

### Original Nukkit — Acknowledgments

NeoNukkitX was originally derived from the Nukkit project. The original creators and maintainers of Nukkit are credited below:

| Role                        | Credit                                          |
|-----------------------------|-------------------------------------------------|
| Original Creator of Nukkit  | **MagicDroidX**                                 |
| Long-term Project Host      | **CloudburstMC** (`cloudburstmc/Nukkit`)        |
| NukkitX Lineage             | **CreeperFace** & contributors                  |
| PowerNukkit Lineage         | PowerNukkit / PowerNukkitX maintainers          |

We acknowledge and thank the original Nukkit authors and contributors whose work made this project possible.

### A Note on Forks

> **NeoNukkitX is a heavily modified and rearchitected server core — it is not a typical fork.**

While NeoNukkitX originated from Nukkit, virtually every layer has been rewritten:
- The entire namespace has been replaced (`cn.nukkit.*` → `rusplugins.neonukkitx.*`).
- The build system, Entity AI, world generation, and protection subsystems are original work.
- It is published and maintained as a **standalone product** under **NeoNukkitX-Intertainment** and **RUSPlugins-Team LLC**, with its own plugin API (`v1.1.0`), branding, and roadmap.

As a result, NeoNukkitX should be regarded as a **modified core** — not as a downstream fork of any existing project.

---

<p align="center"><sub>Built with ☢️ by the NeoNukkitX Team — Nuclear powered server software.</sub></p>
