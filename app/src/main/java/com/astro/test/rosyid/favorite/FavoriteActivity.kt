package com.astro.test.rosyid.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.test.rosyid.R
import com.astro.test.rosyid.databinding.ActivityFavoriteBinding
import com.astro.test.rosyid.db.network.model.Developer
import com.astro.test.rosyid.developer.DeveloperViewModel
import com.astro.test.rosyid.developer.ListDeveloperAdapter
import com.astro.test.rosyid.utils.NetworkUtils.populateState
import com.astro.test.rosyid.utils.OnClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class FavoriteActivity : AppCompatActivity(), CoroutineScope, OnClickListener {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var developerViewModel: DeveloperViewModel
    private lateinit var favoriteAdapter: ListDeveloperAdapter
    private val favoriteData = arrayListOf<Developer>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)
        developerViewModel = ViewModelProvider(this)[DeveloperViewModel::class.java]
        favoriteAdapter = ListDeveloperAdapter(this)

        getFavorites()
        observerFavorites()
    }

    private fun observerFavorites() {
        developerViewModel.favorites.observe(this) { favorites ->
            populateState(
                favorites,
                onSuccess = {
                    favorites.data?.let { data ->
                        favoriteData.clear()
                        favoriteData.addAll(data)
                        setupFavoritesAdapter()
                    }
                }
            )
        }
    }

    private fun setupFavoritesAdapter() {
        binding.rvFavorite.visibility = View.VISIBLE
        binding.rvPlaceholder.visibility = View.GONE

        favoriteAdapter.setData(favoriteData)

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
        }
    }

    private fun getFavorites() {
        developerViewModel.getFavorites()
    }

    override fun onChecked(developer: Developer) {
        developerViewModel.addFavorite(developer)
    }

    override fun onUnchecked(developer: Developer) {
        developerViewModel.deleteFavorite(developer)
    }
}