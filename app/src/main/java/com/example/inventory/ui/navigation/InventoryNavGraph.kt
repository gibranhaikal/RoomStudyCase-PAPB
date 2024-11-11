/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.item.ItemDetailsDestination
import com.example.inventory.ui.item.ItemDetailsScreen
import com.example.inventory.ui.item.ItemEditDestination
import com.example.inventory.ui.item.ItemEditScreen
import com.example.inventory.ui.item.ItemEntryDestination
import com.example.inventory.ui.item.ItemEntryScreen

/**
 * Menyediakan Navigation Graph untuk aplikasi.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController, // Kontroler navigasi untuk mengatur pergerakan antar layar.
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route, // Layar awal adalah layar Home.
        modifier = modifier
    ) {
        // Konfigurasi untuk layar Home.
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) }, // Navigasi ke layar tambah item.
                navigateToItemUpdate = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}") // Navigasi ke layar detail item berdasarkan ID.
                }
            )
        }
        // Konfigurasi untuk layar tambah item (Item Entry).
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() }, // Navigasi kembali.
                onNavigateUp = { navController.navigateUp() }
            )
        }
        // Konfigurasi untuk layar detail item.
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType // Tipe argumen ID adalah Int.
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem = {
                    navController.navigate("${ItemEditDestination.route}/$it") // Navigasi ke layar edit item dengan ID tertentu.
                },
                navigateBack = { navController.navigateUp() } // Navigasi kembali.
            )
        }
        // Konfigurasi untuk layar edit item.
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType // Tipe argumen ID adalah Int.
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() }, // Navigasi kembali.
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
