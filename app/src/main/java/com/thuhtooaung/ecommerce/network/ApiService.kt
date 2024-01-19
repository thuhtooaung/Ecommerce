package com.thuhtooaung.ecommerce.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.thuhtooaung.ecommerce.models.ProductDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val loggingInterceptor = HttpLoggingInterceptor().setLevel(
    HttpLoggingInterceptor.Level.BASIC
)

private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://ecommerce-23185-default-rtdb.asia-southeast1.firebasedatabase.app/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .build()

interface ApiService {

    @GET("products/{page}.json")
    suspend fun getProducts(
        @Path("page") page: Int
    ): List<ProductDto>?

    @GET("products/{id}.json")
    suspend fun getProduct(
        @Path("id") id: String
    ): ProductDto

}

object ApiHelper {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}