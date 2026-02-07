# 📖 AI Chat SDK - Quick Reference

A quick reference guide for the most common SDK operations.

---

## 🚀 Setup (5 Minutes)

### 1. Add Internet Permission
```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.INTERNET"/>
```

### 2. Create Application Class
```kotlin
class YourApp : MyApp() {
    override fun onCreate() {
        super.onCreate()
        configureApiKey("sk-proj-your-api-key")
    }
}
```

### 3. Register in Manifest
```xml
<application android:name=".YourApp" ...>
```

### 4. Use in Activity
```kotlin
setContent {
    MaterialTheme {
        ChatScreen()
    }
}
```

---

## 📝 Common Operations

### Display Chat Screen
```kotlin
@Composable
fun MyScreen() {
    ChatScreen()
}
```

### Send Message Programmatically
```kotlin
val repository = (application as YourApp).repository
lifecycleScope.launch {
    repository.insert(Message(sender = "User", message = "Hello!"))
}
```

### Get AI Response
```kotlin
val response = repository.getAIResponse("Your message")
repository.insert(Message(sender = "AI", message = response))
```

### Observe Messages
```kotlin
repository.messages.collect { messageList ->
    // messageList is List<Message>
    messageList.forEach { message ->
        println("${message.sender}: ${message.message}")
    }
}
```

### Clear All Messages
```kotlin
repository.clear()
```

### Check Loading State
```kotlin
@Composable
fun MyScreen(viewModel: ChatViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    
    if (isLoading) {
        CircularProgressIndicator()
    }
}
```

---

## 🎨 Customization Snippets

### Custom Theme Colors
```kotlin
MaterialTheme(
    colorScheme = lightColorScheme(
        primary = Color(0xFF6200EE),
        onPrimary = Color.White
    )
) {
    ChatScreen()
}
```

### Dark Mode Support
```kotlin
MaterialTheme(
    colorScheme = if (isSystemInDarkTheme()) 
        darkColorScheme() 
    else 
        lightColorScheme()
) {
    ChatScreen()
}
```

### Custom ViewModel
```kotlin
val repository = (application as YourApp).repository
val viewModel = viewModel(
    factory = ChatViewModelFactory(repository)
)
ChatScreen(viewModel = viewModel)
```

---

## 🔐 API Key Configuration

### Option 1: BuildConfig (Recommended)
```properties
# local.properties
OPENAI_API_KEY=sk-proj-your-key
```

```kotlin
// build.gradle.kts
buildConfigField("String", "OPENAI_API_KEY", 
    "\"${project.findProperty("OPENAI_API_KEY") ?: ""}\"")
```

```kotlin
// YourApp.kt
configureApiKey(BuildConfig.OPENAI_API_KEY)
```

### Option 2: Environment Variable
```kotlin
val apiKey = System.getenv("OPENAI_API_KEY") ?: "default-key"
configureApiKey(apiKey)
```

### Option 3: Remote Config (Production)
```kotlin
// Fetch from your backend or Firebase Remote Config
val apiKey = remoteConfig.getString("openai_api_key")
configureApiKey(apiKey)
```

---

## 🐛 Troubleshooting

### "No response from OpenAI"
```kotlin
// Check API key is set correctly
Log.d("API", "Key configured: ${!apiKey.isEmpty()}")

// Check network connectivity
val isConnected = context.isNetworkAvailable()
```

### App Crashes on Startup
```xml
<!-- Verify AndroidManifest.xml has -->
<application android:name=".YourApp" ...>
```

### Messages Not Persisting
```kotlin
// Ensure database is initialized
class YourApp : MyApp() {
    override fun onCreate() {
        super.onCreate()  // IMPORTANT: Call super first
        configureApiKey(...)
    }
}
```

### UI Not Updating
```kotlin
// Use collectAsState() in Composables
val messages by viewModel.messages.collectAsState()

// Don't use .value directly
// ❌ val messages = viewModel.messages.value
// ✅ val messages by viewModel.messages.collectAsState()
```

---

## 📊 Data Models

### Message
```kotlin
Message(
    id = "auto-generated-uuid",
    sender = "User" or "AI",
    message = "Message text"
)

// Check if message is from AI
message.isBot // true if sender == "AI"
```

### Create Custom Message
```kotlin
val userMsg = Message(
    sender = "User",
    message = "Hello, AI!"
)
```

---

## 🔄 Flows and State

### Collect Messages
```kotlin
// In Activity/Fragment
lifecycleScope.launch {
    repository.messages.collect { messages ->
        // Update UI
    }
}
```

### In Composable
```kotlin
@Composable
fun MyScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Use messages and isLoading
}
```

---

## 📦 Dependencies

```kotlin
// In your app's build.gradle.kts
dependencies {
    implementation(project(":ai-chat-lib"))
}
```

---

## 🔗 Useful Links

- [Complete Usage Guide](USAGE_GUIDE.md)
- [API Documentation](ai-chat-lib/README.md)
- [Contributing Guidelines](CONTRIBUTING.md)
- [Changelog](CHANGELOG.md)
- [OpenAI API Docs](https://platform.openai.com/docs/api-reference)

---

## 💡 Best Practices

### ✅ DO
- Store API keys securely (BuildConfig, environment variables)
- Handle errors gracefully
- Show loading indicators
- Use Flow for reactive updates
- Call super.onCreate() in Application class

### ❌ DON'T
- Hardcode API keys in code
- Commit API keys to version control
- Block the main thread with network calls
- Ignore error states
- Use .value on StateFlow in Composables (use collectAsState())

---

## 📱 Example Projects

Check the `sampleapp` module for a complete working example:

```kotlin
// sampleapp/src/main/java/com/sa/sampleapp/MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatScreen()
        }
    }
}
```

---

## 🆘 Getting Help

1. Check [Troubleshooting](#troubleshooting)
2. Read [Usage Guide](USAGE_GUIDE.md)
3. Search [GitHub Issues](https://github.com/SalmanMudah/ai-chat-android/issues)
4. Create new issue with details

---

**Happy Coding! 🎉**
