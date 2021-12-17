package com.adrc95.punkappsample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrc95.punkappsample.data.Beer

@Dao
interface PunkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(beer : Beer)

    @Query("Select * from Beers WHERE id = :id ORDER BY name ASC")
    fun getBeer(id: Long): Beer?
}