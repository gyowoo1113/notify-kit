plugins {
    id("java")
}

group = "io.github.gyowoo1113"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    repositories { mavenCentral() }

    plugins.withType<JavaPlugin> {
        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        }

        tasks.withType<Test> { useJUnitPlatform() }

        dependencies {
            "compileOnly"("org.projectlombok:lombok:1.18.30")
            "annotationProcessor"("org.projectlombok:lombok:1.18.30")
            "testCompileOnly"("org.projectlombok:lombok:1.18.30")
            "testAnnotationProcessor"("org.projectlombok:lombok:1.18.30")

            "testImplementation"(platform("org.junit:junit-bom:5.10.0"))
            "testImplementation"("org.junit.jupiter:junit-jupiter")
        }
    }
}