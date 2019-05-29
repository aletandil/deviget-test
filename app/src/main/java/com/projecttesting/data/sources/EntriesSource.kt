package com.projecttesting.data.sources

import com.projecttesting.data.models.Entry
import com.projecttesting.data.models.TopEntriesResponse


interface EntriesSource {

    suspend fun getTopEntries(): TopEntriesResponse?

    suspend fun getEntryByID(entryID: String): Entry?

    suspend fun updateEntry(entry: Entry)

}