package com.adrc95.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrc95.core.database.entity.BeerEntity

@Dao
interface PunkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beer: BeerEntity)

    @Query("SELECT * FROM Beers WHERE id = :id LIMIT 1")
    suspend fun getBeer(id: Long): BeerEntity?
}
