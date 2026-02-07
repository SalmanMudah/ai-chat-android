package com.sa.aichatlib.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a chat message in the conversation.
 *
 * @property id Unique identifier for the message. Auto-generated if not provided.
 * @property sender The sender of the message. Use "AI" for bot messages, "User" for user messages.
 * @property message The text content of the message.
 */
@Immutable
@Serializable
data class Message(
    val id: String = UUID.randomUUID().toString(),
    val sender: String,
    val message: String,
) {
    /**
     * Returns true if this message was sent by the AI bot.
     */
    val isBot: Boolean = sender == "AI"
    
    companion object {
        /**
         * Creates a default welcome message from the AI assistant.
         *
         * @return A default AI message saying "Hi, I'm your assistant."
         */
        fun defaultMessage(): Message = Message(sender = "AI", message = "Hi, I'm your assistant.")
    }
}