package com.thuhtooaung.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thuhtooaung.ecommerce.ui.screens.main.MainScreen
import com.thuhtooaung.ecommerce.ui.screens.product.ProductScreen
import com.thuhtooaung.ecommerce.ui.screens.StartScreen
import com.thuhtooaung.ecommerce.ui.theme.EcommerceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommerceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "start"
                    ) {
                        composable("start") {
                            StartScreen(
                                modifier = Modifier.fillMaxSize(),
                                navigateTo = { route -> navController.navigate(route) }
                            )
                        }
                        composable("main") {
                            MainScreen(
                                modifier = Modifier.fillMaxSize(),
                                navigateTo = { route -> navController.navigate(route) }
                            )
                        }
                        composable(
                            route = "product/{id}",
                            arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
                        ) {
                            ProductScreen(
                                modifier = Modifier.fillMaxSize(),
                                navigateUp = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}