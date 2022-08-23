package com.example.justeatsample.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class DaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: DataBase
    private lateinit var dao: Dao
    private lateinit var menuItem: MenuItemDbModel


    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.dataBaseDao()
        menuItem = MenuItemDbModel(
            "name",
            "open",
            "https://test.com",
            false,
            uuid = "1",
            averageProductPrice = 1,
            bestMatch = 1,
            deliveryCosts = 100,
            distance = 500,
            minCost = 19,
            newest = 10,
            popularity = 5,
            ratingAverage = 4.5
        )
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMenuItem() = runTest {

        dao.insert(menuItem)

        val list = dao.getAllMenuItems()

        assertThat(list).contains(menuItem)

    }

    @Test
    fun testUpdateMenuItemFavoriteState() = runTest {
        dao.insert(menuItem)
        dao.update(true, menuItem.uuid)


        val list = dao.getAllMenuItems()

        assertThat(list.find { it.uuid == menuItem.uuid }?.isFavorite).isEqualTo(true)

    }

}