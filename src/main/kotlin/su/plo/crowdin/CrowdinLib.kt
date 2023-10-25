package su.plo.crowdin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

object CrowdinLib {

    /**
     * Downloads and returns map of raw files.
     *
     * Map contains [String] as a key and [ByteArray] as value.
     *
     * Map [String] key is a [minecraft language code](https://minecraft.fandom.com/wiki/Language).
     *
     * @param crowdinProjectId The crowdin project identifier.
     * @param fileName The crowdin filename. Use this if you want to use specific translation.
     * By default, **FIRST** json from the file list will be used.
     */
    fun downloadRawTranslations(
        crowdinProjectId: String,
        fileName: String? = null
    ): CompletableFuture<Map<String, ByteArray>> =
        CoroutineScope(Dispatchers.Default).future {
            Crowdin(crowdinProjectId).downloadRawTranslations(fileName)
        }
}
