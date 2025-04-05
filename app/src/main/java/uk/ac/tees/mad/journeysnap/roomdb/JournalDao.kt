package uk.ac.tees.mad.journeysnap.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.journeysnap.model.JournalEntity

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJournal(entity: JournalEntity)

    @Query("SELECT * FROM journal_table WHERE id = :id LIMIT 1")
    fun getJournal(id:String):Flow<JournalEntity>

    @Query("SELECT * FROM journal_table")
    fun getAllJournal():Flow<List<JournalEntity>>

    @Delete
    suspend fun deleteJournal(entity: JournalEntity)

    @Query("DELETE FROM journal_table")
    suspend fun deleteAllJournal()
}