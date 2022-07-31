package com.berkayyetis.campbooktesting.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CampDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCamp(camp : Camp)

    @Delete
    suspend fun deleteCamp(camp : Camp)

    @Query("SELECT * FROM camps")
    fun observeCamps(): LiveData<List<Camp>>


}