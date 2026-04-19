package com.adrc95.punkappsample.ui.beerlist.actions

import android.content.Context
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.adrc95.feature.beers.presentation.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.buildBeerListUiActions(
    context: Context = requireContext(),
    navController: NavController = findNavController(),
) = BeerListUiActions(context, navController)

class BeerListUiActions(
    private val context: Context,
    private val navController: NavController,
) {
    fun openBeerDetail(beerId: Long) {
        navController.navigate("punkappsample://beer/$beerId".toUri())
    }

    fun showLoadingError(view: View) {
        Snackbar.make(
            view,
            context.getString(R.string.loading_beer_error),
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}
