package su.plo.crowdin

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import java.util.zip.ZipInputStream

internal class Crowdin(
    private val projectId: String
) {

    @Throws(IOException::class)
    fun downloadRawTranslations(fileName: String? = null): Map<String, ByteArray> {
        val url = URL("https://crowdin.com/backend/download/project/$projectId.zip")

        return CACHE.get(url) {
            val connection = url.openConnection()
            connection.connectTimeout = 3_000

            ZipInputStream(connection.getInputStream()).use { zis ->
                return@get generateSequence { zis.nextEntry }
                    .filter { it.size < 10_000_000 }
                    .map { it.name to zis.readBytes() }
                    .toMap()
            }
        }
            .filter { entry ->
                fileName
                    ?.let { entry.key.endsWith(it) }
                    ?: JSON_PATTERN.matcher(entry.key).matches()
            }
            .map { it.key.substringBefore("/") to it.value }
            .filter { CROWDIN_CODE_TO_MC_CODE.containsKey(it.first) }
            .associate { CROWDIN_CODE_TO_MC_CODE[it.first]!! to it.second }
    }

    companion object {

        private val CACHE: Cache<URL, Map<String, ByteArray>> = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10L, TimeUnit.MINUTES)
            .build();

        private val JSON_PATTERN = Pattern.compile("^([a-z]{2}(-[A-Z]{2})?)/(.+\\.json)$")
        // https://github.com/gbl/CrowdinTranslate/blob/a9a8b6fcbffd60b511e6c7514630129cf3866db7/src/main/java/de/guntram/mcmod/crowdintranslate/CrowdinTranslate.java#L27
        private val MC_CODE_TO_CROWDIN_CODE: Map<String, String> = HashMap<String, String>().also {
            it["af_za"] = "af"
            it["ar_sa"] = "ar"
            it["ast_es"] = "ast"
            it["az_az"] = "az"
            it["ba_ru"] = "ba"
            //it["bar"] = "bar"         // Bavaria
            it["be_by"] = "be"
            it["bg_bg"] = "bg"
            it["br_fr"] = "br-FR"
            //it["brb"] = "brb"         // Brabantian
            it["bs_ba"] = "bs"
            it["ca_es"] = "ca"
            it["cs_cz"] = "cs"
            it["cy_gb"] = "cy"
            it["da_dk"] = "da"
            it["de_at"] = "de-AT"
            it["de_ch"] = "de-CH"
            it["de_de"] = "de"
            it["el_gr"] = "el"
            it["en_au"] = "de-AT"
            it["en_ca"] = "en-CA"
            it["en_gb"] = "en-GB"
            it["en_nz"] = "en-NZ"
            it["en_pt"] = "en-PT"
            it["en_za"] = "en-ZA"
            it["en_ud"] = "en-UD"
            it["en_us"] = "en-US"
            //it["enp"] = "enp"         // Anglish
            //it["enws"] = "enws"       // Shakespearean English
            it["eo_uy"] = "eo"
            it["es_ar"] = "es-AR"
            it["es_cl"] = "es-CL"
            it["es_ec"] = "es-EC"
            it["es_es"] = "es-ES"
            it["es_mx"] = "es-MX"
            it["es_uy"] = "es-UY"
            it["es_ve"] = "es-VE"
            //it["esan"] = "esan"       // Andalusian
            it["et_ee"] = "et"
            it["eu_es"] = "eu"
            it["fa_ir"] = "fa"
            it["fi_fi"] = "fi"
            it["fil_ph"] = "fil"
            it["fo_fo"] = "fo"
            it["fr_ca"] = "fr-CA"
            it["fr_fr"] = "fr"
            it["fra_de"] = "fra-DE"
            it["fy_nl"] = "fy-NL"
            it["ga_ie"] = "ga-IE"
            it["gd_gb"] = "gd"
            it["gl_es"] = "gl"
            it["haw_us"] = "haw"
            it["he_il"] = "he"
            it["hi_in"] = "hi"
            it["hr_hr"] = "hr"
            it["hu_hu"] = "hu"
            it["hy_am"] = "hy-AM"
            it["id_id"] = "id"
            it["ig_ng"] = "ig"
            it["io_en"] = "ido"
            it["is_is"] = "is"
            //it["isv"] = "isv"         // Interslavic
            it["it_it"] = "it"
            it["ja_jp"] = "ja"
            it["jbo_en"] = "jbo"
            it["ka_ge"] = "ka"
            it["kk_kz"] = "kk"
            it["kn_in"] = "kn"
            it["ko_kr"] = "ko"
            //it["ksh"] = "ksh"         // Ripuarian
            it["kw_gb"] = "kw"
            it["la_la"] = "la-LA"
            it["lb_lu"] = "lb"
            it["li_li"] = "li"
            it["lol_us"] = "lol"
            it["lt_lt"] = "lt"
            it["lv_lv"] = "lv"
            //it["lzh"] = "lzh"         // Classical Chinese
            it["mi_NZ"] = "mi"
            it["mk_mk"] = "mk"
            it["mn_mn"] = "mn"
            it["ms_my"] = "ms"
            it["mt_mt"] = "mt"
            it["nds_de"] = "nds"
            it["nl_be"] = "nl-BE"
            it["nl_nl"] = "nl"
            it["nn_no"] = "nn-NO"
            it["no_no‌"] = "no"
            it["oc_fr"] = "oc"
            //it["ovd"] = "ovd"		    // Elfdalian
            it["pl_pl"] = "pl"
            it["pt_br"] = "pt-BR"
            it["pt_pt"] = "pt-PT"
            it["qya_aa"] = "qya-AA"
            it["ro_ro"] = "ro"
            //it["rpr"] = "rpr"         // Russian (pre-revolutionary)
            it["ru_ru"] = "ru"
            it["se_no"] = "se"
            it["sk_sk"] = "sk"
            it["sl_si"] = "sl"
            it["so_so"] = "so"
            it["sq_al"] = "sq"
            it["sr_sp"] = "sr"
            it["sv_se"] = "sv-SE"
            //it["sxu"] = "sxu"         // Upper Saxon German
            //it["szl"] = "szl"         // Silesian
            it["ta_in"] = "ta"
            it["th_th"] = "th"
            it["tl_ph"] = "tl"
            it["tlh_aa"] = "tlh-AA"
            it["tr_tr"] = "tr"
            it["tt_ru"] = "tt-RU"
            it["uk_ua"] = "uk"
            it["val_es"] = "val-ES"
            it["vec_it"] = "vec"
            it["vi_vn"] = "vi"
            it["yi_de"] = "yi"
            it["yo_ng"] = "yo"
            it["zh_cn"] = "zh-CN"
            it["zh_hk"] = "zh-HK"
            it["zh_tw"] = "zh-TW"
        }
        private val CROWDIN_CODE_TO_MC_CODE: Map<String, String> =
            MC_CODE_TO_CROWDIN_CODE
                .map { it.value to it.key }
                .toMap()
    }
}
