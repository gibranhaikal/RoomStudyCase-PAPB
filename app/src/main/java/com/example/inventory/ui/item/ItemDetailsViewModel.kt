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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel untuk mengambil, memperbarui, dan menghapus item dari sumber data di [ItemsRepository].
 */
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle // Digunakan untuk menyimpan dan mengambil state yang diperlukan.
) : ViewModel() {

    // Mengambil ID item dari argumen navigasi, memastikan bahwa itemId tidak null.
    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    companion object {
        // Konstanta untuk batas waktu operasi (timeout) sebesar 5000 milidetik (5 detik).
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * State UI untuk layar ItemDetailsScreen.
 * Menyimpan informasi apakah item sedang dalam stok atau tidak dan detail itemnya.
 *
 * @param outOfStock Status ketersediaan item, defaultnya true (kosong).
 * @param itemDetails Detail dari item, defaultnya adalah instance kosong dari ItemDetails.
 */
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)

