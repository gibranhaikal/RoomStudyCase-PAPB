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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

// Object ItemEntryDestination yang menentukan rute navigasi untuk layar input item baru.
object ItemEntryDestination : NavigationDestination {
    override val route = "item_entry" // Rute untuk layar input item.
    override val titleRes = R.string.item_entry_title // Judul layar input item.
}

/**
 * Fungsi Composable untuk layar input item baru.
 * Menampilkan AppBar dan formulir input untuk memasukkan data item baru.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEntryScreen(
    navigateBack: () -> Unit, // Fungsi navigasi kembali.
    onNavigateUp: () -> Unit, // Fungsi untuk navigasi naik.
    canNavigateBack: Boolean = true, // Menentukan apakah tombol navigasi kembali tersedia.
    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory) // ViewModel untuk layar input item.
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            // Menampilkan AppBar dengan judul dan tombol kembali.
            InventoryTopAppBar(
                title = stringResource(ItemEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        // Konten utama layar input item baru.
        ItemEntryBody(
            itemUiState = viewModel.itemUiState, // UI state untuk data item.
            onItemValueChange = viewModel::updateUiState, // Fungsi untuk menangani perubahan nilai item.
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem() // Menyimpan data item yang baru.
                    navigateBack() // Navigasi kembali setelah item disimpan.
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()) // Menggulung layar secara vertikal jika konten panjang.
                .fillMaxWidth()
        )
    }
}

/**
 * Fungsi Composable untuk menampilkan formulir input item.
 * Menyediakan kolom input untuk nama, harga, dan jumlah item, serta tombol simpan.
 */
@Composable
fun ItemEntryBody(
    itemUiState: ItemUiState, // State UI untuk data item yang sedang diinput.
    onItemValueChange: (ItemDetails) -> Unit, // Fungsi untuk menangani perubahan pada nilai item.
    onSaveClick: () -> Unit, // Fungsi untuk menyimpan data item.
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Formulir input item yang mencakup nama, harga, dan jumlah.
        ItemInputForm(
            itemDetails = itemUiState.itemDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        // Tombol untuk menyimpan item, aktif hanya jika input valid.
        Button(
            onClick = onSaveClick,
            enabled = itemUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}

/**
 * Fungsi Composable untuk formulir input item.
 * Menyediakan kolom input untuk nama item, harga, dan jumlah stok.
 */
@Composable
fun ItemInputForm(
    itemDetails: ItemDetails, // Detail item yang sedang diinput.
    modifier: Modifier = Modifier,
    onValueChange: (ItemDetails) -> Unit = {}, // Fungsi untuk menangani perubahan pada detail item.
    enabled: Boolean = true // Menentukan apakah kolom input aktif atau tidak.
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Kolom input untuk nama item.
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange = { onValueChange(itemDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.item_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Kolom input untuk harga item.
        OutlinedTextField(
            value = itemDetails.price,
            onValueChange = { onValueChange(itemDetails.copy(price = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.item_price_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Kolom input untuk jumlah stok item.
        OutlinedTextField(
            value = itemDetails.quantity,
            onValueChange = { onValueChange(itemDetails.copy(quantity = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.quantity_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Menampilkan teks keterangan jika kolom input aktif.
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

/**
 * Preview untuk layar input item.
 */
@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    InventoryTheme {
        // Menampilkan preview dari layar input item.
        ItemEntryBody(
            itemUiState = ItemUiState(
                ItemDetails(
                    name = "Item name", price = "10.00", quantity = "5"
                )
            ),
            onItemValueChange = {},
            onSaveClick = {}
        )
    }
}
