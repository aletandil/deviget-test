package com.projecttesting.data.repositories

import androidx.lifecycle.LiveData
import com.projecttesting.data.daos.EntriesDao
import com.projecttesting.data.models.Entry
import com.projecttesting.data.models.TopEntriesResponse
import com.projecttesting.data.services.EntriesService
import com.projecttesting.data.services.EntriesService.Companion.AFTER_FILTER
import com.projecttesting.data.sources.EntriesSource
import com.projecttesting.network.NetworkHandler
import com.projecttesting.network.RequestCallback
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * singleton is maintained by Dagger
 *
 * @Inject tells Dagger that this class can be injected
 * Dagger automatically calls this constructor, if an instance of this class is requested.
 *
 * @param entriesService  remote data source; generated by retrofit
 *
 */
@Singleton
class EntryRepository @Inject constructor(
    private val entriesService: EntriesService,
    private val entriesDao: EntriesDao
) : EntriesSource {

    private var afterIndex: String? = null

    override suspend fun getEntryByID(entryID: String): Entry? {

        return loadEntryByIDFromLocalDataSource(entryID)
    }

    override suspend fun getTopEntries(newEntries: Boolean): LiveData<List<Entry>>? {

        loadTopEntriesFromRemoteDataSource(newEntries)
        return loadTopEntriesFromLocalDataSource()
    }

    private fun loadTopEntriesFromLocalDataSource(): LiveData<List<Entry>> {
        return entriesDao.getTopEntries()
    }

    override suspend fun updateEntry(entry: Entry) {

        entriesDao.updateEntries(entry)

    }

    private fun loadEntryByIDFromLocalDataSource(entryID: String): Entry? {
        return entriesDao.getEntryById(entryID)
    }

    private suspend fun loadTopEntriesFromRemoteDataSource(newEntries: Boolean): TopEntriesResponse? {

        var topEntriesResponse: TopEntriesResponse? = null

        val apiFilters = HashMap<String, String>()
        if (afterIndex != null && !newEntries) {
            apiFilters[AFTER_FILTER] = afterIndex!!
        }

        NetworkHandler.request(entriesService.getTopEntries(apiFilters), object : RequestCallback<TopEntriesResponse> {

            override fun onError(message: String?) {
                Timber.e(message)
            }

            override fun onSuccess(data: TopEntriesResponse?) {
                data?.let {
                    topEntriesResponse = data
                    afterIndex = data.data?.after
                    data.data?.children?.forEach({
                        entriesDao.insertEntries(it.data!!)
                    })
                }
            }

            override fun onLoading() {
                // TODO: handle loading state
            }
        })

        return topEntriesResponse

    }

    override fun getLocalTopEntries(): LiveData<List<Entry>> {
        return entriesDao.getTopEntries()
    }
}