val kotlin_version = "1.6.10"
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}
plugins {
    kotlin("jvm") version "1.6.10"
}
apply(plugin = "com.github.johnrengelman.shadow")
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${rootProject.name}.jar")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://libraries.minecraft.net/") }
    maven { url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/") }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")

}

