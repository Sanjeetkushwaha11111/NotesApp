package com.sanjeet.androidassignment.ui.characterlistimport androidx.lifecycle.ViewModelimport androidx.lifecycle.ViewModelProviderimport com.sanjeet.androidassignment.data.repository.CharacterRepositoryclass CharacterViewModelFactory(private val repository: CharacterRepository) :    ViewModelProvider.Factory {    override fun <T : ViewModel> create(modelClass: Class<T>): T {        if (modelClass.isAssignableFrom(CharachterViewModel::class.java)) {            @Suppress("UNCHECKED_CAST")            return CharachterViewModel(repository) as T        }        throw IllegalArgumentException("Unknown ViewModel class")    }}