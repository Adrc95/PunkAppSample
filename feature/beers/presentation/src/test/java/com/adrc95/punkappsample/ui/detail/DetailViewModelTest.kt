package com.adrc95.punkappsample.ui.detail

import app.cash.turbine.test
import com.adrc95.domain.builder.beer
import com.adrc95.domain.repository.BeerRepository
import com.adrc95.domain.repository.FakeBeerRepository
import com.adrc95.domain.usecase.GetBeer
import com.adrc95.punkappsample.ui.common.MainDispatcherRule
import com.adrc95.punkappsample.ui.detail.mapper.toDisplayModel
import com.adrc95.punkappsample.ui.detail.state.DetailUiState
import com.adrc95.punkappsample.ui.detail.state.DetailViewEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(
        repository: BeerRepository,
        id: Long,
    ): DetailViewModel = DetailViewModel(
        idBeer = id,
        getBeer = GetBeer(repository)
    )

    @Test
    fun `given existing beer when state is collected then emits loading and beer detail state`() =
        runTest(mainDispatcherRule.scheduler) {
            //Given
            val beerId = 1L
            val beer = beer { withId(beerId) }
            val repository = FakeBeerRepository().apply {
                setBeers(page = 1, items = listOf(beer))
            }
            val viewModel = createViewModel(repository, beerId)
            val expectedState = DetailUiState(
                isLoading = false,
                beer = beer.toDisplayModel(),
            )

            //When
            viewModel.state.test {
                val firstState = awaitItem()
                //Then
                val loadedState = if (firstState.isLoading) awaitItem() else firstState
                assertEquals(expectedState, loadedState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given missing beer when state is collected then emits loading and error state`() =
        runTest(mainDispatcherRule.scheduler) {
            //Given
            val repository = FakeBeerRepository()
            val viewModel = createViewModel(repository, id = 1L)
            val expectedState = DetailUiState(isLoading = false)

            //When
            viewModel.state.test {
                val firstState = awaitItem()
                //Then
                val errorState = if (firstState.isLoading) awaitItem() else firstState
                assertEquals(expectedState, errorState)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given missing beer when state is collected then emits show error event`() =
        runTest(mainDispatcherRule.scheduler) {
            //Given
            val repository = FakeBeerRepository()
            val viewModel = createViewModel(repository, id = 1L)

            //When
            val stateCollectionJob = backgroundScope.launch {
                viewModel.state.collect { }
            }
            viewModel.events.test {
                //Then
                assertEquals(DetailViewEvent.ShowError, awaitItem())
                assertEquals(DetailUiState(isLoading = false), viewModel.state.value)
                cancelAndIgnoreRemainingEvents()
                stateCollectionJob.cancel()
            }
        }
}
