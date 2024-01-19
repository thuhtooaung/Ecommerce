package com.thuhtooaung.ecommerce.ui.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thuhtooaung.ecommerce.models.Brand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandItem(
    modifier: Modifier = Modifier,
    brand: Brand,
    isSelected: Boolean,
    onClick: (id: Int) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(
                alpha = 0.2f
            )
        ),
        border = if (isSelected) BorderStroke(
            width = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        ) else null,
        onClick = { onClick(brand.id) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = brand.logo),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                alpha = if (isSelected) 1.0f else 0.2f
            )
        }
    }
}