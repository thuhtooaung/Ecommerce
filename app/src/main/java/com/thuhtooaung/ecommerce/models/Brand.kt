package com.thuhtooaung.ecommerce.models

import androidx.annotation.DrawableRes

data class Brand(
    val id: Int,
    val name: String,
    @DrawableRes val logo: Int
)