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

package com.example.inventory.data

import android.content.Context

/**
 * AppContainer adalah sebuah antarmuka yang berfungsi sebagai kontainer untuk dependency injection.
 * Antarmuka ini menyediakan referensi ke instance dari ItemsRepository yang bisa diakses oleh komponen
 * atau kelas yang membutuhkannya.
 */
interface AppContainer {
    // Properti bertipe ItemsRepository. Ini akan menampung instance dari ItemsRepository,
    // yang nantinya dapat diakses oleh kelas-kelas lain yang membutuhkan.
    val itemsRepository: ItemsRepository
}

/**
 * Implementasi dari [AppContainer] yang menyediakan instance dari [OfflineItemsRepository].
 * AppDataContainer mengambil context sebagai parameter untuk akses ke database atau resource Android.
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementasi dari [ItemsRepository]. Menggunakan properti 'lazy' agar instance hanya
     * diinisialisasi ketika pertama kali digunakan, sehingga menghemat resource.
     */
    override val itemsRepository: ItemsRepository by lazy {
        // Membuat instance OfflineItemsRepository dengan mengakses itemDao dari database Inventory.
        // InventoryDatabase.getDatabase(context) digunakan untuk mendapatkan instance database dengan context.
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}

