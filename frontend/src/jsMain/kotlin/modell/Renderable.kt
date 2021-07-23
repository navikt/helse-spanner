package modell

import androidx.compose.runtime.Composable

interface Renderable {
    @Composable
    fun render(children: @Composable () -> Unit = {})
}

