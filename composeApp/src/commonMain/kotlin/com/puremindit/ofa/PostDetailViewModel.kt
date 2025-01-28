package com.puremindit.ofa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class PostDetailViewModel(private val postRepository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.onStart {
        getPost(1)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PostDetailUiState()
    )

    suspend fun getPost(id: Int): Post? {
        val post = postRepository.getPost(id)
        _uiState.value = PostDetailUiState(selectedPost = post)
        return post
    }


}

data class PostDetailUiState(val selectedPost: Post? = null) {

}
