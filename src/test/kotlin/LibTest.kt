import org.junit.jupiter.api.assertThrows
import su.plo.crowdin.CrowdinLib
import java.util.concurrent.ExecutionException
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LibTest {

    @Test
    fun download() {
        val toml = CrowdinLib.downloadRawTranslations(
            "pv-crowdin-test",
            "test.toml"
        ).get()
        assertEquals(toml.size, 1)
    }

    @Test
    fun downloadInvalidProject() {
        assertThrows<ExecutionException> {
            CrowdinLib.downloadRawTranslations(
                "pv-crowdin-test1",
                "test.toml"
            ).get()
        }
    }
}
