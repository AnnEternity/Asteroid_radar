package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidData
import com.udacity.asteroidradar.databinding.ListItemBinding

class MainAdapter(private val onClick: (Asteroid) -> Unit) :
    RecyclerView.Adapter<ItemViewHolder>() {

    private var listAsteroidCurrent = emptyList<Asteroid>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent,false)
        val viewHolder = ItemViewHolder(binding, onClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listAsteroidCurrent [position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listAsteroidCurrent.size
    }

    fun updateListAsteroidCurrent (newList: List<Asteroid>){
        listAsteroidCurrent = newList
        notifyDataSetChanged()
    }
}