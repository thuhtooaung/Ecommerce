package com.thuhtooaung.ecommerce.ui.screens.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.thuhtooaung.ecommerce.R
import com.thuhtooaung.ecommerce.models.RequestStatus
import com.thuhtooaung.ecommerce.ui.theme.orange
import com.thuhtooaung.ecommerce.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    myViewModel: ProductViewModel = viewModel(),
    navigateUp: () -> Unit
) {

    val uiState = myViewModel.productUiState.collectAsState()

    var selectedShoeSize: String by remember { mutableStateOf("") }
    var selectedShoeColor: String by remember { mutableStateOf("") }

    var selectedQuantity: Int by remember { mutableIntStateOf(1) }

    when (uiState.value.status) {
        RequestStatus.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        RequestStatus.ERROR -> {}
        RequestStatus.SUCCESS -> {
            val product = uiState.value.product!!
            selectedShoeSize = product.sizeNumbers.first()
            selectedShoeColor = product.colors.first()
            Box(modifier = modifier) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AsyncImage(
                            model = product.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.categoryName,
                                color = Color.Gray,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Row {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = orange
                                )
                                Text(
                                    text = "(${product.rating})",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "$${product.price}",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Size:",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = product.sizes,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = product.sizeNumbers) { item ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (item == selectedShoeSize) {
                                            orange
                                        } else {
                                            Color.LightGray.copy(
                                                alpha = 0.25f
                                            )
                                        }
                                    ),
                                    onClick = {
                                        selectedShoeSize = item
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(72.dp)
                                            .height(54.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item,
                                            color = if (item == selectedShoeSize) {
                                                white
                                            } else {
                                                orange
                                            },
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        var showDescription by remember { mutableStateOf(false) }
                        var showDeliveryAndReturns by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleSmall
                            )
                            IconButton(onClick = { showDescription = !showDescription }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = showDescription,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Text(
                                text = product.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                        }
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Free Delivery and Returns",
                                style = MaterialTheme.typography.titleSmall
                            )
                            IconButton(onClick = {
                                showDeliveryAndReturns = !showDeliveryAndReturns
                            }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = showDeliveryAndReturns,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Text(
                                text = product.deliveryAndReturns,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                        }
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(items = product.colors) { item ->
                                val color = Color(
                                    android.graphics.Color.parseColor(
                                        item
                                    )
                                )
                                OutlinedCard(
                                    modifier = Modifier.size(40.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = color
                                    ),
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        width = CardDefaults.outlinedCardBorder().width,
                                        color = if (item == "#FFFFFF") Color.LightGray.copy(
                                            alpha = 0.5f
                                        ) else color
                                    ),
                                    onClick = { selectedShoeColor = item }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (selectedShoeColor == item) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = orange
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Quantity",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedCard(
                                    modifier = Modifier.size(24.dp),
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        width = CardDefaults.outlinedCardBorder().width,
                                        color = Color.LightGray.copy(alpha = 0.75f)
                                    ),
                                    onClick = { if (selectedQuantity > 1) selectedQuantity -= 1 }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_decrease),
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = selectedQuantity.toString(),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                OutlinedCard(
                                    modifier = Modifier.size(24.dp),
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        width = CardDefaults.outlinedCardBorder().width,
                                        color = Color.LightGray.copy(alpha = 0.75f)
                                    ),
                                    onClick = { selectedQuantity += 1 }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_increase),
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .navigationBarsPadding(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = orange)
                        ) {
                            Text(
                                text = "Add to Cart",
                                modifier = Modifier.padding(vertical = 16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
                TopAppBar(
                    title = { /*TODO*/ },
                    navigationIcon = {
                        OutlinedIconButton(
                            onClick = navigateUp,
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back_arrow),
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        OutlinedIconButton(
                            onClick = { /*TODO*/ },
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                        ) {
                            Icon(
                                imageVector = if (product.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = orange
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    }

}