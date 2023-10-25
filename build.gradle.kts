plugins {
    idea
    `maven-publish`

    kotlin("jvm") version libs.versions.kotlin.get()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.coroutines.jdk8)

    implementation(libs.guava)

    testImplementation(kotlin("test"))
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    }

    compileKotlin {
        kotlinOptions {
            val key = "-Xjvm-default="
            freeCompilerArgs = freeCompilerArgs.filterNot { it.startsWith(key) } + listOf(key + "all")
        }
    }

    build {
        doLast {
            jar.get().archiveFile.get().asFile.delete()
        }
    }

    test {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "lib"
        }
    }

    repositories {
        maven("https://repo.plasmoverse.com/snapshots") {
            name = "PlasmoVerseSnapshots"

            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}
