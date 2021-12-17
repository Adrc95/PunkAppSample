package com.adrc95.punkappsample.ui.beerlist

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.R
import com.adrc95.punkappsample.ui.common.extension.basicDiffUtil
import com.adrc95.punkappsample.ui.common.extension.inflate
import kotlin.properties.Delegates

class BeerAdapter(private val listener: (Beer) -> Unit) : RecyclerView.Adapter<BeerViewHolder>(), Filterable  {

    var filterBeers: List<Beer> by basicDiffUtil(
        emptyList(),
        areContentsTheSame = {old, new -> old == new},
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    var beers: List<Beer> by Delegates.observable(emptyList()) { _, _, new ->
        filterBeers = new
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = parent.inflate(R.layout.item_beer_list, false)
        return BeerViewHolder(view)
    }

    override fun getItemCount(): Int = filterBeers.size

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = filterBeers[position]
        holder.render(beer)
        holder.itemView.setOnClickListener { listener(beer) }
    }

    override fun getFilter(): Filter = beerFilter

    private val beerFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val query = constraint.toString().trim()

            val filteredList = beers.filter {
                it.name.contains(query, ignoreCase = true)
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
                Handler(Looper.getMainLooper()).postDelayed( {
                    if (results.values is List<*>) {
                        filterBeers = results.values as List<Beer>
                    }
                }, 500)
        }
    }
}
