package com.adrc95.punkappsample.ui.beerlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.databinding.ItemBeerListBinding
import com.adrc95.punkappsample.ui.common.extension.loadUrl

class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemBeerListBinding.bind(view)

    fun render(beer : Beer) = with(binding) {
        ivBeer.loadUrl(beer.imageURL)
        tvBeerName.text = beer.name
        tvBeerDescription.text = beer.description
    }
}