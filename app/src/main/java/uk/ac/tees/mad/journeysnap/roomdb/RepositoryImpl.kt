package uk.ac.tees.mad.journeysnap.roomdb

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.journeysnap.model.JournalEntity
import javax.inject.Inject

class RepositoryImpl@Inject constructor(private val dao: JournalDao):Repository {
    override suspend fun addJournal(entity: JournalEntity) {
        dao.addJournal(entity)
    }

    override fun getJournal(id: String): Flow<JournalEntity> {
        return dao.getJournal(id)
    }

    override fun getAllJournal(): Flow<List<JournalEntity>> {
        return dao.getAllJournal()
    }

    override suspend fun deleteJournal(entity: JournalEntity) {
        dao.deleteJournal(entity)
    }

    override suspend fun deleteAllJournal() {
        dao.deleteAllJournal()
    }
}