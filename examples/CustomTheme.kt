package com.example.integration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import com.sa.aichatlib.ui.ChatScreen

/**
 * CUSTOM THEME EXAMPLE
 * 
 * Shows how to customize the appearance of the chat interface using Material3 theming.
 * The SDK respects Material Theme colors, so you can easily match your app's branding.
 * 
 * For more customization options, see: USAGE_GUIDE.md#customization
 */

class CustomThemeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            // Example 1: Custom Light Theme
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF6200EE),        // User message bubbles
                    onPrimary = Color.White,             // Text on user bubbles
                    background = Color(0xFFF5F5F5),     // Screen background
                    surface = Color.White,               // Card/surface backgrounds
                    onSurface = Color(0xFF1C1B1F)       // Text on surfaces
                )
            ) {
                ChatScreen()
            }
        }
    }
}

/**
 * Example 2: Dark Theme Support
 */
class DarkThemeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            // Automatically switch between light and dark themes
            MaterialTheme(
                colorScheme = if (isSystemInDarkTheme()) {
                    darkColorScheme(
                        primary = Color(0xFFBB86FC),
                        onPrimary = Color.Black,
                        background = Color(0xFF121212),
                        surface = Color(0xFF1E1E1E)
                    )
                } else {
                    lightColorScheme(
                        primary = Color(0xFF6200EE),
                        onPrimary = Color.White,
                        background = Color.White,
                        surface = Color(0xFFF5F5F5)
                    )
                }
            ) {
                ChatScreen()
            }
        }
    }
}

/**
 * Example 3: Custom Brand Colors
 */
class BrandedThemeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            // Match your company's brand colors
            val brandColorScheme = lightColorScheme(
                primary = Color(0xFF00BCD4),      // Cyan (your brand color)
                secondary = Color(0xFFFF9800),    // Orange (accent)
                tertiary = Color(0xFF4CAF50),     // Green (success states)
                error = Color(0xFFF44336),        // Red (errors)
                background = Color(0xFFFAFAFA),
                surface = Color.White,
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color(0xFF212121),
                onSurface = Color(0xFF212121)
            )
            
            MaterialTheme(colorScheme = brandColorScheme) {
                ChatScreen()
            }
        }
    }
}

/**
 * What gets themed:
 * - User message bubbles: Uses colorScheme.primary
 * - AI message bubbles: Fixed gray (can be customized by copying MessageItem.kt)
 * - Text colors: Uses onPrimary, onSurface
 * - Loading indicator: Uses primary color
 * - Text field: Uses surface colors
 * - Send button: Uses primary color
 * 
 * To customize beyond Material Theme:
 * 1. Copy MessageItem.kt from the SDK
 * 2. Modify bubble colors, shapes, spacing
 * 3. Create your own custom ChatScreen using the SDK components
 */
