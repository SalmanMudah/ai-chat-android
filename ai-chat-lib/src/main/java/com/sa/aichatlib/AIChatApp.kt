package com.sa.aichatlib

import android.app.Application
import androidx.room.Room
import com.sa.aichatlib.dao.AppDatabase
import com.sa.aichatlib.repository.ChatRepository

/**
 * Application class for initializing the AI Chat SDK.
 *
 * This class must be registered in your AndroidManifest.xml:
 * ```xml
 * <application
 *     android:name=".YourAppClass"
 *     ...>
 * </application>
 * ```
 *
 * @sample
 * ```kotlin
 * class YourAppClass : MyApp() {
 *     override fun onCreate() {
 *         super.onCreate()
 *         // Optionally configure with your API key
 *         configureApiKey("sk-proj-...")
 *     }
 * }
 * ```
 */
class MyApp : Application() {
	lateinit var repository: ChatRepository
		private set

	override fun onCreate() {
		super.onCreate()
		val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "chat_db").build()
		repository = ChatRepository(db.messageDao())
	}
	
	/**
	 * Configures the OpenAI API key for the chat repository.
	 *
	 * Call this method in your Application's onCreate() before using the chat functionality.
	 *
	 * @param apiKey Your OpenAI API key from https://platform.openai.com/account/api-keys
	 */
	fun configureApiKey(apiKey: String) {
		val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "chat_db").build()
		repository = ChatRepository(db.messageDao(), apiKey)
	}
}

