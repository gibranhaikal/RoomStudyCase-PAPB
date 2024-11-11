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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    // Fungsi abstrak untuk mengakses DAO (Data Access Object) dari tabel Item.
    abstract fun itemDao(): ItemDao

    companion object {
        // Variabel yang menyimpan instance dari InventoryDatabase, bersifat @Volatile untuk memastikan
        // setiap perubahan pada instance ini dapat langsung terlihat di thread lain.
        @Volatile
        private var Instance: InventoryDatabase? = null

        // Fungsi untuk mendapatkan instance database. Jika instance sudah ada, maka akan dikembalikan,
        // jika belum, maka instance baru akan dibuat.
        fun getDatabase(context: Context): InventoryDatabase {
            // Jika Instance tidak null, langsung dikembalikan, jika null, dibuat instance baru dengan synchronized
            // untuk menghindari pembuatan instance ganda pada kondisi multithreading.
            return Instance ?: synchronized(this) {
                // Membuat database menggunakan Room dengan nama "item_database".
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build().also { Instance = it } // Menyimpan instance yang baru dibuat ke dalam Instance
            }
        }
    }
}