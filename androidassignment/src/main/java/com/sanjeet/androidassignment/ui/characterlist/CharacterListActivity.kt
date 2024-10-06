package com.sanjeet.androidassignment.ui.characterlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanjeet.androidassignment.MyApplication
import com.sanjeet.androidassignment.api.RetrofitHelper
import com.sanjeet.androidassignment.data.characterModel.CharacterList
import com.sanjeet.androidassignment.data.repository.CharacterRepository
import com.sanjeet.androidassignment.databinding.ActivityCharacterListBinding


class CharacterListActivity : AppCompatActivity() {


    private val bookMarDao by lazy {
        (application as MyApplication).database.bookMarkDao()
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            CharacterViewModelFactory(CharacterRepository(RetrofitHelper.apiService, bookMarDao))
        )[CharachterViewModel::class.java]
    }
    private lateinit var binding: ActivityCharacterListBinding
    private val characterListAdapter by lazy {
        CharacterListAdapter(arrayListOf()) { characterList, isStarred ->
            starClicked(characterList, isStarred)
        }
    }

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


    private fun starClicked(characterList: CharacterList, isBookMarked: Boolean) {
        viewModel.bookMarkCharacter(characterList, isBookMarked)
    }
}