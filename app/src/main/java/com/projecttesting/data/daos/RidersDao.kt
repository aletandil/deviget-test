package com.projecttesting.data.daos

import androidx.room.*
import com.projecttesting.data.models.Rider

@Dao
interface RidersDao {

    /**
     * Select all riders from the riders table.
     *
     * @return all riders.
     */
    @Query("SELECT * FROM Riders")
    fun getRiders(): List<Rider>

    /**
     * Select a rider by id.
     *
     * @param riderId the rider id.
     * @return the rider with riderId.
     */
    @Query("SELECT * FROM Riders WHERE id = :riderId")
    fun getRiderById(riderId: Int): Rider?

    /**
     * Insert a rider in the database. If the rider already exists, ignore insert
     *
     * @param rider the rider to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRider(rider: Rider)

    /**
     * Update a rider.
     *
     * @param rider rider to be updated
     * @return the number of riders updated. This should always be 1.
     */
    @Update
    fun updateRider(rider: Rider): Int

    /**
     * Delete a rider by id.
     *
     * @return the number of riders deleted. This should always be 1.
     */
    @Query("DELETE FROM Riders WHERE id = :riderId")
    fun deleteRiderById(riderId: String): Int

    /**
     * Delete all riders.
     */
    @Query("DELETE FROM Riders")
    fun deleteRiders()

}