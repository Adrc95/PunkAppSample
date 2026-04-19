package com.adrc95.punkappsample.ui.beerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrc95.feature.beers.presentation.R
import com.adrc95.feature.beers.presentation.databinding.FragmentBeerListBinding
import com.adrc95.punkappsample.ui.beerlist.actions.buildBeerListUiActions
import com.adrc95.punkappsample.ui.beerlist.adapter.BeerAdapter
import com.adrc95.punkappsample.ui.beerlist.state.BeerListViewEvent
import com.adrc95.punkappsample.ui.common.EndlessRecyclerOnScrollListener
import com.adrc95.punkappsample.ui.common.extension.diff
import com.adrc95.punkappsample.ui.common.extension.launchAndCollect
import com.adrc95.punkappsample.ui.common.extension.setVisible
import com.adrc95.punkappsample.ui.common.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerListFragment : BaseFragment<FragmentBeerListBinding>(), SearchView.OnQueryTextListener {

    private val adapter: BeerAdapter by lazy { BeerAdapter(viewModel::onBeerClicked) }

    private val viewModel: BeerListViewModel by viewModels()

    private lateinit var scrollListener: EndlessRecyclerOnScrollListener

    private val uiActions by lazy { buildBeerListUiActions() }

    override fun bindView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBeerListBinding =
        FragmentBeerListBinding.inflate(layoutInflater, container, false)

    override fun onDestroyView() {
        binding.rvBeers.adapter = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPullDownRefresh()
        initializeList()
        initializeMenu()
        initializeState()
        initializeEvents()
    }

    private fun initializeEvents() {
        viewLifecycleOwner.launchAndCollect(viewModel.events) { event ->
            when (event) {
                is BeerListViewEvent.ShowError -> uiActions.showLoadingError(binding.root)

                is BeerListViewEvent.NavigateToBeerDetail -> uiActions.openBeerDetail(event.beerId)

                is BeerListViewEvent.EnableEndlessScroll ->
                    scrollListener.enabledScrollEnd = event.enabled
            }
        }
    }

    private fun initializeMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_search, menu)
                    configureSearchView(menu.findItem(R.id.searchBar))
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun configureSearchView(searchItem: MenuItem) {
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search)
        searchView.isIconified = true
        searchView.setOnQueryTextListener(this)
    }

    private fun initializeState() = with(viewModel.state) {
        diff(viewLifecycleOwner, { it.isLoading }) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        diff(viewLifecycleOwner, { it.beers }) { beers -> adapter.submitList(beers) }
    }

    private fun initializeList() {
        val layoutManager = binding.rvBeers.layoutManager as LinearLayoutManager
        binding.rvBeers.itemAnimator = null
        binding.rvBeers.setHasFixedSize(true)
        binding.rvBeers.adapter = adapter
        scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(page: Int) {
                viewModel.onLoadMore(page)
            }

        }
        binding.rvBeers.addOnScrollListener(scrollListener)
    }

    private fun initPullDownRefresh() = with(binding) {
        refreshLayout.setOnRefreshListener {
            scrollListener.resetState()
            viewModel.onRefreshBeers()
        }
    }

    private fun showLoading() = with(binding) {
        refreshLayout.isRefreshing = true
        shimmerViewContainer.setVisible(true)
    }

    private fun hideLoading() = with(binding) {
        refreshLayout.isRefreshing = false
        shimmerViewContainer.setVisible(false)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean =
        viewModel.onQueryTextChange(newText).let { true }
}
