package com.thuhtooaung.ecommerce.models

data class ProductDto(
    val id: Int,
    val name: String,
    val brandId: Int,
    val brandName: String,
    val categoryId: Int,
    val categoryName: String,
    val price: Double,
    val imageUrl: String,
    val rating: Double,
    val sizes: String,
    val sizeNumbers: List<String>,
    val description: String,
    val deliveryAndReturns: String,
    val colors: List<String>,
    val isFavourite: Boolean
)
