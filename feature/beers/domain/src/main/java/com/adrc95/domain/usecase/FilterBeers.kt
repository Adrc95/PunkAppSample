package com.adrc95.domain.usecase

import com.adrc95.domain.model.Beer
import javax.inject.Inject

class FilterBeers @Inject constructor() {
    operator fun invoke(
        beers: List<Beer>,
        query: String,
    ): List<Beer> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) return beers

        return beers.filter { beer ->
            beer.name.contains(normalizedQuery, ignoreCase = true)
        }
    }
}
