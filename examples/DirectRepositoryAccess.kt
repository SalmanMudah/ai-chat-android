package com.example.integration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.sa.aichatlib.model.Message
import com.sa.aichatlib.MyApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * DIRECT REPOSITORY ACCESS EXAMPLE
 * 
 * Shows how to use ChatRepository directly without the provided UI.
 * Useful when you want to:
 * - Build a custom chat UI
 * - Integrate AI into non-chat features
 * - Access messages programmatically
 * - Implement custom business logic
 * 
 * For standard use cases, prefer using ChatScreen.
 * See: BasicIntegration.kt
 */

class DirectRepositoryActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Get repository from Application
        val repository = (application as MyApp).repository
        
        // Example 1: Send a message programmatically
        lifecycleScope.launch {
            // Insert user message
            val userMessage = Message(
                sender = "User",
                message = "Hello, AI!"
            )
            repository.insert(userMessage)
            
            // Get AI response
            val aiResponse = repository.getAIResponse("Hello, AI!")
            
            // Insert AI message
            val aiMessage = Message(
                sender = "AI",
                message = aiResponse
            )
            repository.insert(aiMessage)
        }
        
        // Example 2: Observe all messages
        lifecycleScope.launch {
            repository.messages.collect { messageList ->
                // Do something with messages
                messageList.forEach { message ->
                    println("${message.sender}: ${message.message}")
                }
            }
        }
        
        // Example 3: Clear conversation
        lifecycleScope.launch {
            repository.clear()
        }
    }
}

/**
 * CUSTOM UI WITH REPOSITORY
 * 
 * Build your own chat interface using the repository directly.
 */
class CustomChatUIActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = (application as MyApp).repository
        
        setContent {
            MaterialTheme {
                CustomChatScreen(repository)
            }
        }
    }
}

@Composable
fun CustomChatScreen(repository: com.sa.aichatlib.repository.ChatRepository) {
    // Collect messages from repository
    val messages by repository.messages.collectAsState(initial = emptyList())
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    // Create coroutine scope at composable level
    val coroutineScope = rememberCoroutineScope()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Custom message list
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            messages.forEach { message ->
                Text(
                    text = "${message.sender}: ${message.message}",
                    modifier = Modifier.padding(4.dp)
                )
            }
            
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
        
        // Custom input
        Row(modifier = Modifier.padding(8.dp)) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f)
            )
            
            Button(
                onClick = {
                    val messageToSend = inputText
                    inputText = ""
                    
                    // Use coroutine scope for sending message
                    coroutineScope.launch {
                        isLoading = true
                        
                        // Insert user message
                        repository.insert(
                            Message(sender = "User", message = messageToSend)
                        )
                        
                        // Get AI response
                        val response = repository.getAIResponse(messageToSend)
                        
                        // Insert AI message
                        repository.insert(
                            Message(sender = "AI", message = response)
                        )
                        
                        isLoading = false
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

/**
 * NON-CHAT AI INTEGRATION
 * 
 * Use the AI for non-chat features like content generation,
 * text analysis, or automated responses.
 */
class AIAssistantActivity : ComponentActivity() {
    
    private lateinit var repository: com.sa.aichatlib.repository.ChatRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = (application as MyApp).repository
    }
    
    // Example: AI-powered text summarization
    suspend fun summarizeText(text: String): String {
        val prompt = "Summarize this text in 2-3 sentences: $text"
        return repository.getAIResponse(prompt)
    }
    
    // Example: AI-powered content generation
    suspend fun generateBlogPost(topic: String): String {
        val prompt = "Write a short blog post about: $topic"
        return repository.getAIResponse(prompt)
    }
    
    // Example: AI-powered code review
    suspend fun reviewCode(code: String): String {
        val prompt = "Review this code and suggest improvements: $code"
        return repository.getAIResponse(prompt)
    }
    
    // Example: AI-powered translation
    suspend fun translateText(text: String, language: String): String {
        val prompt = "Translate this to $language: $text"
        return repository.getAIResponse(prompt)
    }
}

/**
 * BATCH PROCESSING
 * 
 * Process multiple messages or perform batch operations.
 */
class BatchProcessingExample {
    
    suspend fun processBatch(
        repository: com.sa.aichatlib.repository.ChatRepository,
        questions: List<String>
    ) {
        questions.forEach { question ->
            // Send question
            repository.insert(
                Message(sender = "User", message = question)
            )
            
            // Get and save response
            val response = repository.getAIResponse(question)
            repository.insert(
                Message(sender = "AI", message = response)
            )
        }
    }
    
    suspend fun exportConversation(
        repository: com.sa.aichatlib.repository.ChatRepository
    ): String {
        // Use first() to get current messages without infinite collection
        val messages = repository.messages.first()
        return messages.joinToString("\n") { message ->
            "${message.sender}: ${message.message}"
        }
    }
}

/**
 * Key Repository Methods:
 * 
 * - repository.messages: Flow<List<Message>>
 *   Reactive stream of all messages
 * 
 * - repository.insert(message: Message)
 *   Save a message to database
 * 
 * - repository.getAIResponse(message: String): String
 *   Get AI response for a message
 * 
 * - repository.clear()
 *   Delete all messages
 * 
 * For more details, see: ai-chat-lib/README.md
 */
