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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

// Object ItemDetailsDestination untuk menentukan rute navigasi ke layar detail item.
object ItemDetailsDestination : NavigationDestination {
    override val route = "item_details" // Rute layar detail item.
    override val titleRes = R.string.item_detail_title // Judul layar detail item.
    const val itemIdArg = "itemId" // Argumen yang digunakan untuk ID item.
    val routeWithArgs = "$route/{$itemIdArg}" // Rute dengan argumen ID item.
}

/**
 * Fungsi Composable untuk layar detail item.
 * Menampilkan AppBar, tombol floating untuk mengedit item, dan menampilkan detail item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    navigateToEditItem: (Int) -> Unit, // Fungsi untuk navigasi ke layar edit item.
    navigateBack: () -> Unit, // Fungsi untuk navigasi kembali.
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            // AppBar dengan judul dan tombol untuk navigasi kembali.
            InventoryTopAppBar(
                title = stringResource(ItemDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            // Tombol floating untuk mengedit item.
            FloatingActionButton(
                onClick = { navigateToEditItem(0) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_item_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        // Konten utama layar detail item.
        ItemDetailsBody(
            itemDetailsUiState = ItemDetailsUiState(),
            onSellItem = { },
            onDelete = { },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

/**
 * Fungsi Composable untuk menampilkan isi dari detail item, termasuk tombol untuk menjual dan menghapus.
 */
@Composable
private fun ItemDetailsBody(
    itemDetailsUiState: ItemDetailsUiState,
    onSellItem: () -> Unit, // Fungsi untuk aksi menjual item.
    onDelete: () -> Unit, // Fungsi untuk aksi menghapus item.
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        // Menampilkan detail dari item.
        ItemDetails(
            item = itemDetailsUiState.itemDetails.toItem(),
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol untuk menjual item.
        Button(
            onClick = onSellItem,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = true
        ) {
            Text(stringResource(R.string.sell))
        }

        // Tombol untuk menghapus item, memunculkan dialog konfirmasi.
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }

        // Dialog konfirmasi hapus jika diperlukan.
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

/**
 * Fungsi Composable untuk menampilkan satu item dalam bentuk kartu.
 */
@Composable
fun ItemDetails(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            // Menampilkan detail nama item, jumlah stok, dan harga.
            ItemDetailsRow(
                labelResID = R.string.item,
                itemDetail = item.name,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
            ItemDetailsRow(
                labelResID = R.string.quantity_in_stock,
                itemDetail = item.quantity.toString(),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
            ItemDetailsRow(
                labelResID = R.string.price,
                itemDetail = item.formatedPrice(),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

/**
 * Fungsi Composable untuk menampilkan baris detail item dengan label dan nilai detail.
 */
@Composable
private fun ItemDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID)) // Label untuk detail item.
        Spacer(modifier = Modifier.weight(1f)) // Spacer untuk pemisahan antara label dan nilai detail.
        Text(text = itemDetail, fontWeight = FontWeight.Bold) // Nilai detail item dengan font tebal.
    }
}

/**
 * Fungsi Composable untuk dialog konfirmasi penghapusan item.
 */
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, // Fungsi untuk konfirmasi hapus.
    onDeleteCancel: () -> Unit, // Fungsi untuk batal hapus.
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Tidak melakukan apa-apa jika dialog diabaikan */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no)) // Tombol batal.
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes)) // Tombol konfirmasi hapus.
            }
        }
    )
}

// Preview untuk ItemDetailsScreen dengan daftar contoh item.
@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
    InventoryTheme {
        ItemDetailsBody(
            ItemDetailsUiState(
                outOfStock = true,
                itemDetails = ItemDetails(1, "Pen", "$100", "10")
            ),
            onSellItem = {},
            onDelete = {}
        )
    }
}