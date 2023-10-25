# mc-crowdin-lib
Kotlin library and Gradle plugin for downloading crowdin translations.

## Library
### Adding dependency to the project
<img alt="version" src="https://img.shields.io/badge/dynamic/xml?label=%20&query=/metadata/versioning/versions/version[not(contains(text(),'%2B'))][last()]&url=https://repo.plasmoverse.com/snapshots/su/plo/crowdin/lib/maven-metadata.xml">

```kotlin
repositories {
    maven("https://repo.plasmoverse.com/snapshots")
}

dependencies {
    implementation("su.plo.crowdin:lib:$version")
}
```

### Usage
See [LibTest.kt](https://github.com/plasmoapp/crowdin-lib/blob/main/src/test/kotlin/LibTest.kt)

## Gradle plugin
Gradle plugin downloads crowdin languages to `/generated/sources/crowdin` folder and adds this to the main source set.

By default, download action is triggered before `compileJava` (or `compileKotlin`), but you can add `dependsOn(crowdinDownload)` manually to your task.

### Usage
<img alt="version" src="https://img.shields.io/badge/dynamic/xml?label=%20&query=/metadata/versioning/versions/version[not(contains(text(),'%2B'))][last()]&url=https://repo.plasmoverse.com/snapshots/su/plo/crowdin/plugin/maven-metadata.xml">

settings.gradle.kts
```kotlin
pluginManagement {
    repositories {
        maven("https://repo.plasmoverse.com/snapshots")
    }
}
```

build.gradle.kts
```kotlin
plugins {
    id("su.plo.crowdin.plugin") version $version
}

plasmoCrowdin {
    projectId = "plasmo-voice"
    sourceFileName = "client.json"
    resourceDir = "assets/plasmovoice/lang"

    // uncomment if you want to create `list` file with all mc language codes separated by new line.
    // createList = true
}
```

## Credits
- [CrowdinTranslate](https://github.com/gbl/CrowdinTranslate) (map of the mc->crowdin language codes)
