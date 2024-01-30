plugins {
    `java-gradle-plugin`
    id("maven-publish")
}

group = "dev.jwall64"
version = "0.1.1-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
    workingDir(file("run/test"))
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