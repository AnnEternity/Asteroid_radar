package com.udacity.asteroidradar.main

import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidData
import com.udacity.asteroidradar.databinding.ListItemBinding

class ItemViewHolder(
    private val item: ListItemBinding,
    private val onClick: (Asteroid) -> Unit
) :
    RecyclerView.ViewHolder(item.root) {

    fun bind(asteroidItem: Asteroid) {
        item.asteroid = asteroidItem
        item.root.setOnClickListener {
            onClick(asteroidItem)
        }
    }
}