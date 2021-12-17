package com.adrc95.punkappsample.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.R
import com.adrc95.punkappsample.databinding.FragmentDetailBinding
import com.adrc95.punkappsample.ui.common.EventObserver
import com.adrc95.punkappsample.ui.common.extension.joinToBulletList
import com.adrc95.punkappsample.ui.common.extension.loadUrl
import com.adrc95.punkappsample.ui.common.extension.renderInHtmlContent
import com.adrc95.punkappsample.ui.common.extension.setVisible
import com.adrc95.punkappsample.ui.common.view.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel : DetailViewModel by viewModels()

    override fun bindView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)

    override fun initFlows() {
       launch {
           viewModel.state.collect {
               manageState(it)
           }
       }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeEvents()
    }

     private fun initializeEvents() {
        viewModel.error.observe(this, EventObserver {
            showLoading()
            displayLoadingError()
        })

    }

    private fun manageState(state: DetailViewState) {
        if (state is DetailViewState.Loading) {
            showLoading()
        }
        else {
            hideLoading()
        }

        when (state) {
            DetailViewState.LoadDetails -> {
                viewModel.onLoadDetail()
            }
            is DetailViewState.RenderBeer -> {
                renderBeer(state.beer)
            }
        }
    }

    private fun renderBeer(beer: Beer) = with(binding) {
        val abv = beer.abv
        val maltIngredients = beer.ingredients.malt.map { it.name }.joinToBulletList()
        val hops =  beer.ingredients.hops.map { it.name }.joinToBulletList()
        val foodPairings = beer.foodPairings.map { it }.joinToBulletList()
        ivImage.loadUrl(beer.imageURL)
        tvIpa.text = beer.name
        tvYear.text = beer.firstBrewed
        tvDescription.text = beer.description
        tvAbv.text =  getString(R.string.abv_text, abv.toString())
        tvInfo.renderInHtmlContent(getString(R.string.detail_info_text, maltIngredients, hops, foodPairings))
    }

    private fun displayLoadingError() {
       Snackbar.make(binding.root, R.string.loading_beer_error, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() = with(binding) {
        shimmerViewContainer.setVisible(true)
    }

    private fun hideLoading() = with(binding) {
        shimmerViewContainer.setVisible(false)
    }

}