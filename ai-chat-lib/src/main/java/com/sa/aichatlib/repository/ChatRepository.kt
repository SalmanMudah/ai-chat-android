package com.sa.aichatlib.repository

import com.sa.aichatlib.dao.MessageDao
import com.sa.aichatlib.model.Message
import com.sa.aichatlib.model.OpenAIChatRequest
import com.sa.aichatlib.model.OpenAIChatResponse
import com.sa.aichatlib.model.OpenAIMessage
import com.sa.aichatlib.utils.toDomain
import com.sa.aichatlib.utils.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Repository for managing chat messages and AI communication.
 *
 * Handles:
 * - Local message persistence using Room database
 * - Communication with OpenAI API
 * - Message transformation between domain and entity models
 *
 * @property dao The MessageDao for database operations.
 * @property apiKey The OpenAI API key. If not provided, defaults to "YOUR_API_KEY" (which will fail).
 *                  Get your API key from https://platform.openai.com/account/api-keys
 *
 * @sample
 * ```kotlin
 * val repository = ChatRepository(
 *     dao = database.messageDao(),
 *     apiKey = "sk-proj-..." // Your actual OpenAI API key
 * )
 * ```
 */
class ChatRepository(
    private val dao: MessageDao,
    private val apiKey: String = "YOUR_API_KEY"
) {
    /**
     * Flow of all messages from the database, mapped to domain models.
     * Automatically updates when messages are added or removed.
     */
	val messages: Flow<List<Message>> = dao.getAllMessages().map { list ->
		list.map { it.toDomain() }
	}

	private val jsonParser = Json { ignoreUnknownKeys = true }

	private val client = OkHttpClient()

    /**
     * Inserts a message into the database.
     *
     * @param message The message to insert.
     */
	suspend fun insert(message: Message) = dao.insert(message.toEntity())
	
    /**
     * Clears all messages from the database.
     */
	suspend fun clear() = dao.clear()

    /**
     * Sends a message to OpenAI and returns the AI's response.
     *
     * This method:
     * 1. Creates a chat completion request using GPT-3.5-turbo
     * 2. Sends the request to OpenAI's API
     * 3. Parses and returns the response text
     *
     * @param message The user's message to send to the AI.
     * @return The AI's response text, or an error message if the request fails.
     *
     * @throws Exception if network request fails (caught and returns error message).
     */
	suspend fun getAIResponse(message: String): String = withContext(Dispatchers.IO) {
		try {

			val requestBody = jsonParser.encodeToString(
				OpenAIChatRequest(
					model = "gpt-3.5-turbo",
					messages = listOf(OpenAIMessage(role = "user", content = message))
				)
			).toRequestBody("application/json".toMediaType())

			val request = Request.Builder()
				.url("https://api.openai.com/v1/chat/completions")
				.header("Authorization", "Bearer $apiKey")
				.post(requestBody)
				.build()

			val response = client.newCall(request).execute()
			val bodyString = response.body?.string() ?: ""

			val parsed = jsonParser.decodeFromString<OpenAIChatResponse>(bodyString)
			return@withContext parsed.choices.firstOrNull()?.message?.content?.trim()
				?: "No response from OpenAI"
		} catch (e: Exception) {
			e.printStackTrace()
			return@withContext "Something went wrong"
		}
	}


}
