plugins {
    `java-gradle-plugin`
    id("maven-publish")
}

group = "dev.jwall64"
version = "0.3.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.google.gson)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)    
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

    if (project.hasProperty("pubUrl")) {

        var url: String = project.properties["pubUrl"] as String
        val repo = project.properties["pubRepo"]

        if(repo != null) {
            if(!url.endsWith("/")) {
                url += "/"
            }
            url += repo
        }

        repositories.maven(url) {
            name = "pub"
            credentials(PasswordCredentials::class.java)
        }
    }
}
