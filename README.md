# 🤖 AI Chat SDK for Android (OpenAI)

A lightweight, production-ready Android SDK that lets you integrate conversational AI (OpenAI GPT-3.5) into any Android app using modern development practices.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org/)

---

## ✨ Features

- 🎨 **Jetpack Compose UI** - Modern, declarative chat interface
- 🤖 **OpenAI GPT-3.5** - Powered by state-of-the-art AI
- 💾 **Local Persistence** - Message history stored with Room database
- 🔁 **Real-time Updates** - Reactive UI with Kotlin Flow
- 🧱 **Clean Architecture** - MVVM pattern for maintainability
- 📱 **Easy Integration** - Simple API, works out of the box
- 🔐 **Secure** - API key configuration via dependency injection
- 📚 **Well Documented** - Comprehensive guides and KDoc comments

---

## 🚀 Quick Start

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
        // Configure with your OpenAI API key
        configureApiKey(BuildConfig.OPENAI_API_KEY)
    }
}
```

### 3. Register in Manifest

```xml
<application
    android:name=".YourApp"
    ...>
</application>
```

### 4. Add Chat Screen

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

That's it! 🎉 You now have a fully functional AI chat interface.

---

## 📖 Documentation

- **[Complete Usage Guide](USAGE_GUIDE.md)** - Step-by-step integration instructions
- **[Changelog](CHANGELOG.md)** - Version history and updates
- **[Sample App](sampleapp/)** - Working example application
- **[API Reference](ai-chat-lib/)** - Detailed SDK documentation

---

## 🔧 API Key Configuration

### Recommended: Using BuildConfig

1. Add to `local.properties`:
```properties
OPENAI_API_KEY=sk-proj-your-key-here
```

2. Configure in `build.gradle.kts`:
```kotlin
android {
    buildFeatures {
        buildConfig = true
    }
    
    buildTypes {
        debug {
            buildConfigField("String", "OPENAI_API_KEY", 
                "\"${project.findProperty("OPENAI_API_KEY") ?: ""}\"")
        }
    }
}
```

3. Use in your Application class:
```kotlin
configureApiKey(BuildConfig.OPENAI_API_KEY)
```

🔐 **Security Note**: Never hardcode API keys or commit them to version control!

Get your API key from: [OpenAI Platform](https://platform.openai.com/account/api-keys)

---

## 🏗️ Architecture

The SDK follows Clean Architecture principles with clear separation of concerns:

```
ai-chat-lib/
├── model/           # Data models (Message, API requests/responses)
├── dao/             # Room database DAO and entities
├── repository/      # Data layer (API + local persistence)
├── viewmodel/       # Business logic and state management
├── ui/              # Compose UI components (ChatScreen, MessageItem)
├── factory/         # ViewModel factory for dependency injection
└── utils/           # Helper functions and extensions
```

### Tech Stack

| Layer          | Technology                    |
|----------------|-------------------------------|
| UI             | Jetpack Compose              |
| State          | Kotlin Coroutines + Flow     |
| Database       | Room                         |
| Network        | OkHttp + Kotlin Serialization |
| Architecture   | MVVM + Clean Architecture    |
| DI             | Manual (lightweight)         |

---

## 💡 Usage Examples

### Basic Chat Implementation

```kotlin
setContent {
    MaterialTheme {
        ChatScreen()
    }
}
```

### Custom Theme

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

### Direct Repository Access

```kotlin
val repository = (application as YourApp).repository

lifecycleScope.launch {
    // Send message
    repository.insert(Message(sender = "User", message = "Hello!"))
    
    // Get AI response
    val response = repository.getAIResponse("Hello!")
    repository.insert(Message(sender = "AI", message = response))
    
    // Observe messages
    repository.messages.collect { messages ->
        // Update UI
    }
}
```

### Custom ViewModel

```kotlin
val repository = (application as YourApp).repository
val viewModel = viewModel(factory = ChatViewModelFactory(repository))

ChatScreen(viewModel = viewModel)
```

---

## 🎨 Customization

The SDK is designed to be easily customizable:

- **Colors**: Modify message bubble colors in `MessageItem.kt`
- **Styling**: Change text styles, spacing, and shapes
- **Loading UI**: Replace the default loading indicator
- **Layouts**: Create custom message layouts
- **Theme**: Apply Material3 theming

See [USAGE_GUIDE.md](USAGE_GUIDE.md#customization) for detailed customization options.

---

## 📱 Sample App

The repository includes a complete sample app demonstrating SDK usage:

```bash
git clone https://github.com/SalmanMudah/ai-chat-android.git
cd ai-chat-android
# Open in Android Studio and run the 'sampleapp' module
```

---

## 🔍 Troubleshooting

### Common Issues

**"No response from OpenAI"**
- Verify API key is correct
- Check internet connection
- Ensure OpenAI account has credits

**App crashes on startup**
- Confirm Application class is registered in AndroidManifest.xml
- Verify you're extending MyApp
- Check Room dependencies

See [USAGE_GUIDE.md](USAGE_GUIDE.md#troubleshooting) for complete troubleshooting guide.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License - Free to use, modify, and extend.
```

---

## 🙏 Acknowledgments

- Built with modern Android development best practices
- Follows Material Design 3 guidelines
- Inspired by the need for easy AI integration in Android apps

---

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/SalmanMudah/ai-chat-android/issues)
- **Discussions**: [GitHub Discussions](https://github.com/SalmanMudah/ai-chat-android/discussions)
- **Documentation**: [Usage Guide](USAGE_GUIDE.md)

---

**Made with ❤️ for the Android community**

