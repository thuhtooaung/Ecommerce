package com.thuhtooaung.ecommerce.ui.screens.product

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuhtooaung.ecommerce.models.ProductDto
import com.thuhtooaung.ecommerce.models.RequestStatus
import com.thuhtooaung.ecommerce.network.RemoteDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pageSize = 5
    private val productId: Int = checkNotNull(savedStateHandle["id"])

    private val _productUiState = MutableStateFlow(ProductUiState())
    val productUiState: StateFlow<ProductUiState> get() = _productUiState

    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            try {
                _productUiState.update { currentState ->
                    currentState.copy(status = RequestStatus.LOADING)
                }
                val pageNumber = if (productId % pageSize == 0) {
                    (productId / pageSize) - 1
                } else {
                    productId / pageSize
                }
                val productIndex = (productId - 1) - (pageNumber * pageSize)
                val response = RemoteDataSource.getProduct(id = "$pageNumber/$productIndex")
                _productUiState.update { currentState ->
                    currentState.copy(
                        status = RequestStatus.SUCCESS,
                        product = response
                    )
                }
            } catch (e: Exception) {
                Log.d(javaClass.name, e.toString())
                _productUiState.update { currentState ->
                    currentState.copy(
                        status = RequestStatus.ERROR,
                        errorMessage = "Failed"
                    )
                }
            }
        }
    }

}

data class ProductUiState(
    val status: RequestStatus = RequestStatus.LOADING,
    val product: ProductDto? = null,
    val errorMessage: String? = null
)