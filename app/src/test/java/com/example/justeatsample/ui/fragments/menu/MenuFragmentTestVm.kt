package com.example.justeatsample.ui.fragments.menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.justeatsample.MainCoroutineRule

import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import com.example.justeatsample.data.source.repository.FakeRepository
import com.example.justeatsample.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MenuFragmentTestVm {


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MenuFragmentVm
    private lateinit var fakeRepository: FakeRepository
    private lateinit var listOfRestaurants: ArrayList<Restaurant>

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        viewModel = MenuFragmentVm(fakeRepository)
        listOfRestaurants = arrayListOf(
            Restaurant(
                name = "test1",
                sortingValues = SortingValues(
                    averageProductPrice = 1,
                    bestMatch = 1,
                    deliveryCosts = 3,
                    distance = 4,
                    minCost = 50,
                    newest = 10,
                    popularity = 4,
                    ratingAverage = 5.5
                ), status = "open",
                imageUrl = "https://test.com",
                id = "1",
                isFavorite = false
            ),
            Restaurant(
                name = "test2",
                sortingValues = SortingValues(
                    averageProductPrice = 4,
                    bestMatch = 5,
                    deliveryCosts = 5,
                    distance = 4,
                    minCost = 100,
                    newest = 100,
                    popularity = 4,
                    ratingAverage = 5.5
                ), status = "close",
                imageUrl = "https://test.com",
                id = "2",
                isFavorite = false
            ),
            Restaurant(
                name = "test3",
                sortingValues = SortingValues(
                    averageProductPrice = 4,
                    bestMatch = 2,
                    deliveryCosts = 5,
                    distance = 4,
                    minCost = 100,
                    newest = 100,
                    popularity = 4,
                    ratingAverage = 5.5
                ), status = "close",
                imageUrl = "https://test.com",
                id = "3",
                isFavorite = true
            )
        )
    }

    @Test
    fun `get response state of success with fake list and check with the view model's live data items `() =
        runTest {
            fakeRepository.getList().collect {
                assertThat(it.data).isEqualTo(viewModel.items.getOrAwaitValueTest().data)
            }

        }

    @Test
    fun `sort list with favorite and un favorite and expected the favorite item be in top`() {
        val tempFav = listOfRestaurants.filter { it.isFavorite } as ArrayList
        val tempNotFave = listOfRestaurants.filter { !it.isFavorite } as ArrayList
        val finalList = ArrayList<Restaurant>()
        finalList.addAll(tempFav)
        finalList.addAll(tempNotFave)


        assertThat(tempFav.indexOf(finalList.find { it.isFavorite })).isEqualTo(0)

    }

    @Test
    fun `sort with favorite , opening status  and best match test`() {
        val tempFav = listOfRestaurants.filter { it.isFavorite } as ArrayList
        val tempNotFave = listOfRestaurants.filter { !it.isFavorite } as ArrayList

        tempFav.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.bestMatch })
        tempNotFave.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.bestMatch })

        val finalList = ArrayList<Restaurant>()

        finalList.addAll(tempFav)
        finalList.addAll(tempNotFave)

        assertThat(finalList.indexOf(finalList.find { it.id == "2" })).isEqualTo(2)
    }

}