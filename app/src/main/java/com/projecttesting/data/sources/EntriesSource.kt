package com.projecttesting.data.sources

import androidx.lifecycle.LiveData
import com.projecttesting.data.models.Entry


interface EntriesSource {

    suspend fun getTopEntries(newEntries: Boolean): LiveData<List<Entry>>?

    suspend fun getEntryByID(entryID: String): Entry?

    suspend fun updateEntry(entry: Entry)

    fun getLocalTopEntries(): LiveData<List<Entry>>?
}