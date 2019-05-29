package com.projecttesting.data.sources

import com.projecttesting.data.models.TopEntriesResponse


interface EntriesSource {

    suspend fun getTopEntries(): TopEntriesResponse?
}