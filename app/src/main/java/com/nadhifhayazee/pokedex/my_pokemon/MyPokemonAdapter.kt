package com.nadhifhayazee.pokedex.my_pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nadhifhayazee.core_model.MyPokemon
import com.nadhifhayazee.pokedex.databinding.ItemMyPokemonLayoutBinding

class MyPokemonAdapter(
    private val onItemClicked: (String) -> Unit,
    private val onReleaseClicked: (Int) -> Unit,
    private val onRenameClicked: (Int) -> Unit
) : ListAdapter<MyPokemon, MyPokemonAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyPokemonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class ViewHolder(private val binding: ItemMyPokemonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    getItem(absoluteAdapterPosition).name?.let { it1 -> onItemClicked.invoke(it1) }
                }

                btnRelease.setOnClickListener {
                    getItem(absoluteAdapterPosition).id?.let { id -> onReleaseClicked.invoke(id) }
                }

                btnRename.setOnClickListener {
                    getItem(absoluteAdapterPosition).id?.let { id -> onRenameClicked.invoke(id) }
                }
            }
        }

        fun bindView(item: MyPokemon) {
            binding.apply {
                tvName.text = item.getPreviewName()
                ivPokemon.load(item.imageUrl)
            }
        }

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyPokemon>() {
            override fun areItemsTheSame(oldItem: MyPokemon, newItem: MyPokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyPokemon, newItem: MyPokemon): Boolean {
                return oldItem == newItem
            }
        }
    }


}