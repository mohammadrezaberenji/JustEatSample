package com.example.justeatsample.data.source.repository

import com.example.justeatsample.data.db.Dao
import com.example.justeatsample.data.db.MenuItemDbModel
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import com.example.justeatsample.data.source.remote.webService.WebService
import com.example.justeatsample.data.source.repository.RepositoryImp
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import com.google.common.truth.Truth.assertThat
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryUnitTest {


    lateinit var repositoryImp: RepositoryImp

    @MockK
    lateinit var webService: WebService

    @MockK
    lateinit var dao: Dao


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryImp = RepositoryImp(webService, dao)
    }

    @Test
    fun testGettingListFromRemote() = runBlocking {
        println("testGettingListFromRemote")
        val menuItemEntity = MenuItemsEntity(
            arrayListOf(
                Restaurant(
                    name = "test",
                    sortingValues = SortingValues(
                        averageProductPrice = 1,
                        bestMatch = 2,
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
                    name = "otherTest",
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
                    id = "2",
                    isFavorite = false
                ) ,
                Restaurant(
                    name = "otherTest",
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
        )
        val modelDbList = arrayListOf<MenuItemDbModel>(
            MenuItemDbModel(
                name = "test",
                averageProductPrice = 1,
                bestMatch = 2,
                deliveryCosts = 3,
                distance = 4,
                minCost = 50,
                newest = 10,
                popularity = 4,
                ratingAverage = 5.5,
                isFavorite = false,
                uuid = "1",
                status = "open",
                imageUrl = "https://test.com"
            ),
            MenuItemDbModel(
                name = "otherTest",
                averageProductPrice = 4,
                bestMatch = 2,
                deliveryCosts = 5,
                distance = 4,
                minCost = 100,
                newest = 100,
                popularity = 4,
                ratingAverage = 5.5,
                imageUrl = "https://test.com",
                status = "close",
                uuid = "2",
                isFavorite = false
            ) ,
            MenuItemDbModel(
                name = "otherTest",
                averageProductPrice = 4,
                bestMatch = 2,
                deliveryCosts = 5,
                distance = 4,
                minCost = 100,
                newest = 100,
                popularity = 4,
                ratingAverage = 5.5,
                imageUrl = "https://test.com",
                status = "close",
                uuid = "3",
                isFavorite = true
            )
        )

        val spy = spyk<RepositoryImp>(repositoryImp)


        coEvery { repositoryImp.getItemsFromDb() } returns menuItemEntity.restaurants
        coEvery { dao.getAllMenuItems() as ArrayList } returns modelDbList

        coEvery { spy.getList() } returns flowOf(ResponseState.Success(menuItemEntity))



        repositoryImp.getList().collect() {
            when (it) {
                is ResponseState.Success -> {
                    assertThat(it.data).isEqualTo(menuItemEntity)
                }
            }
        }
    }


}