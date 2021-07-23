package modell

import androidx.compose.runtime.Composable
import kotlinx.serialization.json.JsonObject

interface DetaljView {

    @Composable
    fun renderDetaljer()

    fun json(): String = ""
}
