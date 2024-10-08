package com.sanjeet.androidassignment.ui.characterlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanjeet.androidassignment.MyApplication
import com.sanjeet.androidassignment.api.RetrofitHelper
import com.sanjeet.androidassignment.data.repository.CharacterRepository
import com.sanjeet.androidassignment.databinding.ActivityCharacterListBinding

class CharacterListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterListBinding
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(
                RetrofitHelper.apiService,
                (application as MyApplication).database.bookMarkDao()
            )
        )
    }
    private val characterListAdapter by lazy {
        CharacterListAdapter(arrayListOf(),viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchCharacterList()
    }

    private fun setupRecyclerView() {
        binding.listRv.apply {
            adapter = characterListAdapter
            layoutManager = LinearLayoutManager(this@CharacterListActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.characterLiveData.observe(this) { characters ->
            characterListAdapter.updateAdapter(characters)
        }
    }

}