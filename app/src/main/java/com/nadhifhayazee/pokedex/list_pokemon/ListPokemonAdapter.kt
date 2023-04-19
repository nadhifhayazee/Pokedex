package com.nadhifhayazee.pokedex.list_pokemon

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.nadhifhayazee.core_model.PokemonResult
import com.nadhifhayazee.pokedex.R
import com.nadhifhayazee.pokedex.databinding.ItemListPokemonLayoutBinding

class ListPokemonAdapter(private val onItemClicked: (PokemonResult) -> Unit) :
    PagingDataAdapter<PokemonResult, ListPokemonAdapter.ViewHolder>(diffUtil) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListPokemonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    inner class ViewHolder(private val binding: ItemListPokemonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it1 -> onItemClicked.invoke(it1) }
            }
        }
        fun bindView(item: PokemonResult?) {
            binding.apply {
                ivPokemon.load(item?.getImageUrl())
                tvName.text = item?.name

                val imageLoader = ImageLoader.Builder(binding.root.context).build()
                val request = ImageRequest.Builder(binding.root.context)
                    .data(item?.getImageUrl())
                    .target { result ->
                        ivPokemon.setImageDrawable(result)
                        val bitmap = (result as BitmapDrawable).bitmap
                        val rgbImage = bitmap.copy(Bitmap.Config.RGB_565, false)
                        Palette.from(rgbImage).generate { palette ->
                            val dominantColor =
                                palette?.getDominantColor(binding.root.context.getColor(R.color.white))

                            dominantColor?.let { cardView.setCardBackgroundColor(it) }
                        }
                    }
                    .build()
                imageLoader.enqueue(request)
            }
        }

    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonResult>() {
            override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PokemonResult,
                newItem: PokemonResult
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}