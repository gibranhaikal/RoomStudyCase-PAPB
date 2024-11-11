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

package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item

/**
 * ViewModel untuk mengambil semua item yang ada di database Room.
 * ViewModel berfungsi untuk mengelola data UI terkait dengan siklus hidup (lifecycle) dan memisahkan
 * logika bisnis dari UI. ViewModel ini akan menyediakan data untuk layar Home.
 */
class HomeViewModel() : ViewModel() {
    companion object {
        // Konstanta untuk mendefinisikan batas waktu (timeout) sebesar 5000 milidetik (5 detik).
        // Dapat digunakan untuk membatasi waktu operasi tertentu seperti loading data dari database.
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Kelas data untuk UI State pada layar HomeScreen.
 * HomeUiState berfungsi untuk merepresentasikan keadaan UI saat ini,
 * seperti daftar item yang akan ditampilkan. Dengan mengelola UI state, kita bisa
 * memastikan UI selalu menampilkan data yang sesuai dengan kondisi terbaru.
 *
 * @param itemList Daftar item yang akan ditampilkan pada layar Home, dengan default nilai list kosong.
 */
data class HomeUiState(val itemList: List<Item> = listOf())