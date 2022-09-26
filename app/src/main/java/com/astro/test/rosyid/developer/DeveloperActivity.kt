package com.astro.test.rosyid.developer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.test.rosyid.R
import com.astro.test.rosyid.databinding.ActivityDeveloperBinding
import com.astro.test.rosyid.favorite.FavoriteActivity
import com.astro.test.rosyid.db.network.model.Developer
import com.astro.test.rosyid.utils.AppUtils.delay
import com.astro.test.rosyid.utils.AppUtils.searchByUsername
import com.astro.test.rosyid.utils.NetworkUtils.populateState
import com.astro.test.rosyid.utils.OnClickListener
import com.telkom.privyidtest.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class DeveloperActivity : AppCompatActivity(), CoroutineScope, OnClickListener {

    private lateinit var binding: ActivityDeveloperBinding
    private lateinit var developerViewModel: DeveloperViewModel
    private lateinit var developerAdapter: ListDeveloperAdapter
    private val developerData = arrayListOf<Developer>()
    private val favoriteData = arrayListOf<Developer>()
    private var isSearchable = false
    private var keyword: String? = null
    private var sortDataBy = 0

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_developer)
        developerViewModel = ViewModelProvider(this)[DeveloperViewModel::class.java]
        developerAdapter = ListDeveloperAdapter(this)

        setupViews()
        getDevelopers()
        observerDeveloper()
        getFavorites()
        observerFavorites()
    }

    private fun setupViews() {
        binding.apply {
            searchByUsername(svDeveloper) { word ->
                delay(300) {
                    isSearchable = word.isNullOrEmpty()
                    keyword = word

                    if (!word.isNullOrEmpty())
                        getDevelopersByName(word)
                    else
                        getDevelopers()
                }
            }
            btnFavorite.setOnClickListener {
                startActivity(
                    Intent(this@DeveloperActivity, FavoriteActivity::class.java)
                )
            }
            setupRadioButton()
        }
    }

    private fun setupRadioButton() {
        binding.radioGroup.apply {
            check(R.id.radio_asc)
            sortDataBy = R.id.radio_asc
            setOnCheckedChangeListener { _, id ->
                sortDataBy = id

                if (!keyword.isNullOrEmpty())
                    getDevelopersByName(keyword!!)
                else
                    getDevelopers()
            }
        }
    }


    private fun getDevelopers() {
        developerViewModel.getDeveloper()
    }

    private fun getDevelopersByName(name: String) {
        developerViewModel.getDeveloperByName(name)
    }

    private fun getFavorites() {
        developerViewModel.getFavorites()
    }

    private fun observerDeveloper() {
        developerViewModel.developers.observe(this) { developers ->
            populateState(
                developers,
                onSuccess = {
                    developers.data?.let { data ->
                        developerData.clear()
                        developerData.addAll(data)
                        setupDeveloperAdapter()
                    }
                },
                onEmpty = {
                    binding.rvDeveloper.visibility = View.INVISIBLE
                    binding.rvPlaceholder.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun observerFavorites() {
        developerViewModel.favorites.observe(this) { favorites ->
            populateState(
                favorites,
                onSuccess = {
                    favorites.data?.let { data ->
                        favoriteData.clear()
                        favoriteData.addAll(data)
                    }
                }
            )
        }
    }

    private fun setupDeveloperAdapter() {
        if (sortDataBy == R.id.radio_asc)
            developerData.sortBy { it.login }
        else
            developerData.sortByDescending { it.login }

        developerAdapter.setData(developerData, favoriteData)

        binding.rvDeveloper.apply {
            layoutManager = LinearLayoutManager(this@DeveloperActivity)
            adapter = developerAdapter
        }
    }

    override fun onChecked(developer: Developer) {
        val data = Developer(
            developer.id,
            developer.login,
            developer.avatar_url,
            developer.url,
            true
        )
        developerViewModel.addFavorite(data)
    }

    override fun onUnchecked(developer: Developer) {
        developerViewModel.deleteFavorite(developer)
    }
}