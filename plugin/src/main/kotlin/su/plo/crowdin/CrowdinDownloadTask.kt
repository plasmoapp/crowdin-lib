package su.plo.crowdin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URI

open class CrowdinDownloadTask : DefaultTask() {

    @TaskAction
    fun action() {
        val plugin = project.plugins.getPlugin(CrowdinPlugin::class.java)
        val params = plugin.params

        val fileExtension = params.sourceFileName?.substringAfter(".") ?: ".json"

        val languagesDir = File(project.buildDir, "generated/sources/crowdin/${params.resourceDir}")
        languagesDir.mkdirs()

        val url = when {
            params.projectId != null -> URI.create("https://crowdin.com/backend/download/project/${params.projectId}.zip").toURL()
            params.url != null -> params.url!!
            else -> throw IllegalStateException("projectId and url can't be null")
        }

        val translations = CrowdinLib.downloadRawTranslations(
            url,
            params.sourceFileName,
            params.keyTransformer ?: if (params.url != null) {
                Crowdin.GITHUB_LOCALE_KEY_TRANSFORMER
            } else {
                Crowdin.DEFAULT_KEY_TRANSFORMER
            }
        ).get()

        if (translations.isEmpty()) {
            if (params.createList) {
                File(languagesDir, "list").writeText(params.sourceLanguage)
            }

            return
        }
        if (params.createList) {
            File(languagesDir, "list")
                .writeText(
                    "${params.sourceLanguage}\n${
                        translations.keys.filter { it != params.sourceLanguage }.joinToString("\n")
                    }"
                )
        }

        translations.forEach { (languageName, languageBytes) ->
            File(languagesDir, "$languageName.$fileExtension").writeBytes(languageBytes)
        }
    }
}
