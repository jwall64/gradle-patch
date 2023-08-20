plugins {
    `java-gradle-plugin`
    id("maven-publish")
}

group = "dev.jwall64"
version = "0.1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.jwall64.MidnightConfig:api:2.0.0-SNAPSHOT")
    implementation("com.github.jwall64.MidnightConfig:codec-json:2.0.0-SNAPSHOT")
}

gradlePlugin {
    val multiVersion by plugins.creating {
        id = "dev.jwall64.gradle-patch"
        implementationClass = "dev.jwall64.gradle.patch.PatchPlugin"
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}