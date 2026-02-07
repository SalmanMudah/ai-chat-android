package com.example.integration

import android.app.Application
import com.sa.aichatlib.MyApp

/**
 * API KEY CONFIGURATION EXAMPLES
 * 
 * Shows different secure ways to configure your OpenAI API key.
 * NEVER hardcode API keys in production code!
 * 
 * Security best practices:
 * 1. Use BuildConfig for local development
 * 2. Use environment variables for CI/CD
 * 3. Fetch from secure backend for production
 * 4. Always add local.properties to .gitignore
 * 
 * For complete security guide, see: USAGE_GUIDE.md#api-key-configuration
 */

// ==========================================
// Method 1: BuildConfig (Recommended)
// ==========================================

/**
 * Step 1: Add to local.properties:
 * OPENAI_API_KEY=sk-proj-your-key-here
 * 
 * Step 2: Add to app/build.gradle.kts:
 * android {
 *     buildFeatures {
 *         buildConfig = true
 *     }
 *     buildTypes {
 *         debug {
 *             buildConfigField("String", "OPENAI_API_KEY", 
 *                 "\"${project.findProperty("OPENAI_API_KEY") ?: ""}\"")
 *         }
 *     }
 * }
 */
class BuildConfigExample : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        // Use the API key from BuildConfig
        configureApiKey(BuildConfig.OPENAI_API_KEY)
    }
}

// ==========================================
// Method 2: Environment Variables
// ==========================================

/**
 * Useful for CI/CD pipelines and server deployments.
 * Set environment variable before running app:
 * export OPENAI_API_KEY="sk-proj-your-key-here"
 */
class EnvironmentVariableExample : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        val apiKey = System.getenv("OPENAI_API_KEY") 
            ?: "default-fallback-key"
        
        configureApiKey(apiKey)
    }
}

// ==========================================
// Method 3: Remote Configuration (Production)
// ==========================================

/**
 * Fetch API key from your secure backend.
 * Most secure option for production apps.
 * 
 * Benefits:
 * - API key never stored in app binary
 * - Can rotate keys without app update
 * - Can implement user-specific keys
 */
class RemoteConfigExample : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        // Initially configure with placeholder
        configureApiKey("LOADING")
        
        // Fetch real key asynchronously from your backend
        fetchApiKeyFromBackend { apiKey ->
            // Reconfigure once key is fetched
            configureApiKey(apiKey)
        }
    }
    
    private fun fetchApiKeyFromBackend(callback: (String) -> Unit) {
        // Example: Fetch from your secure API
        /*
        apiClient.getOpenAIKey { result ->
            result.onSuccess { key ->
                callback(key)
            }.onFailure {
                // Handle error - maybe use cached key or show error
                callback("ERROR")
            }
        }
        */
    }
}

// ==========================================
// Method 4: Firebase Remote Config
// ==========================================

/**
 * Use Firebase Remote Config to manage API keys.
 * Good for gradual rollouts and A/B testing.
 */
class FirebaseRemoteConfigExample : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        // Configure with default first
        configureApiKey("default-key")
        
        /*
        // Initialize Firebase Remote Config
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setDefaultsAsync(mapOf(
            "openai_api_key" to "default-key"
        ))
        
        // Fetch and activate
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val apiKey = remoteConfig.getString("openai_api_key")
                configureApiKey(apiKey)
            }
        }
        */
    }
}

// ==========================================
// Method 5: Encrypted SharedPreferences
// ==========================================

/**
 * Store API key encrypted on device.
 * Useful for user-provided keys.
 */
class EncryptedStorageExample : MyApp() {
    override fun onCreate() {
        super.onCreate()
        
        /*
        // Use EncryptedSharedPreferences
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
            
        val encryptedPrefs = EncryptedSharedPreferences.create(
            this,
            "secret_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        
        val apiKey = encryptedPrefs.getString("api_key", null)
        if (apiKey != null) {
            configureApiKey(apiKey)
        }
        */
    }
}

// ==========================================
// Security Checklist
// ==========================================

/**
 * ✅ DO:
 * - Use BuildConfig for development
 * - Store API keys in local.properties (gitignored)
 * - Fetch from secure backend in production
 * - Rotate keys periodically
 * - Use environment-specific keys (dev, staging, prod)
 * 
 * ❌ DON'T:
 * - Hardcode API keys in source code
 * - Commit API keys to version control
 * - Store keys in plain text SharedPreferences
 * - Share keys between environments
 * - Include keys in app bundles/APKs
 */
