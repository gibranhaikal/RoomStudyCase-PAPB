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

package com.example.inventory.ui.navigation

/**
 * Interface yang mendefinisikan destinasi navigasi untuk aplikasi.
 * Setiap destinasi memiliki rute unik dan ID sumber daya string untuk judul layar.
 */
interface NavigationDestination {

    /**
     * Nama unik untuk menentukan jalur (path) ke suatu composable.
     * Setiap layar atau composable dalam aplikasi memiliki rute yang unik untuk mempermudah navigasi.
     */
    val route: String

    /**
     * ID sumber daya string yang berisi judul yang akan ditampilkan pada layar.
     * Menggunakan ID ini memungkinkan judul ditampilkan berdasarkan sumber daya string (string resource)
     * yang mendukung internationalization (i18n) atau berbagai bahasa.
     */
    val titleRes: Int
}