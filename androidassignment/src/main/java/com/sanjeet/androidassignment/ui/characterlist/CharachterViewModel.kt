package com.sanjeet.androidassignment.ui.characterlistimport androidx.lifecycle.LiveDataimport androidx.lifecycle.MutableLiveDataimport androidx.lifecycle.ViewModelimport androidx.lifecycle.viewModelScopeimport com.sanjeet.androidassignment.api.UseCaseResultimport com.sanjeet.androidassignment.data.characterModel.CharacterListimport com.sanjeet.androidassignment.data.repository.CharacterRepositoryimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.launchimport kotlinx.coroutines.withContextimport timber.log.Timberclass CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {    private val _characterLiveData = MutableLiveData<List<CharacterList>>()    val characterLiveData: LiveData<List<CharacterList>> = _characterLiveData    fun fetchCharacterList() {        viewModelScope.launch(Dispatchers.IO) {            when (val response = repository.getAllCharacters()) {                is UseCaseResult.Success -> {                    _characterLiveData.postValue(response.data.results!!)                    fetchEpisodeNames(response.data.results)                    getBookMarkedCharacterById(response.data.results)                }                is UseCaseResult.Exception -> {                    Timber.e(">>>>>>>> getCharacterList api exception ${response.exception}")                }            }        }    }    private suspend fun fetchEpisodeNames(characters: MutableList<CharacterList>) {        viewModelScope.launch(Dispatchers.IO) {            characters.forEach { characterWithEpisode ->                val episode = repository.fetchEpisodeForCharacter(characterWithEpisode.episode[0])                when (episode) {                    is UseCaseResult.Success -> {                        characterWithEpisode.episodeName = episode.data.name                    }                    is UseCaseResult.Exception -> {                        Timber.e(">>>>>>>> fetchEpisodeNames api exception ${episode.exception}")                    }                }                withContext(Dispatchers.Main) {                    _characterLiveData.postValue(characters)                }            }        }    }    private suspend fun getBookMarkedCharacterById(characters: MutableList<CharacterList>) {        viewModelScope.launch(Dispatchers.IO) {            characters.forEach { character ->                character.isBookMarked = repository.getBookMarkedCharacterById(character.id)            }            withContext(Dispatchers.Main) {                _characterLiveData.postValue(characters)            }        }    }    fun toggleBookmarkForCharacter(character: CharacterList, isBookMarked: Boolean) {        viewModelScope.launch(Dispatchers.IO) {            if (isBookMarked) {                repository.addBookMarkedCharacter(character)            } else {                repository.removeBookMarkedCharacter(character)            }            val updatedCharacter = character.copy(isBookMarked = isBookMarked)            updateCharacterListAfterBookmark(updatedCharacter)        }    }    private fun updateCharacterListAfterBookmark(updatedCharacter: CharacterList) {        val currentList = _characterLiveData.value ?: return        val newList = currentList.map { if (it.id == updatedCharacter.id) updatedCharacter else it }        _characterLiveData.postValue(newList)    }}