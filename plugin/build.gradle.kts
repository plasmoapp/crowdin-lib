plugins {
    kotlin("jvm")
    `maven-publish`
    `java-gradle-plugin`
}

dependencies {
    api(project(":"))
}

gradlePlugin {
    plugins {
        create("crowdin") {
            id = "su.plo.crowdin.plugin"
            implementationClass = "su.plo.crowdin.CrowdinPlugin"
        }
    }
}

publishing {
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
