package com.thuhtooaung.ecommerce.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.thuhtooaung.ecommerce.data.ProductsPagingSource

class MainViewModel : ViewModel() {

    val products = Pager(config = PagingConfig(pageSize = 20)) {
        ProductsPagingSource()
    }.flow.cachedIn(viewModelScope)

}