package uk.ac.tees.mad.journeysnap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_table")
data class JournalEntity(
    @PrimaryKey val id:String,
    val name:String,
    val story:String,
    val location:String,
    val startDate:Long,
    val endDate:Long,
    val image:String
)
