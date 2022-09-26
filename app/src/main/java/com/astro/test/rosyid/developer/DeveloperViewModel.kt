package com.astro.test.rosyid.developer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astro.test.rosyid.MyApplication
import com.astro.test.rosyid.db.local.AppDatabase
import com.astro.test.rosyid.db.network.ApiClient
import com.astro.test.rosyid.db.network.model.Developer
import com.telkom.privyidtest.utils.Resource
import kotlinx.coroutines.launch

class DeveloperViewModel: ViewModel() {

    private val client = ApiClient().retrofitClient
    private val database = AppDatabase.getDatabase(MyApplication.applicationContext()).databaseDao()

    private val _developers = MutableLiveData<Resource<List<Developer>>>()
    val developers: MutableLiveData<Resource<List<Developer>>>
        get() = _developers

    private val _favorites = MutableLiveData<Resource<List<Developer>>>()
    val favorites: MutableLiveData<Resource<List<Developer>>>
        get() = _favorites

    fun getDeveloper() {
        _developers.postValue(
            Resource.loading(null)
        )

        viewModelScope.launch {
            try {
                val result = client.getDeveloperList()
                if (result.isNotEmpty())
                    _developers.postValue(
                        Resource.success(result)
                    )
                else
                    _developers.postValue(
                        Resource.empty("empty")
                    )
            } catch (e:Exception) {
                _developers.postValue(
                    Resource.error(e.message)
                )
                e.printStackTrace()
            }
        }
    }

    fun getDeveloperByName(name: String) {
        _developers.postValue(
            Resource.loading(null)
        )

        viewModelScope.launch {
            try {
                val result = client.getDeveloperListByName(name)
                if (result.items.isNotEmpty())
                    _developers.postValue(
                        Resource.success(result.items)
                    )
                else
                    _developers.postValue(
                        Resource.empty("empty")
                    )
            } catch (e:Exception) {
                _developers.postValue(
                    Resource.error(e.message)
                )
                e.printStackTrace()
            }
        }
    }

    fun addFavorite(developer: Developer) {
        viewModelScope.launch {
            try {
                database.addFavorite(developer)
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(developer: Developer) {
        viewModelScope.launch {
            try {
                database.deleteFavorite(developer)
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFavorites() {
        _favorites.postValue(
            Resource.loading(null)
        )

        viewModelScope.launch {
            try {
                val result = database.getFavorite()
                if (result.isNotEmpty())
                    _favorites.postValue(
                        Resource.success(result)
                    )
                else
                    _favorites.postValue(
                        Resource.empty("empty")
                    )
            } catch (e:Exception) {
                _favorites.postValue(
                    Resource.error(e.message)
                )
                e.printStackTrace()
            }
        }
    }
}