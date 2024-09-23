package su.plo.crowdin

import java.net.URL

open class CrowdinParams {

    var projectId: String? = null
    var url: URL? = null
    var keyTransformer: Crowdin.KeyTransformer? = null

    var resourceDir = "languages"
    var sourceFileName: String? = null

    /**
     * Whether the file `list` with language codes separated by `\n` should be created.
     */
    var createList = false

    /**
     * Source language.
     *
     * Will be first in the list if [createList] is true.
     */
    var sourceLanguage: String = "en_us"
}
