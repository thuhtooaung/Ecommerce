package com.thuhtooaung.ecommerce.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.thuhtooaung.ecommerce.R
import com.thuhtooaung.ecommerce.models.BottomNavigationBarItem
import com.thuhtooaung.ecommerce.models.Brand
import com.thuhtooaung.ecommerce.ui.composes.BottomNavigationItem
import com.thuhtooaung.ecommerce.ui.composes.BrandItem
import com.thuhtooaung.ecommerce.ui.composes.HomeFilterView
import com.thuhtooaung.ecommerce.ui.composes.HomeSearchBar
import com.thuhtooaung.ecommerce.ui.composes.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    myViewModel: MainViewModel = viewModel(),
    navigateTo: (route: String) -> Unit
) {

    val products = myViewModel.products.collectAsLazyPagingItems()

    var selectedBottomNavItemId by remember { mutableIntStateOf(1) }
    var selectedBrandId: Int? by remember { mutableStateOf(null) }

    val bottomNavigationBarItems = listOf(
        BottomNavigationBarItem(
            id = 1,
            route = "",
            selectedIcon = R.drawable.ic_home,
            unselectedIcon = R.drawable.ic_home_outline
        ),
        BottomNavigationBarItem(
            id = 2,
            route = "",
            selectedIcon = R.drawable.ic_cart,
            unselectedIcon = R.drawable.ic_cart_outline
        ),
        BottomNavigationBarItem(
            id = 3,
            route = "",
            selectedIcon = R.drawable.ic_bookmark,
            unselectedIcon = R.drawable.ic_bookmark_outline
        ),
        BottomNavigationBarItem(
            id = 4,
            route = "",
            selectedIcon = R.drawable.ic_search,
            unselectedIcon = R.drawable.ic_search_outline
        )
    )

    val brands = listOf(
        Brand(id = 1, name = "Nike", logo = R.drawable.logo_nike),
        Brand(id = 2, name = "Adidas", logo = R.drawable.logo_adidas),
        Brand(id = 3, name = "Under Armour", logo = R.drawable.logo_under_armour),
        Brand(id = 4, name = "Puma", logo = R.drawable.logo_puma),
        Brand(id = 5, name = "Reebok", logo = R.drawable.logo_reebok)
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu_drawer),
                            contentDescription = null
                        )
                    }
                },
                title = { /*TODO*/ },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart_outline),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                bottomNavigationBarItems.forEach { item ->
                    BottomNavigationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        item = item,
                        isSelected = selectedBottomNavItemId == item.id,
                        onClick = { itemId -> selectedBottomNavItemId = itemId }
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        when (products.loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Loading",
                        color = Color.LightGray
                    )
                }
            }

            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    TextButton(onClick = { products.refresh() }) {
                        Text(text = "Refresh")
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding),
                    contentPadding = PaddingValues(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        HomeSearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = brands) { brand ->
                                BrandItem(
                                    modifier = Modifier
                                        .width(72.dp)
                                        .height(56.dp),
                                    brand = brand,
                                    isSelected = selectedBrandId == brand.id,
                                    onClick = { brandId -> selectedBrandId = brandId }
                                )
                            }
                        }
                    }
                    item {
                        HomeFilterView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                    items(count = products.itemCount) { index ->
                        val product = products[index]
                        if (product != null) {
                            ProductItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .padding(horizontal = 16.dp),
                                product = product,
                                onClick = { route -> navigateTo(route) }
                            )
                        }
                    }
                }
            }
        }
    }

}