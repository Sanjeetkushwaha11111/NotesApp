package com.sanjeet.androidassignment.ui.characterlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanjeet.androidassignment.api.RetrofitHelper
import com.sanjeet.androidassignment.data.repository.CharacterRepository
import com.sanjeet.androidassignment.databinding.ActivityCharacterListBinding

class CharacterListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            CharacterViewModelFactory(CharacterRepository(RetrofitHelper.apiService))
        )[CharachterViewModel::class.java]
    }
    private lateinit var binding: ActivityCharacterListBinding
    private val characterListAdapter by lazy { CharacterListAdapter(arrayListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listRv.apply {
            adapter = characterListAdapter
            layoutManager = LinearLayoutManager(this@CharacterListActivity)
        }
        viewModel.getCharacterList()

        viewModel.characterLiveData.observe(this) { characters ->
            characterListAdapter.updateAdapter(characters)
        }
    }
}