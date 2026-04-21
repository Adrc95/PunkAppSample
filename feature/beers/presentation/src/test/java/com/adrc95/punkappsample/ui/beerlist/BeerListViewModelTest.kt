package com.adrc95.punkappsample.ui.beerlist

import app.cash.turbine.test
import com.adrc95.domain.builder.beer
import com.adrc95.domain.exception.Error
import com.adrc95.domain.repository.FakeBeerRepository
import com.adrc95.domain.usecase.FilterBeers
import com.adrc95.domain.usecase.GetBeers
import com.adrc95.domain.usecase.RefreshBeers
import com.adrc95.punkappsample.ui.beerlist.mapper.toDisplayModel
import com.adrc95.punkappsample.ui.beerlist.model.PageContentDisplayModel
import com.adrc95.punkappsample.ui.beerlist.state.BeerListUiState
import com.adrc95.punkappsample.ui.beerlist.state.BeerListViewEvent
import com.adrc95.punkappsample.ui.common.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BeerListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(repository: FakeBeerRepository): BeerListViewModel =
        BeerListViewModel(
            filterBeers = FilterBeers(),
            getBeers = GetBeers(repository),
            refreshBeers = RefreshBeers(repository),
        )

    @Test
    fun `given beers in first page when initialized then emits beer list state`() =
        runTest(mainDispatcherRule.scheduler) {
            // Given
            val beers = listOf(
                beer { withId(1L).withName("Punk IPA") },
                beer { withId(2L).withName("Elvis Juice") },
            )
            val repository = FakeBeerRepository().apply {
                setBeers(page = 1, items = beers)
            }
            val viewModel = createViewModel(repository)
            val expectedState = BeerListUiState(
                query = "",
                pageContent = PageContentDisplayModel(
                    currentPage = 1,
                    isLoading = false,
                    beers = beers.map { it.toDisplayModel() },
                ),
            )

            // When
            viewModel.state.test {
                // Then
                val firstState = awaitItem()
                val loadedState = if (firstState.pageContent.isLoading) awaitItem() else firstState
                assertEquals(expectedState, loadedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given beers in next page when load more then updates state with requested page beers`() =
        runTest(mainDispatcherRule.scheduler) {
            // Given
            val firstPageBeers = listOf(
                beer { withId(1L).withName("Punk IPA") },
            )
            val secondPageBeers = listOf(
                beer { withId(2L).withName("Elvis Juice") },
            )
            val repository = FakeBeerRepository().apply {
                setBeers(page = 1, items = firstPageBeers)
                setBeers(page = 2, items = secondPageBeers)
            }
            val viewModel = createViewModel(repository)
            val expectedState = BeerListUiState(
                query = "",
                pageContent = PageContentDisplayModel(
                    currentPage = 2,
                    isLoading = false,
                    beers = secondPageBeers.map { it.toDisplayModel() },
                ),
            )

            // When
            viewModel.state.test {
                var initialState = awaitItem()
                while (initialState.pageContent.isLoading) {
                    initialState = awaitItem()
                }
                viewModel.onLoadMore(page = 2)

                // Then
                var loadedState = awaitItem()
                while (loadedState.pageContent.isLoading) {
                    loadedState = awaitItem()
                }
                assertEquals(expectedState, loadedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given loaded beers when query changes then filters beers and disables endless scroll`() =
        runTest(mainDispatcherRule.scheduler) {
            // Given
            val beers = listOf(
                beer { withId(1L).withName("Punk IPA") },
                beer { withId(2L).withName("Elvis Juice") },
                beer { withId(3L).withName("Punk AF") },
            )
            val repository = FakeBeerRepository().apply {
                setBeers(page = 1, items = beers)
            }
            val viewModel = createViewModel(repository)
            val expectedFilteredBeers = listOf(beers[0], beers[2]).map { it.toDisplayModel() }
            val expectedState = BeerListUiState(
                query = "punk",
                pageContent = PageContentDisplayModel(
                    currentPage = 1,
                    isLoading = false,
                    beers = expectedFilteredBeers,
                ),
            )

            // When
            viewModel.state.test stateTest@{
                val initialState = this@stateTest.awaitItem()
                if (initialState.pageContent.isLoading) {
                    this@stateTest.awaitItem()
                }
                viewModel.events.test {
                    viewModel.onQueryTextChange("punk")

                    // Then
                    assertEquals(expectedState, this@stateTest.awaitItem())
                    assertEquals(BeerListViewEvent.EnableEndlessScroll(false), awaitItem())
                    cancelAndIgnoreRemainingEvents()
                }
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given selected beer when beer clicked then emits navigation event`() =
        runTest(mainDispatcherRule.scheduler) {
            // Given
            val selectedBeer = beer { withId(7L) }.toDisplayModel()
            val repository = FakeBeerRepository()
            val viewModel = createViewModel(repository)

            // When
            viewModel.events.test {
                viewModel.onBeerClicked(selectedBeer)

                // Then
                assertEquals(BeerListViewEvent.NavigateToBeerDetail(7L), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given refresh succeeds when refresh beers then updates loading state`() =
        runTest(mainDispatcherRule.scheduler) {
            // Given
            val beers = listOf(
                beer { withId(1L).withName("Punk IPA") },
            )
            val repository = FakeBeerRepository().apply {
                setBeers(page = 1, items = beers)
            }
            val viewModel = createViewModel(repository)
            val expectedState = BeerListUiState(
                query = "",
                pageContent = PageContentDisplayModel(
                    currentPage = 1,
                    isLoading = false,
                    beers = beers.map { it.toDisplayModel() },
                ),
            )

            // When
            viewModel.state.test {
                var initialState = awaitItem()
                while (initialState.pageContent.isLoading) {
                    initialState = awaitItem()
                }
                viewModel.onRefreshBeers()

                // Then
                var refreshedState = awaitItem()
                while (refreshedState.pageContent.isLoading) {
                    refreshedState = awaitItem()
                }
                assertEquals(expectedState, refreshedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given refresh fails when refresh beers then emits show error event`() =
        runTest(mainDispatcherRule.scheduler) {
            // Given
            val beers = listOf(
                beer { withId(1L).withName("Punk IPA") },
            )
            val repository = FakeBeerRepository().apply {
                setBeers(page = 1, items = beers)
                setRefreshBeersError(Error.Connectivity)
            }
            val viewModel = createViewModel(repository)

            // When
            viewModel.state.test stateTest@{
                var initialState = this@stateTest.awaitItem()
                while (initialState.pageContent.isLoading) {
                    initialState = this@stateTest.awaitItem()
                }
                viewModel.events.test {
                    viewModel.onRefreshBeers()

                    // Then
                    assertEquals(BeerListViewEvent.ShowError, awaitItem())
                    var refreshedState = this@stateTest.awaitItem()
                    while (refreshedState.pageContent.isLoading) {
                        refreshedState = this@stateTest.awaitItem()
                    }
                    assertEquals(false, refreshedState.pageContent.isLoading)
                    cancelAndIgnoreRemainingEvents()
                }
                cancelAndIgnoreRemainingEvents()
            }
        }
}
