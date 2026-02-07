package com.example.integration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.sa.aichatlib.MyApp
import com.sa.aichatlib.ui.ChatScreen

/**
 * BASIC INTEGRATION EXAMPLE
 * 
 * This is the simplest way to integrate the AI Chat SDK into your app.
 * Just three steps:
 * 1. Create an Application class extending MyApp
 * 2. Configure your API key
 * 3. Use ChatScreen in your Activity
 * 
 * Prerequisites:
 * - Add <uses-permission android:name="android.permission.INTERNET"/> to AndroidManifest.xml
 * - Register YourApplication in AndroidManifest.xml with android:name=".YourApplication"
 * 
 * For more details, see: USAGE_GUIDE.md
 */

// Step 1: Create Application class
class YourApplication : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        // Step 2: Configure your OpenAI API key
        // IMPORTANT: Don't hardcode keys! Use BuildConfig or environment variables
        configureApiKey(BuildConfig.OPENAI_API_KEY)
        
        // Alternative: For testing only
        // configureApiKey("sk-proj-your-test-key-here")
    }
}

// Step 3: Use ChatScreen in your Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            // Wrap in MaterialTheme for proper styling
            MaterialTheme {
                // That's it! The chat screen is ready to use
                ChatScreen()
            }
        }
    }
}

/**
 * What this gives you:
 * - Full chat interface with message bubbles
 * - AI-powered responses using OpenAI GPT-3.5
 * - Persistent message history (survives app restarts)
 * - Loading indicators during AI responses
 * - Automatic keyboard handling
 * 
 * The ChatScreen handles everything:
 * - Message input and sending
 * - Displaying conversation history
 * - Communicating with AI
 * - Saving messages to database
 */
