package uk.ac.tees.mad.journeysnap.model

data class Journal(
    val name:String = "",
    val location:String = "",
    val story:String = "",
    val startDate:Long = 0,
    val endDate:Long = 0,
    val photo:String = ""
)
