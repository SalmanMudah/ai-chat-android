package com.sa.aichatlib.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sa.aichatlib.repository.ChatRepository
import com.sa.aichatlib.viewmodel.ChatViewModel

/**
 * Factory for creating ChatViewModel instances with dependency injection.
 *
 * This factory allows us to pass the ChatRepository dependency to the ViewModel,
 * which is required since ViewModels cannot have constructor parameters by default.
 *
 * @property repository The ChatRepository instance to inject into the ViewModel
 *
 * @sample
 * ```kotlin
 * val viewModel = viewModel(
 *     factory = ChatViewModelFactory(repository)
 * )
 * ```
 */
@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory(private val repository: ChatRepository) :
	ViewModelProvider.Factory {
	/**
	 * Creates a new instance of the given ViewModel class.
	 *
	 * @param modelClass The class of the ViewModel to create
	 * @return A newly created ViewModel instance
	 */
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return ChatViewModel(repository) as T
	}
}
