package com.adrc95.punkappsample.ui.beerlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.R
import com.adrc95.punkappsample.databinding.FragmentBeerListBinding
import com.adrc95.punkappsample.ui.common.EndlessRecyclerOnScrollListener
import com.adrc95.punkappsample.ui.common.EventObserver
import com.adrc95.punkappsample.ui.common.extension.setVisible
import com.adrc95.punkappsample.ui.common.view.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeerListFragment : BaseFragment<FragmentBeerListBinding>(), SearchView.OnQueryTextListener {

    private val adapter: BeerAdapter by lazy {
        BeerAdapter(viewModel::onBeerClicked)
    }

    private val viewModel: BeerListViewModel by viewModels()

    private lateinit var navController: NavController

    private lateinit var scrollListener : EndlessRecyclerOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        scrollListener.resetState()
        viewModel.onRefreshBeers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.searchBar)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search)
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = true
        searchView.maxWidth = Integer.MAX_VALUE
    }

    override fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): FragmentBeerListBinding =
        FragmentBeerListBinding.inflate(layoutInflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeNavController()
        initPullDownRefresh()
        initializeList()
        initializeNavigation()
        initializeEvents()
    }

    private fun initializeNavController() {
        navController = findNavController()
    }

    private fun initializeEvents() {
        viewModel.error.observe(this, EventObserver {
            displayLoadingError()
        })

    }

    override fun initFlows() {
        launch {
            viewModel.state.collect {
                manageState(it)
            }
        }
    }

    private fun initializeList() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvBeers.layoutManager = layoutManager
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

    private fun initPullDownRefresh()  = with(binding) {
        refreshLayout.setOnRefreshListener {
            scrollListener.resetState()
            viewModel.onRefreshBeers()
        }
    }

    private fun initializeNavigation() {
        viewModel.navigateToBeerDetail.observe(this, EventObserver { beer ->
            val action = BeerListFragmentDirections.actionListBeerFragmentToDetailFragment(beer.id)
            navController.navigate(action)
        })

        viewModel.error.observe(this, EventObserver {
           displayLoadingError()
        })

        viewModel.enabledEndlessScroll.observe(this, EventObserver {
          enabledEndlessScroll(it)
        })
    }


    private fun manageState(state: BeerListViewState) {

        if (state is BeerListViewState.Loading) {
            showLoading()
        }
        else {
            hideLoading()
        }

        when (state) {
            is BeerListViewState.ShowBeers -> {
                renderBeers(state.beers, state.refresh)
            }
            is BeerListViewState.LoadBeers -> {
                viewModel.onLoadBeers()
            }
            is BeerListViewState.ChangeSearchText -> {
                changeSearchText(state.query)
            }
        }
    }

    private fun enabledEndlessScroll(enabled: Boolean) {
        scrollListener.enabledScrollEnd = enabled
    }

    private fun changeSearchText(query: String) {
        adapter.filter.filter(query)
    }

    private fun showLoading() = with(binding)  {
        refreshLayout.isRefreshing = true
        shimmerViewContainer.setVisible(true)
    }

    private fun hideLoading() = with(binding)  {
        refreshLayout.isRefreshing = false
        shimmerViewContainer.setVisible(false)
    }

    private fun renderBeers(beers: List<Beer>, refresh: Boolean) {
        if (refresh) {
            adapter.beers = beers
        }
        else {
            adapter.beers = adapter.beers + beers
        }
    }

    private fun displayLoadingError() {
        Snackbar.make(binding.root, R.string.loading_beer_error, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return  false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.onQueryTextChange(newText)
        return  true
    }


}