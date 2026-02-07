# 🤖 AI Chat SDK for Android (OpenAI & Gemini Support)

A lightweight, production-ready Android SDK module that lets you integrate conversational AI (OpenAI) into any Android app using modern development practices.

---

## ✨ Module Features

- ✨ **Jetpack Compose UI** - Ready-to-use chat interface
- 🤖 **OpenAI GPT-3.5** - AI-powered conversations
- 💾 **Local Persistence** - Room database for message history
- 🔁 **Real-time Updates** - Kotlin Flow for reactive UI
- 🧱 **MVVM Architecture** - Clean, maintainable code
- 📱 **Easy Integration** - Drop-in SDK module
- 🔐 **Secure API Configuration** - Dependency injection for API keys

---

## 📦 Module Structure

```
ai-chat-lib/
├── model/
│   ├── Message.kt              # Chat message data model
│   ├── OpenAIChatRequest.kt    # OpenAI API request model
│   └── OpenAIChatResponse.kt   # OpenAI API response model
├── dao/
│   ├── AppDatabase.kt          # Room database configuration
│   ├── MessageDao.kt           # Database access object
│   └── MessageEntity.kt        # Database entity
├── repository/
│   └── ChatRepository.kt       # Data layer (API + DB)
├── viewmodel/
│   └── ChatViewModel.kt        # UI state management
├── ui/
│   ├── ChatScreen.kt           # Main chat composable
│   └── MessageItem.kt          # Message bubble composable
├── factory/
│   └── ChatViewModelFactory.kt # ViewModel factory
└── utils/
    └── Extensions.kt           # Helper extensions
```

---

## 🚀 Quick Integration

### 1. Add Module to Your App

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":ai-chat-lib"))
}
```

### 2. Extend MyApp Class

```kotlin
class YourApp : MyApp() {
    override fun onCreate() {
        super.onCreate()
        configureApiKey(BuildConfig.OPENAI_API_KEY)
    }
}
```

### 3. Use ChatScreen in Your Activity

```kotlin
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

---

## 📚 API Reference

### ChatScreen

Main chat UI composable with message list, input field, and send button.

```kotlin
@Composable
fun ChatScreen(viewModel: ChatViewModel = defaultChatViewModel())
```

**Parameters:**
- `viewModel` - Optional custom ChatViewModel instance

**Features:**
- Scrollable message list (reverse layout)
- Text input with send button
- Loading indicator during AI response
- Persistent message history

---

### ChatViewModel

ViewModel for managing chat state and user interactions.

```kotlin
class ChatViewModel(repository: ChatRepository)
```

**Properties:**
- `messages: StateFlow<List<Message>>` - Flow of all messages
- `isLoading: StateFlow<Boolean>` - Loading state indicator

**Methods:**
- `sendUserMessage(text: String)` - Send a message and get AI response

---

### ChatRepository

Data repository for message persistence and AI communication.

```kotlin
class ChatRepository(dao: MessageDao, apiKey: String = "YOUR_API_KEY")
```

**Constructor Parameters:**
- `dao` - MessageDao for database operations
- `apiKey` - OpenAI API key (optional, default: "YOUR_API_KEY")

**Properties:**
- `messages: Flow<List<Message>>` - Flow of all messages from database

**Methods:**
- `suspend fun insert(message: Message)` - Save message to database
- `suspend fun clear()` - Clear all messages
- `suspend fun getAIResponse(message: String): String` - Get AI response

---

### Message

Data model representing a chat message.

```kotlin
data class Message(
    val id: String = UUID.randomUUID().toString(),
    val sender: String,
    val message: String
)
```

**Properties:**
- `id` - Unique message identifier (auto-generated)
- `sender` - Message sender ("AI" or "User")
- `message` - Message text content
- `isBot` - True if sender is "AI"

**Static Methods:**
- `Message.defaultMessage()` - Creates default AI welcome message

---

## 🎨 Customization

### Custom Message Colors

The message bubbles use Material Theme colors by default:
- **AI messages**: Gray background (`Color(0xFFE0E0E0)`)
- **User messages**: Primary theme color

To customize, modify `MessageItem.kt`:

```kotlin
val bubbleColor = if (isBot) 
    Color(0xFF...) // Your AI color
else 
    MaterialTheme.colorScheme.primary
```

### Custom Loading Indicator

Replace the default "Gemini is thinking..." text in `ChatScreen.kt`:

```kotlin
Text("Your custom loading text...", style = MaterialTheme.typography.bodyMedium)
```

### Custom ViewModel

Create your own ViewModel extending ChatViewModel:

```kotlin
class CustomChatViewModel(repository: ChatRepository) : ChatViewModel(repository) {
    // Add custom functionality
}
```

---

## 🔧 Advanced Usage

### Direct Repository Access

```kotlin
val repository = (application as YourApp).repository

lifecycleScope.launch {
    // Insert user message
    repository.insert(Message(sender = "User", message = "Hello"))
    
    // Get AI response
    val response = repository.getAIResponse("Hello")
    repository.insert(Message(sender = "AI", message = response))
    
    // Observe messages
    repository.messages.collect { messageList ->
        // Update your custom UI
    }
}
```

### Custom Factory

```kotlin
val customRepository = ChatRepository(dao, "your-api-key")
val viewModel = viewModel(
    factory = ChatViewModelFactory(customRepository)
)
```

---

## 📊 Database Schema

### MessageEntity

```sql
CREATE TABLE messages (
    id TEXT PRIMARY KEY NOT NULL,
    sender TEXT NOT NULL,
    message TEXT NOT NULL
);
```

---

## 🔐 Security Considerations

1. **API Keys**: Never hardcode API keys in source code
2. **ProGuard**: Use provided ProGuard rules for release builds
3. **Network Security**: Ensure HTTPS communication
4. **Data Privacy**: Consider encrypting database for sensitive data

---

## 📖 Dependencies

Required dependencies (already included in module):

- Jetpack Compose UI
- Jetpack Compose Material3
- Room Database
- OkHttp
- Kotlin Serialization
- Kotlin Coroutines
- Lifecycle ViewModel

---

## 🐛 Known Limitations

1. Currently supports OpenAI GPT-3.5 only
2. No conversation context management (each message is independent)
3. No message editing or deletion UI
4. No image or file attachment support
5. No typing indicators from AI side

See [CHANGELOG.md](../CHANGELOG.md) for planned enhancements.

---

## 📄 License

MIT License - Free to use, modify, and extend.

---

For complete usage instructions, see [USAGE_GUIDE.md](../USAGE_GUIDE.md)

For contributing guidelines, see [CONTRIBUTING.md](../CONTRIBUTING.md)

