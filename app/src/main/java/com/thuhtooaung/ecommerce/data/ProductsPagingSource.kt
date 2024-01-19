package com.thuhtooaung.ecommerce.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thuhtooaung.ecommerce.models.ProductDto
import com.thuhtooaung.ecommerce.network.RemoteDataSource

class ProductsPagingSource: PagingSource<Int, ProductDto>() {
    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = RemoteDataSource.getProducts(nextPageNumber)
            LoadResult.Page(
                data =  response ?: listOf(),
                prevKey = null,
                nextKey = if (response != null) nextPageNumber.plus(1) else null
            )
        }  catch (e: Exception){
            Log.d(javaClass.name, e.toString())
            LoadResult.Error(Error("Something was wrong!"))
        }
    }
}