package com.thuhtooaung.ecommerce.models

import androidx.annotation.DrawableRes

data class BottomNavigationBarItem(
    val id: Int,
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
)