package com.thuhtooaung.ecommerce.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RemoteDataSource {

    suspend fun getProducts(page: Int) = withContext(Dispatchers.IO) {
        ApiHelper.retrofitService.getProducts(page = page)
    }

    suspend fun getProduct(id: String) = withContext(Dispatchers.IO){
        ApiHelper.retrofitService.getProduct(id = id)
    }

}