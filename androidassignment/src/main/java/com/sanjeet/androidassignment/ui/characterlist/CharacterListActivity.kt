package com.sanjeet.androidassignment.ui.characterlist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sanjeet.androidassignment.R
import com.sanjeet.androidassignment.api.RetrofitHelper
import com.sanjeet.androidassignment.data.repository.CharacterRepository
import timber.log.Timber

class CharacterListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            CharacterViewModelFactory(CharacterRepository(RetrofitHelper.apiService))
        )[CharachterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_character_list)
        viewModel.getCharacterList()
        viewModel.characterLiveData.observe(this) {
            for (i in it) {
                Timber.e(">>>>name is ${i.name}")
            }
        }

    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val nw = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(nw) ?: return false

        return networkCapabilities.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        }
    }
}