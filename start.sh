#!/bin/bash
MIN_RAM="512M"
MAX_RAM="2G"
JVM_OPTS=" -Xms${MIN_RAM} -Xmx${MAX_RAM} -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=100 -XX:+DisableExplicitGC -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:G1HeapRegionSize=4M -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCCountTarget=4 -XX:+AlwaysPreTouch -XX:+UseCompressedOops -XX:+UseCompressedClassPointers -Dfile.encoding=UTF-8 -Djava.awt.headless=true -Djdk.nio.maxCachedBufferSize=262144 -Dio.netty.leakDetectionLevel=disabled -Dio.netty.recycler.maxCapacity.default=1000 -Dio.netty.allocator.type=pooled"
SERVER_JAR="NeoNukkitX-1.1.0.0.jar"
echo "Starting NeoNukkitX..."
java ${JVM_OPTS} -jar ${SERVER_JAR} nogui
