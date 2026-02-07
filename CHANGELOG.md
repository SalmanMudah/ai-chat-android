# Changelog

All notable changes to the AI Chat SDK will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Added
- Comprehensive KDoc documentation for all public APIs
- Detailed USAGE_GUIDE.md with step-by-step integration instructions
- API key configuration support via constructor injection
- `configureApiKey()` method in MyApp for easier API key setup
- Error handling examples and best practices in documentation
- Troubleshooting section in usage guide
- Code examples for advanced usage patterns
- Security best practices documentation
- Performance optimization guidelines

### Changed
- ChatRepository now accepts API key as constructor parameter instead of hardcoded value
- Improved error messages for better user experience
- Enhanced inline documentation with usage examples

### Improved
- README.md with better structure and examples
- Documentation for all public classes and methods
- Setup instructions with multiple configuration options

---

## [1.0.0] - Initial Release

### Added
- Jetpack Compose UI for chat interface
- OpenAI GPT-3.5 integration
- Local message persistence using Room
- Real-time chat updates using Kotlin Flow
- MVVM + Clean Architecture implementation
- Sample app demonstrating SDK usage
- Basic README with setup instructions

### Features
- Message bubbles with different styles for user and AI
- Loading indicator during AI response
- Persistent chat history
- Simple integration API

---

## Future Enhancements

### Planned Features
- Gemini AI integration option
- Support for multiple AI models (GPT-4, etc.)
- Conversation context management
- Message editing and deletion
- Export conversation history
- Dark/Light theme support
- Message timestamps
- Read receipts
- Typing indicators
- Custom message types (images, files)
- Conversation branching
- Message search functionality
- Analytics and usage tracking
- Rate limiting and retry logic
- Offline support with queue
- Multi-language support

### Under Consideration
- Voice input support
- Text-to-speech for AI responses
- Custom AI personalities
- Fine-tuned model support
- WebSocket support for real-time updates
- Message reactions
- Threading/reply support
- User profiles and avatars

---

For more information about contributing to this project, please see [CONTRIBUTING.md](CONTRIBUTING.md).
