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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel untuk mengambil dan memperbarui item dari sumber data di [ItemsRepository].
 */
class ItemEditViewModel(
    savedStateHandle: SavedStateHandle, // State yang disimpan untuk mengakses data yang diperlukan.
) : ViewModel() {

    /**
     * Menyimpan UI state item saat ini.
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set // Setter bersifat private agar UI state hanya bisa diubah dari dalam ViewModel.

    // Mengambil ID item dari argumen navigasi dan memastikan bahwa itemId tidak null.
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    // Fungsi untuk memvalidasi input item, memastikan nama, harga, dan jumlah tidak kosong.
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}