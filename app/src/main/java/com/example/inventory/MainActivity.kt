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
package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.inventory.ui.theme.InventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Mengaktifkan mode edge-to-edge untuk pengalaman layar penuh
        enableEdgeToEdge()

        // Memanggil onCreate superclass untuk menjalankan logika bawaan Activity
        super.onCreate(savedInstanceState)

        // Mengatur konten tampilan Activity dengan menggunakan Compose
        setContent {
            // Mengaplikasikan tema InventoryTheme ke seluruh konten aplikasi
            InventoryTheme {
                // Surface adalah container dengan warna background dari tema aplikasi
                Surface(
                    modifier = Modifier.fillMaxSize(), // Mengisi ukuran layar penuh
                    color = MaterialTheme.colorScheme.background // Warna background dari tema Material
                ) {
                    // Memanggil InventoryApp sebagai composable utama yang berisi seluruh navigasi aplikasi
                    InventoryApp()
                }
            }
        }
    }
}