# Contributing to AI Chat SDK

Thank you for your interest in contributing to the AI Chat SDK! This document provides guidelines and instructions for contributing.

---

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [How to Contribute](#how-to-contribute)
- [Coding Standards](#coding-standards)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Testing](#testing)
- [Documentation](#documentation)

---

## Code of Conduct

This project adheres to a code of conduct that all contributors are expected to follow:

- Be respectful and inclusive
- Welcome newcomers and help them get started
- Focus on constructive criticism
- Respect differing viewpoints and experiences
- Accept responsibility and apologize for mistakes

---

## Getting Started

### Prerequisites

- Android Studio (Arctic Fox or later)
- JDK 11 or higher
- Kotlin 1.9.0 or later
- Git

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:

```bash
git clone https://github.com/YOUR_USERNAME/ai-chat-android.git
cd ai-chat-android
```

3. Add the upstream repository:

```bash
git remote add upstream https://github.com/SalmanMudah/ai-chat-android.git
```

---

## Development Setup

### 1. Open in Android Studio

Open the project in Android Studio and let it sync the Gradle files.

### 2. Configure API Key

Create a `local.properties` file in the root directory:

```properties
OPENAI_API_KEY=sk-proj-your-test-key-here
```

### 3. Build the Project

```bash
./gradlew build
```

### 4. Run the Sample App

Run the `sampleapp` module to test the SDK.

---

## How to Contribute

### Reporting Bugs

Before creating a bug report:
- Check if the issue already exists
- Collect relevant information (OS version, Android version, stack traces, etc.)
- Try to reproduce the issue with minimal code

When filing a bug report, include:
- Clear, descriptive title
- Step-by-step reproduction instructions
- Expected vs actual behavior
- Screenshots or screen recordings if applicable
- Device and OS information

### Suggesting Enhancements

Enhancement suggestions are welcome! Please:
- Use a clear, descriptive title
- Provide detailed description of the enhancement
- Explain why this enhancement would be useful
- Include code examples if applicable

### Code Contributions

1. **Choose an Issue**: Look for issues labeled `good first issue` or `help wanted`
2. **Comment on the Issue**: Let others know you're working on it
3. **Create a Branch**: Use a descriptive branch name

```bash
git checkout -b feature/your-feature-name
# or
git checkout -b fix/issue-number-description
```

4. **Make Your Changes**: Follow the coding standards below
5. **Test Your Changes**: Ensure all tests pass and add new tests if needed
6. **Commit Your Changes**: Follow commit message guidelines
7. **Push to Your Fork**: 

```bash
git push origin feature/your-feature-name
```

8. **Open a Pull Request**: Fill in the PR template completely

---

## Coding Standards

### Kotlin Style Guide

Follow the [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use 4 spaces for indentation (no tabs)
- Maximum line length: 120 characters
- Use camelCase for variable and function names
- Use PascalCase for class names
- Add KDoc comments for public APIs

### Code Organization

```kotlin
// 1. Package declaration
package com.sa.aichatlib.example

// 2. Imports (organized alphabetically)
import androidx.compose.runtime.*
import kotlinx.coroutines.*

// 3. Top-level declarations
const val MAX_MESSAGE_LENGTH = 1000

// 4. Class declaration with KDoc
/**
 * Description of the class.
 *
 * @property param1 Description of param1
 */
class ExampleClass(private val param1: String) {
    // Properties
    private val internalProp = "value"
    
    // Initialization blocks
    init {
        // Initialization code
    }
    
    // Public methods
    fun publicMethod() {
        // Implementation
    }
    
    // Private methods
    private fun privateMethod() {
        // Implementation
    }
    
    // Companion object
    companion object {
        const val CONSTANT = "value"
    }
}
```

### Documentation

All public APIs must have KDoc comments:

```kotlin
/**
 * Brief description of the function.
 *
 * Detailed description with usage examples if needed.
 *
 * @param message The message to process
 * @return The processed result
 * @throws IllegalArgumentException if message is empty
 *
 * @sample
 * ```kotlin
 * val result = processMessage("Hello")
 * println(result) // Outputs: "Processed: Hello"
 * ```
 */
fun processMessage(message: String): String {
    require(message.isNotEmpty()) { "Message cannot be empty" }
    return "Processed: $message"
}
```

### Error Handling

- Use `Result` type for operations that can fail
- Provide meaningful error messages
- Log errors appropriately
- Handle exceptions at appropriate levels

```kotlin
suspend fun fetchData(): Result<Data> = runCatching {
    // Network call
    apiService.getData()
}.onFailure { exception ->
    Log.e(TAG, "Failed to fetch data", exception)
}
```

---

## Commit Guidelines

### Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

#### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

#### Examples

```
feat(chat): add message deletion functionality

Implement the ability to delete messages from chat history.
Includes UI updates and database operations.

Closes #123
```

```
fix(repository): handle network timeout errors

Add proper error handling for network timeout scenarios.
Display user-friendly error messages.

Fixes #456
```

### Commit Best Practices

- Keep commits atomic and focused
- Write clear, descriptive commit messages
- Reference issue numbers when applicable
- Sign your commits (optional but recommended)

---

## Pull Request Process

### Before Submitting

- [ ] Code follows the project's style guidelines
- [ ] All tests pass
- [ ] New tests added for new functionality
- [ ] Documentation updated (if applicable)
- [ ] No compiler warnings
- [ ] Changelog updated (if applicable)

### PR Template

Your PR should include:

1. **Description**: What changes does this PR introduce?
2. **Motivation**: Why is this change needed?
3. **Type of Change**: Feature, bug fix, documentation, etc.
4. **Testing**: How was this tested?
5. **Screenshots**: If UI changes are involved
6. **Related Issues**: Links to related issues

### Review Process

1. Automated checks must pass (if configured)
2. At least one maintainer review is required
3. Address all review comments
4. Maintainer will merge once approved

---

## Testing

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests "com.sa.aichatlib.ExampleTest"

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Writing Tests

- Write unit tests for business logic
- Write instrumentation tests for UI components
- Aim for high code coverage
- Test edge cases and error scenarios

```kotlin
@Test
fun `sendMessage should add message to repository`() = runTest {
    // Given
    val repository = FakeChatRepository()
    val viewModel = ChatViewModel(repository)
    
    // When
    viewModel.sendUserMessage("Hello")
    
    // Then
    val messages = repository.messages.first()
    assertTrue(messages.any { it.message == "Hello" })
}
```

---

## Documentation

### Updating Documentation

When making changes, update relevant documentation:

- **README.md**: For major features or changes to setup
- **USAGE_GUIDE.md**: For usage examples and tutorials
- **CHANGELOG.md**: For all notable changes
- **KDoc comments**: For API changes

### Documentation Style

- Use clear, concise language
- Include code examples
- Add screenshots for UI features
- Keep documentation up to date

---

## Questions?

If you have questions:

1. Check the [Usage Guide](USAGE_GUIDE.md)
2. Search existing [Issues](https://github.com/SalmanMudah/ai-chat-android/issues)
3. Ask in [Discussions](https://github.com/SalmanMudah/ai-chat-android/discussions)
4. Create a new issue if needed

---

## Recognition

Contributors will be recognized in:

- GitHub contributors list
- Release notes (for significant contributions)
- Special mentions in documentation (for exceptional contributions)

---

## License

By contributing to this project, you agree that your contributions will be licensed under the MIT License.

Thank you for contributing! 🎉
