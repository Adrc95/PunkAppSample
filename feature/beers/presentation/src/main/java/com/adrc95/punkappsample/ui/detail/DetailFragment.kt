package com.adrc95.punkappsample.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.adrc95.feature.beers.presentation.R
import com.adrc95.feature.beers.presentation.databinding.FragmentDetailBinding
import com.adrc95.punkappsample.ui.detail.actions.buildDetailUiActions
import com.adrc95.punkappsample.ui.detail.model.BeerInfoDisplayModel
import com.adrc95.punkappsample.ui.detail.state.DetailViewEvent
import com.adrc95.punkappsample.ui.common.extension.diff
import com.adrc95.punkappsample.ui.common.extension.launchAndCollect
import com.adrc95.punkappsample.ui.common.extension.loadUrl
import com.adrc95.punkappsample.ui.common.extension.renderInHtmlContent
import com.adrc95.punkappsample.ui.common.extension.setVisible
import com.adrc95.punkappsample.ui.common.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel : DetailViewModel by viewModels()
    private val uiActions by lazy { buildDetailUiActions() }

    override fun bindView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeState()
        initializeEvents()
    }

    private fun initializeState() {
        viewModel.state.diff(viewLifecycleOwner, { it.isLoading }) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel.state.diff(viewLifecycleOwner, { it.beer }) { beer ->
            beer?.let(::renderBeer)
        }
    }

    private fun initializeEvents() {
        viewLifecycleOwner.launchAndCollect(viewModel.events) { events ->
            when (events) {
                DetailViewEvent.ShowError -> {
                    showLoading()
                    uiActions.showLoadingError(binding.root)
                }
            }
        }
    }

    private fun renderBeer(beer: BeerInfoDisplayModel) = with(binding) {
        ivImage.loadUrl(beer.imageUrl)
        tvIpa.text = beer.name
        tvYear.text = beer.firstBrewed
        tvDescription.text = beer.description
        tvAbv.text = getString(R.string.abv_text, beer.abv)
        tvInfo.renderInHtmlContent(
            getString(
                R.string.detail_info_text,
                beer.maltIngredients,
                beer.hops,
                beer.foodPairings,
            )
        )
    }

    private fun showLoading() = with(binding) {
        shimmerViewContainer.setVisible(true)
    }

    private fun hideLoading() = with(binding) {
        shimmerViewContainer.setVisible(false)
    }
}
