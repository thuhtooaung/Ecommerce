package com.thuhtooaung.ecommerce.ui.composes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thuhtooaung.ecommerce.R
import com.thuhtooaung.ecommerce.models.BottomNavigationBarItem
import com.thuhtooaung.ecommerce.ui.theme.orange

@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    item: BottomNavigationBarItem,
    isSelected: Boolean,
    onClick: (itemId: Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = { onClick(item.id) }
        ) {
            Icon(
                painter = painterResource(id = if (isSelected) item.selectedIcon else item.unselectedIcon),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .width(48.dp)
                .height(
                    WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.Bottom
        ) {
            AnimatedVisibility(
                visible = isSelected,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_indicator),
                    contentDescription = null,
                    modifier = Modifier
                        .width(48.dp)
                        .height(24.dp),
                    tint = orange
                )
            }
        }
    }
}