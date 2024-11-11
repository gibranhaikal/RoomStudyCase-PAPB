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

package com.example.inventory.ui.item

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

// Object ItemEditDestination untuk menentukan rute navigasi ke layar edit item.
object ItemEditDestination : NavigationDestination {
    override val route = "item_edit" // Rute untuk layar edit item.
    override val titleRes = R.string.edit_item_title // Judul layar edit item.
    const val itemIdArg = "itemId" // Argumen untuk ID item.
    val routeWithArgs = "$route/{$itemIdArg}" // Rute dengan argumen ID item.
}

/**
 * Fungsi Composable untuk layar edit item.
 * Menampilkan AppBar dan formulir untuk mengedit data item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    navigateBack: () -> Unit, // Fungsi navigasi kembali.
    onNavigateUp: () -> Unit, // Fungsi untuk navigasi naik.
    modifier: Modifier = Modifier,
    viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory) // ViewModel untuk layar edit item.
) {
    Scaffold(
        topBar = {
            // Menampilkan AppBar dengan judul dan tombol kembali.
            InventoryTopAppBar(
                title = stringResource(ItemEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        // Konten utama layar edit item.
        ItemEntryBody(
            itemUiState = viewModel.itemUiState, // UI state untuk data item.
            onItemValueChange = { }, // Fungsi untuk menangani perubahan nilai item.
            onSaveClick = { }, // Fungsi untuk menyimpan data item yang telah diedit.
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()) // Menggulung layar secara vertikal jika konten panjang.
        )
    }
}

/**
 * Preview untuk layar edit item.
 */
@Preview(showBackground = true)
@Composable
fun ItemEditScreenPreview() {
    InventoryTheme {
        // Menampilkan preview dari layar edit item.
        ItemEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}

