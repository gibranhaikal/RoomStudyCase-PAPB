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

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.R.string
import com.example.inventory.ui.navigation.InventoryNavHost

/**
 * Composable utama yang merepresentasikan layar-layar dalam aplikasi.
 * Menginisialisasi navigasi dengan menggunakan InventoryNavHost.
 */
@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    InventoryNavHost(navController = navController) // Menjalankan navigasi aplikasi dengan navController.
}

/**
 * App bar untuk menampilkan judul dan menampilkan tombol navigasi kembali secara kondisional.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    title: String, // Judul yang akan ditampilkan di app bar.
    canNavigateBack: Boolean, // Menentukan apakah tombol navigasi kembali ditampilkan.
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null, // Menangani perilaku gulir pada app bar.
    navigateUp: () -> Unit = {} // Fungsi untuk menangani navigasi kembali.
) {
    CenterAlignedTopAppBar(
        title = { Text(title) }, // Menampilkan judul di tengah app bar.
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) { // Menampilkan ikon tombol kembali jika canNavigateBack = true.
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button) // Deskripsi konten untuk aksesibilitas.
                    )
                }
            }
        }
    )
}