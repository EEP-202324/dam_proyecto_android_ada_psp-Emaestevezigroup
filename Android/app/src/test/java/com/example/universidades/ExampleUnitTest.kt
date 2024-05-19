package com.example.universidades

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.universidades.api.UniversityApi
import com.example.universidades.models.Category
import com.example.universidades.models.Location
import com.example.universidades.viewmodels.UniversityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class ExampleUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: UniversityViewModel

    @Mock
    private lateinit var observerLocations: Observer<List<Location>>

    @Mock
    private lateinit var observerCategories: Observer<List<Category>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = UniversityViewModel(UniversityApi.retrofitService)
    }

    @Test
    fun testLoadLocations() = testDispatcher.runBlockingTest {
        viewModel.locations.observeForever(observerLocations)

        val expectedLocations = listOf(Location(1, "Location1"), Location(2, "Location2"))
        Mockito.`when`(UniversityApi.retrofitService.getAllLocations()).thenReturn(expectedLocations)

        viewModel.loadLocations()

        val locations = viewModel.locations.value
        assertEquals(expectedLocations, locations)

        viewModel.locations.removeObserver(observerLocations)
    }

    @Test
    fun testLoadCategories() = testDispatcher.runBlockingTest {
        viewModel.categories.observeForever(observerCategories)

        val expectedCategories = listOf(Category(1, "Category1"), Category(2, "Category2"))
        Mockito.`when`(UniversityApi.retrofitService.getAllCategories()).thenReturn(expectedCategories)

        viewModel.loadCategories()

        val categories = viewModel.categories.value
        assertEquals(expectedCategories, categories)

        viewModel.categories.removeObserver(observerCategories)
    }
}