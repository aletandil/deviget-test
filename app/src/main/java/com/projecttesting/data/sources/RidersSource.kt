package com.projecttesting.data.sources

import com.projecttesting.data.models.Rider


interface RidersSource {

    suspend fun getRider(): Rider?
}