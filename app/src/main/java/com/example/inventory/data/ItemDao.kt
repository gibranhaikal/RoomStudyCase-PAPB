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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    // Fungsi untuk menambahkan item baru ke dalam tabel "items".
    // Menggunakan strategi OnConflictStrategy.IGNORE yang berarti jika ada konflik (seperti item dengan id yang sama),
    // item baru akan diabaikan dan tidak akan menggantikan item yang sudah ada.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    // Fungsi untuk memperbarui item yang sudah ada di tabel "items".
    // Fungsi ini menggantikan data lama dengan data baru dari parameter item.
    @Update
    suspend fun update(item: Item)

    // Fungsi untuk menghapus item dari tabel "items".
    // Item yang dihapus adalah item yang sesuai dengan parameter item yang diberikan.
    @Delete
    suspend fun delete(item: Item)

    // Fungsi untuk mengambil satu item berdasarkan id-nya dari tabel "items".
    // Mengembalikan hasil sebagai Flow<Item> yang dapat dipantau secara real-time jika ada perubahan pada data.
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    // Fungsi untuk mengambil semua item dari tabel "items" dan mengurutkannya secara ascending berdasarkan nama.
    // Mengembalikan hasil sebagai Flow<List<Item>>, sehingga perubahan pada data dapat langsung diamati.
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}

