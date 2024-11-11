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

import kotlinx.coroutines.flow.Flow

/**
 * Repository yang menyediakan fungsi untuk memasukkan, memperbarui, menghapus, dan mengambil data [Item]
 * dari sumber data yang diberikan. Repository ini berfungsi sebagai lapisan abstraksi antara data
 * dan logika bisnis aplikasi.
 */
interface ItemsRepository {

    /**
     * Mengambil semua item dari sumber data yang diberikan.
     * Mengembalikan hasil sebagai Flow<List<Item>> untuk mengamati perubahan data secara real-time.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Mengambil item dari sumber data berdasarkan [id] yang sesuai.
     * Mengembalikan hasil sebagai Flow<Item?>, sehingga kita bisa mengamati perubahan data item dengan id tersebut.
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Menyisipkan item baru ke dalam sumber data.
     * Fungsi ini bersifat suspend, yang berarti akan dijalankan secara asinkron.
     */
    suspend fun insertItem(item: Item)

    /**
     * Menghapus item dari sumber data.
     * Fungsi ini juga bersifat suspend dan dijalankan secara asinkron.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Memperbarui item yang sudah ada di sumber data.
     * Fungsi ini bersifat suspend dan dijalankan secara asinkron.
     */
    suspend fun updateItem(item: Item)
}