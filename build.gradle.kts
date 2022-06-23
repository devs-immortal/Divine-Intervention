val divineInterventionVersion: String by properties
val javaVersion: String by properties
val fabricLoaderVersion: String by properties
val mixinVersion: String by properties

plugins {
    `java-library`
    `maven-publish`
}

group = "net.immortaldevs"
version = divineInterventionVersion

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }

    mavenCentral()
}

dependencies {
    implementation(
            group = "net.fabricmc",
            name = "fabric-loader",
            version = fabricLoaderVersion)

    api(
            group = "net.fabricmc",
            name = "sponge-mixin",
            version = mixinVersion)
}

tasks {
    processResources {
        inputs.property("version", divineInterventionVersion)

        filesMatching("fabric.mod.json") {
            expand("version" to divineInterventionVersion)
        }
    }
}

java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
