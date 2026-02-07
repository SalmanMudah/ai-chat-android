package com.sa.aichatlib.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.aichatlib.model.Message
import com.sa.aichatlib.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing chat conversation state and interactions.
 *
 * Handles:
 * - Loading and displaying messages from the database
 * - Sending user messages and receiving AI responses
 * - Managing loading state during AI request/response cycle
 *
 * @property repository The ChatRepository for data operations and AI communication.
 */
class ChatViewModel(private val repository: ChatRepository) : ViewModel()  {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    /**
     * Flow of all messages in the conversation.
     * Automatically updated when messages are added or loaded from the database.
     */
    val messages: StateFlow<List<Message>> = _messages
    
    private val _isLoading = MutableStateFlow(false)
    /**
     * Indicates whether an AI response is currently being fetched.
     * Use this to show loading indicators in the UI.
     */
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        loadMessagesFromDb()
    }

    private fun loadMessagesFromDb() {
        viewModelScope.launch {
            repository.messages.collect { messageList ->
                _messages.value = messageList
            }
        }
    }

    /**
     * Sends a user message and requests an AI response.
     *
     * This method:
     * 1. Saves the user's message to the database
     * 2. Requests an AI response from the configured AI service
     * 3. Saves the AI's response to the database
     *
     * @param text The user's message text.
     */
    fun sendUserMessage(text: String) {
        viewModelScope.launch {
            val userMessage = Message(sender = "User", message = text)
            repository.insert(userMessage)

            _isLoading.value = true
            val aiReply = repository.getAIResponse(text)
            val aiMessage = Message(sender = "AI", message = aiReply)
            repository.insert(aiMessage)
            _isLoading.value = false
        }
    }

}
