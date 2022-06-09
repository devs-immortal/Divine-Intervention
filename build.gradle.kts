@file:Suppress("GradlePackageVersionRange")

plugins {
    `java-library`
    `maven-publish`
}

version = properties["divine_intervention_version"]!!
group = "net.immortaldevs"

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    mavenCentral()
}

dependencies {
    implementation("net.fabricmc:fabric-loader:${properties["loader_version"]!!}")
    api("net.fabricmc:sponge-mixin:${properties["mixin_version"]!!}")
}

tasks.processResources {
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}

java {
    sourceCompatibility = JavaVersion.toVersion(properties["java_version"]!!)
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
