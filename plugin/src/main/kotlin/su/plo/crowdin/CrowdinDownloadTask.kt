package su.plo.crowdin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CrowdinDownloadTask : DefaultTask() {

    @TaskAction
    fun action() {
        val plugin = project.plugins.getPlugin(CrowdinPlugin::class.java)
        val params = plugin.params

        val fileExtension = params.sourceFileName?.substringAfter(".") ?: ".json"

        val languagesDir = File(project.buildDir, "generated/sources/crowdin/${params.resourceDir}")
        languagesDir.mkdirs()

        val translations = CrowdinLib.downloadRawTranslations(params.projectId, params.sourceFileName).get()
        if (translations.isEmpty()) {
            if (params.createList) {
                File(languagesDir, "list").writeText(params.sourceLanguage)
            }

            return
        }
        if (params.createList) {
            File(languagesDir, "list")
                .writeText("${params.sourceLanguage}\n${translations.keys.filter { it != params.sourceLanguage }.joinToString("\n")}")
        }

        translations.forEach { (languageName, languageBytes) ->
            File(languagesDir, "$languageName.$fileExtension").writeBytes(languageBytes)
        }
    }
}
