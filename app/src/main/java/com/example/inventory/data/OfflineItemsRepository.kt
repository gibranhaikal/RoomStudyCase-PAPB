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

// Kelas OfflineItemsRepository yang mengimplementasikan interface ItemsRepository.
// Repository ini menggunakan ItemDao sebagai sumber data untuk melakukan operasi pada database secara offline.
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {

    // Mengambil semua item dari database sebagai Flow<List<Item>>, yang dapat diamati untuk setiap perubahan data.
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    // Mengambil item berdasarkan id dari database sebagai Flow<Item?>,
    // memungkinkan pengamatan perubahan pada item yang spesifik.
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    // Menyisipkan item baru ke dalam database.
    // Fungsi ini bersifat suspend sehingga dijalankan secara asinkron.
    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    // Menghapus item dari database.
    // Fungsi ini juga bersifat suspend dan dijalankan secara asinkron.
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    // Memperbarui data item yang ada di database.
    // Fungsi ini bersifat suspend dan dijalankan secara asinkron.
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}