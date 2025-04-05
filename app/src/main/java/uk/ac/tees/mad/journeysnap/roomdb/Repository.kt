package uk.ac.tees.mad.journeysnap.roomdb

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.journeysnap.model.JournalEntity

interface Repository {

    suspend fun addJournal(entity: JournalEntity)
    fun getJournal(id:String): Flow<JournalEntity>
    fun getAllJournal():Flow<List<JournalEntity>>
    suspend fun deleteJournal(entity: JournalEntity)
    suspend fun deleteAllJournal()
}