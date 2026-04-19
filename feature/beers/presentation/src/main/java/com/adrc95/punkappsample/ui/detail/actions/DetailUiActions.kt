package com.adrc95.punkappsample.ui.detail.actions

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.adrc95.feature.beers.presentation.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.buildDetailUiActions(
    context: Context = requireContext(),
) = DetailUiActions(context)

class DetailUiActions(
    private val context: Context,
) {
    fun showLoadingError(view: View) {
        Snackbar.make(
            view,
            context.getString(R.string.loading_beer_error),
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}
