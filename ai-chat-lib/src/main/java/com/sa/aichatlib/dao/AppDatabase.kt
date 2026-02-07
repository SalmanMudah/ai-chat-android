package com.sa.aichatlib.dao

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database for storing chat messages.
 *
 * This database persists all conversation history locally,
 * allowing messages to survive app restarts.
 *
 * @see MessageEntity for the database schema
 * @see MessageDao for database operations
 */
@Database(entities = [MessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
	/**
	 * Provides access to message database operations.
	 *
	 * @return The MessageDao instance for database access
	 */
	abstract fun messageDao(): MessageDao
}
