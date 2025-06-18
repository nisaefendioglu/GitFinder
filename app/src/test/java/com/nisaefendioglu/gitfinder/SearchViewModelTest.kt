package com.nisaefendioglu.gitfinder

import com.nisaefendioglu.gitfinder.presentation.ui.search.SearchViewModel
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel()
    }

    @Test
    fun `viewModel_is_not_null_on_init`() {
        assertNotNull(viewModel)
    }
}