package uk.ac.tees.mad.journeysnap.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.journeysnap.roomdb.JournalDao
import uk.ac.tees.mad.journeysnap.roomdb.JournalDatabase
import uk.ac.tees.mad.journeysnap.roomdb.Repository
import uk.ac.tees.mad.journeysnap.roomdb.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):JournalDatabase{
        return JournalDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideJournalDao(db:JournalDatabase):JournalDao{
        return db.journalDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao:JournalDao):Repository{
        return RepositoryImpl(dao)
    }
}