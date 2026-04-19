package com.adrc95.punkappsample.ui.beerlist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adrc95.feature.beers.presentation.databinding.ItemBeerListBinding
import com.adrc95.punkappsample.ui.beerlist.model.BeerDisplayModel
import com.adrc95.punkappsample.ui.common.extension.loadUrl

class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemBeerListBinding.bind(view)

    fun render(beer : BeerDisplayModel) = with(binding) {
        ivBeer.loadUrl(beer.imageUrl)
        tvBeerName.text = beer.name
        tvBeerDescription.text = beer.description
    }
}
