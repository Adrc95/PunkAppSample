package com.adrc95.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.adrc95.core.database.entity.BeerEntity
import com.adrc95.core.database.entity.BeerPageEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PunkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beer: BeerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(beers: List<BeerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPageEntries(entries: List<BeerPageEntryEntity>)

    @Query("SELECT * FROM Beers WHERE id = :id LIMIT 1")
    suspend fun getBeer(id: Long): BeerEntity?

    @Query(
        """
        SELECT b.* FROM Beers b
        INNER JOIN beer_page_entries p ON p.beer_id = b.id
        WHERE p.page = :page AND p.items_per_page = :itemsPerPage
        ORDER BY p.position ASC
        """
    )
    fun getBeers(page: Int, itemsPerPage: Int): Flow<List<BeerEntity>>

    @Query("DELETE FROM beer_page_entries WHERE page = :page AND items_per_page = :itemsPerPage")
    suspend fun deletePageEntries(page: Int, itemsPerPage: Int)

    @Transaction
    suspend fun replacePage(page: Int, itemsPerPage: Int, beers: List<BeerEntity>) {
        insertAll(beers)
        deletePageEntries(page, itemsPerPage)
        insertPageEntries(
            beers.mapIndexed { index, beer ->
                BeerPageEntryEntity(
                    page = page,
                    itemsPerPage = itemsPerPage,
                    beerId = beer.id,
                    position = index
                )
            }
        )
    }
}
