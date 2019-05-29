package com.projecttesting.data.daos

import androidx.room.*
import com.projecttesting.data.models.Entry

@Dao
interface EntriesDao {

    /**
     * Select all top entries from the entries table.
     *
     * @return all riders.
     */
    @Query("SELECT * FROM Entry")
    fun getTopEntries(): List<Entry>

    /**
     * Select a topEntries by id.
     *
     * @param entryID the topEntries id.
     * @return the topEntries with entryID.
     */
    @Query("SELECT * FROM Entry WHERE id = :entryID")
    fun getEntryById(entryID: String): Entry?

    /**
     * Insert a topEntries in the database. If the topEntries already exists, ignore insert
     *
     * @param entry the topEntries to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertEntry(entry: Entry)

    /**
     * Update a topEntries.
     *
     * @param rider topEntries to be updated
     * @return the number of riders updated. This should always be 1.
     */
    @Update
    fun updateEntry(rider: Entry): Int

    /**
     * Delete a topEntries by id.
     *
     * @return the number of riders deleted. This should always be 1.
     */
    @Query("DELETE FROM Entry WHERE id = :entryID")
    fun deleteEntryById(entryID: String): Int

    /**
     * Delete all riders.
     */
    @Query("DELETE FROM Entry")
    fun deleteTopEntries()

}