# 📚 AI Chat SDK - Complete Usage Guide

This guide provides step-by-step instructions for integrating the AI Chat SDK into your Android application.

---

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
3. [Basic Setup](#basic-setup)
4. [API Key Configuration](#api-key-configuration)
5. [Using the Chat Screen](#using-the-chat-screen)
6. [Advanced Configuration](#advanced-configuration)
7. [Error Handling](#error-handling)
8. [Customization](#customization)
9. [Troubleshooting](#troubleshooting)

---

## Prerequisites

Before you begin, ensure you have:

- Android Studio (Arctic Fox or later)
- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 35
- Kotlin 1.9.0 or later
- An OpenAI API key (get one from [OpenAI Platform](https://platform.openai.com/account/api-keys))

---

## Installation

### Step 1: Add Dependencies

Add the following to your project's `gradle/libs.versions.toml`:

```toml
[versions]
okhttp = "4.12.0"
json = "20231013"
room = "2.6.1"

[libraries]
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }
json = { group = "org.json", name = "json", version.ref = "json" }
room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
room-ktx = { module = "androidx.room:room-ktx", version.ref = "room" }
room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
```

### Step 2: Add Module Dependency

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":ai-chat-lib"))
    // ... other dependencies
}
```

---

## Basic Setup

### Step 1: Add Internet Permission

Add the following permission to your `AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET"/>
    
    <application
        android:name=".YourAppClass"
        ...>
        <!-- Your activities -->
    </application>
</manifest>
```

### Step 2: Create Application Class

Create a custom Application class that extends `MyApp`:

```kotlin
package com.yourapp

import com.sa.aichatlib.MyApp

class YourAppClass : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        // Configure your OpenAI API key here
        configureApiKey(BuildConfig.OPENAI_API_KEY)
    }
}
```

### Step 3: Register Application Class

Update your `AndroidManifest.xml` to use your custom Application class:

```xml
<application
    android:name=".YourAppClass"
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:theme="@style/Theme.YourApp">
    ...
</application>
```

---

## API Key Configuration

### Option 1: Using BuildConfig (Recommended for Production)

1. Add your API key to `local.properties`:

```properties
OPENAI_API_KEY=sk-proj-your-api-key-here
```

2. Update your app's `build.gradle.kts`:

```kotlin
android {
    ...
    
    buildTypes {
        debug {
            buildConfigField("String", "OPENAI_API_KEY", "\"${project.findProperty("OPENAI_API_KEY") ?: ""}\"")
        }
        release {
            buildConfigField("String", "OPENAI_API_KEY", "\"${project.findProperty("OPENAI_API_KEY") ?: ""}\"")
        }
    }
    
    buildFeatures {
        buildConfig = true
    }
}
```

3. Use in your Application class:

```kotlin
class YourAppClass : MyApp() {
    override fun onCreate() {
        super.onCreate()
        configureApiKey(BuildConfig.OPENAI_API_KEY)
    }
}
```

### Option 2: Direct Configuration (For Testing Only)

```kotlin
class YourAppClass : MyApp() {
    override fun onCreate() {
        super.onCreate()
        configureApiKey("sk-proj-your-api-key-here")
    }
}
```

⚠️ **Security Warning**: Never hardcode API keys in production code or commit them to version control!

---

## Using the Chat Screen

### Simple Usage

In your `MainActivity.kt`:

```kotlin
package com.yourapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.sa.aichatlib.ui.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ChatScreen()
            }
        }
    }
}
```

### Usage with Custom Theme

```kotlin
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF6200EE),
                    onPrimary = Color.White
                )
            ) {
                ChatScreen()
            }
        }
    }
}
```

---

## Advanced Configuration

### Custom ViewModel Usage

If you need more control over the chat functionality:

```kotlin
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sa.aichatlib.factory.ChatViewModelFactory
import com.sa.aichatlib.ui.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val repository = (application as YourAppClass).repository
                val chatViewModel = viewModel(
                    factory = ChatViewModelFactory(repository)
                )
                
                ChatScreen(viewModel = chatViewModel)
            }
        }
    }
}
```

### Direct Repository Access

For custom UI implementation:

```kotlin
class CustomChatActivity : ComponentActivity() {
    private lateinit var repository: ChatRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        repository = (application as YourAppClass).repository
        
        lifecycleScope.launch {
            // Send a message
            val userMessage = Message(sender = "User", message = "Hello!")
            repository.insert(userMessage)
            
            // Get AI response
            val response = repository.getAIResponse("Hello!")
            val aiMessage = Message(sender = "AI", message = response)
            repository.insert(aiMessage)
            
            // Observe messages
            repository.messages.collect { messages ->
                // Update your UI
            }
        }
    }
}
```

---

## Error Handling

### Handling Network Errors

The SDK automatically handles network errors and returns user-friendly messages. You can customize error handling by extending the ChatRepository:

```kotlin
class CustomChatRepository(dao: MessageDao, apiKey: String) : ChatRepository(dao, apiKey) {
    override suspend fun getAIResponse(message: String): String {
        return try {
            super.getAIResponse(message)
        } catch (e: Exception) {
            when (e) {
                is IOException -> "Network error. Please check your connection."
                is HttpException -> "API error. Please try again later."
                else -> "An unexpected error occurred."
            }
        }
    }
}
```

### Monitoring Loading State

```kotlin
@Composable
fun YourChatScreen(viewModel: ChatViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    val messages by viewModel.messages.collectAsState()
    
    if (isLoading) {
        CircularProgressIndicator()
    }
    
    // Your UI
}
```

---

## Customization

### Custom Message Colors

Modify the `MessageItem.kt` to customize message bubble colors:

```kotlin
// In your custom MessageItem implementation
val bubbleColor = if (isBot) 
    Color(0xFFE0E0E0)  // Gray for AI
else 
    Color(0xFF6200EE)  // Purple for User
```

### Custom Loading Indicator

```kotlin
// Replace the default loading indicator in ChatScreen
if (isLoading) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            YourCustomLoadingIndicator()
        }
    }
}
```

---

## Troubleshooting

### Common Issues

#### 1. "No response from OpenAI" Error

**Cause**: Invalid API key or network issues.

**Solution**:
- Verify your API key is correct
- Check internet connectivity
- Ensure you have credits in your OpenAI account

#### 2. App Crashes on Startup

**Cause**: Application class not registered or Room database initialization issue.

**Solution**:
- Verify `android:name=".YourAppClass"` is in AndroidManifest.xml
- Ensure you're extending MyApp in your Application class
- Check that Room dependencies are properly added

#### 3. Messages Not Persisting

**Cause**: Database not properly initialized.

**Solution**:
- Ensure `super.onCreate()` is called in your Application class
- Verify Room dependencies are included
- Clear app data and restart

#### 4. UI Not Updating

**Cause**: StateFlow not being collected properly.

**Solution**:
- Ensure you're using `collectAsState()` in your Composable
- Verify you're not blocking the main thread
- Check that messages are being inserted into the repository

### Enable Debug Logging

For troubleshooting, you can add logging:

```kotlin
class YourAppClass : MyApp() {
    override fun onCreate() {
        super.onCreate()
        configureApiKey(BuildConfig.OPENAI_API_KEY)
        
        // Enable logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
```

---

## Best Practices

1. **API Key Security**
   - Use BuildConfig for API keys
   - Add `local.properties` to `.gitignore`
   - Never commit API keys to version control

2. **Error Handling**
   - Always handle network errors gracefully
   - Show user-friendly error messages
   - Implement retry logic for failed requests

3. **Performance**
   - Use LazyColumn for message lists (already implemented)
   - Avoid unnecessary recompositions
   - Consider implementing pagination for large conversation histories

4. **User Experience**
   - Show loading indicators during API calls
   - Provide visual feedback for message sending
   - Implement proper keyboard handling

---

## Next Steps

- Explore the [Sample App](sampleapp/) for working examples
- Check out the [API Documentation](ai-chat-lib/README.md)
- Review the [Contributing Guidelines](CONTRIBUTING.md) if you'd like to contribute

---

## Support

If you encounter issues or have questions:

1. Check the [Troubleshooting](#troubleshooting) section
2. Search existing [GitHub Issues](https://github.com/SalmanMudah/ai-chat-android/issues)
3. Create a new issue with detailed information

---

**License**: MIT - Free to use, modify, and extend.
