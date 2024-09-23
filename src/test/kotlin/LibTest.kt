import org.junit.jupiter.api.assertDoesNotThrow
import su.plo.crowdin.CrowdinLib
import java.net.URI
import kotlin.test.Test

internal class LibTest {

    @Test
    fun download() {
        assertDoesNotThrow {
            CrowdinLib.downloadRawTranslations(
                URI.create("https://github.com/plasmoapp/plasmo-voice-crowdin/archive/refs/heads/addons.zip").toURL(),
                "server/discs.toml"
            ).get()
        }
    }
}
