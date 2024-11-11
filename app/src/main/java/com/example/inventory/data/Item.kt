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

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Kelas data Entity yang merepresentasikan satu baris dalam tabel database.
 * Setiap instance dari kelas ini mewakili satu item di tabel "items" pada database.
 */
@Entity(tableName = "items")
data class Item(
    // PrimaryKey adalah kunci utama untuk tabel ini, dengan autoGenerate = true
    // sehingga nilai id akan dihasilkan secara otomatis setiap kali item baru ditambahkan.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Nama item, disimpan dalam kolom name di tabel.
    val name: String,

    // Harga item, disimpan dalam kolom price di tabel.
    val price: Double,

    // Jumlah item yang tersedia, disimpan dalam kolom quantity di tabel.
    val quantity: Int
)