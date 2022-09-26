package com.astro.test.rosyid.developer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.astro.test.rosyid.R
import com.astro.test.rosyid.databinding.ItemDeveloperBinding
import com.astro.test.rosyid.db.network.model.Developer
import com.astro.test.rosyid.utils.AppUtils.showImage
import com.astro.test.rosyid.utils.OnClickListener

class ListDeveloperAdapter constructor(val listener: OnClickListener) : RecyclerView.Adapter<ListDeveloperAdapter.ListViewHolder>() {

    private val developers = arrayListOf<Developer>()
    private val local = arrayListOf<Developer>()
    
    fun setData(data: List<Developer>, dataLocal: List<Developer> = arrayListOf()) {
        developers.clear()
        developers.addAll(data)
        local.clear()
        local.addAll(dataLocal)
    }

    inner class ListViewHolder(private val binding: ItemDeveloperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(developer: Developer) {
            showImage(
                binding.root.context,
                developer.avatar_url,
                binding.imgDeveloper
            )

            binding.apply {
                tvDeveloper.text = developer.login

                toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        listener.onChecked(developer)
                    } else {
                        listener.onUnchecked(developer)
                    }
                }

                if (developer.favorite) toggleFavorite.isChecked = true

                local.forEach { dev ->
                    if (developer.login == dev.login) toggleFavorite.isChecked = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_developer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(developers[position])
    }

    override fun getItemCount(): Int = developers.size
}