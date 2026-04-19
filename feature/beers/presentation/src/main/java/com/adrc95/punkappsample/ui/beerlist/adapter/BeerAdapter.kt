package com.adrc95.punkappsample.ui.beerlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.adrc95.feature.beers.presentation.R
import com.adrc95.punkappsample.ui.beerlist.model.BeerDisplayModel
import com.adrc95.punkappsample.ui.common.extension.basicDiffUtil
import com.adrc95.punkappsample.ui.common.extension.inflate

class BeerAdapter(
    private val listener: (BeerDisplayModel) -> Unit,
) : ListAdapter<BeerDisplayModel, BeerViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = parent.inflate(R.layout.item_beer_list, false)
        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = getItem(position)
        holder.render(beer)
        holder.itemView.setOnClickListener { listener(beer) }
    }
}
