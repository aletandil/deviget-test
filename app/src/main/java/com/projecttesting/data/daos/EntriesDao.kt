package com.projecttesting.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.projecttesting.data.models.Entry

@Dao
interface EntriesDao {

    /**
     * Select all top entries from the entries table.
     *
     * @return all entries.
     */
    @Query("SELECT * FROM Entries WHERE visible = 1 ORDER BY created DESC")
    fun getTopEntries(): LiveData<List<Entry>>

    /**
     * Select a topEntries by id.
     *
     * @param entryID the topEntries id.
     * @return the topEntries with entryID.
     */
    @Query("SELECT * FROM Entries WHERE id = :entryID")
    fun getEntryById(entryID: String): Entry?

    /**
     * Insert a topEntries in the database. If the topEntries already exists, ignore insert
     *
     * @param entries the topEntries to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEntries(entries: Entry)

    /**
     * Update a topEntries.
     *
     * @param topEntries to be updated
     * @return the number of entries updated. This should always be 1.
     */
    @Update
    fun updateEntries(entry: Entry): Int

    /**
     * Delete a topEntries by id.
     *
     * @return the number of entries deleted. This should always be 1.
     */
    @Query("DELETE FROM Entries WHERE id = :entryID")
    fun deleteEntryById(entryID: String): Int

    /**
     * Delete all entries.
     */
    @Query("DELETE FROM Entries")
    fun deleteTopEntries()

}