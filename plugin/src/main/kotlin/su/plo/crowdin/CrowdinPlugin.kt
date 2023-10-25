package su.plo.crowdin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

class CrowdinPlugin : Plugin<Project> {

    lateinit var params: CrowdinParams

    override fun apply(target: Project) {
        params = target.extensions.create("crowdin", CrowdinParams::class.java)
        val crowdinDownload = target.tasks.create("crowdinDownload", CrowdinDownloadTask::class.java)

        val sourceSets = target.extensions.getByType(SourceSetContainer::class.java)
        val mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
        mainSourceSet.resources.srcDir("${target.buildDir}/generated/sources/crowdin")

        target.pluginManager.withPlugin("java") {
            target.tasks.getByName("compileJava").dependsOn(crowdinDownload)
        }

        target.pluginManager.withPlugin("kotlin") {
            target.tasks.getByName("compileKotlin").dependsOn(crowdinDownload)
        }
    }
}
