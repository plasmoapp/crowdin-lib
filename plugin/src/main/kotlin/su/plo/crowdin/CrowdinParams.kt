package su.plo.crowdin

open class CrowdinParams {

    lateinit var projectId: String
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
