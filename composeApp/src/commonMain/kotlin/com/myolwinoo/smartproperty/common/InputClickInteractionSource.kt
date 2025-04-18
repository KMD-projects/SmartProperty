package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * A custom [MutableInteractionSource] that triggers an onClick callback when a press interaction is released.
 * This is useful for integrating click behavior with composable functions that don't have a dedicated onClick parameter.
 */
class InputClickInteractionSource(
    val onClick: () -> Unit
) : MutableInteractionSource {
    override val interactions = MutableSharedFlow<Interaction>(
        extraBufferCapacity = 16,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    /**
     * Emits an interaction to the shared flow.
     * If the interaction is a [PressInteraction.Release], the onClick callback is invoked.
     */
    override suspend fun emit(interaction: Interaction) {
        if (interaction is PressInteraction.Release) {
            onClick()
        }

        interactions.emit(interaction)
    }

    override fun tryEmit(interaction: Interaction): Boolean {
        return interactions.tryEmit(interaction)
    }
}