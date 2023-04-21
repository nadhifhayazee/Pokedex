package com.nadhifhayazee.pokedex.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import com.nadhifhayazee.pokedex.R

object ImageUtil {

    fun getImageAndDominantColor(context: Context, imageView: ImageView, url: String?, onSuccess: (Int) -> Unit) {
        val imageLoader = ImageLoader.Builder(context).build()
        val request = ImageRequest.Builder(context)
            .data(url)
            .target { result ->
                imageView.setImageDrawable(result)
                val bitmap = (result as BitmapDrawable).bitmap
                val rgbImage = bitmap.copy(Bitmap.Config.RGB_565, false)
                Palette.from(rgbImage).generate { palette ->
                    val dominantColor =
                        palette?.getDominantColor(context.getColor(R.color.white))

                    dominantColor?.let { onSuccess.invoke(it) }
                }
            }
            .build()
        imageLoader.enqueue(request)
    }
}