package uk.ac.tees.mad.journeysnap.ui.screen.journal

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.journeysnap.model.Journal
import uk.ac.tees.mad.journeysnap.model.JournalEntity
import uk.ac.tees.mad.journeysnap.roomdb.Repository
import uk.ac.tees.mad.journeysnap.utils.Constants.JOURNALS
import uk.ac.tees.mad.journeysnap.utils.Constants.USERS
import uk.ac.tees.mad.journeysnap.utils.Utils
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val userId = auth.currentUser?.uid ?: ""

    private val _imageList = MutableStateFlow(emptyList<Bitmap>())
    val imageList: StateFlow<List<Bitmap>> get() = _imageList

    fun addImage(bitmap: Bitmap) {
        _imageList.value = _imageList.value.toMutableList().apply { add(bitmap) }
    }

    fun addImage(bitMaps: List<Bitmap>) {
        _imageList.value = _imageList.value.toMutableList().apply { addAll(bitMaps) }
    }

    fun addJournal(
        name: String, location: String,
        story: String, endDate: Long,
        startDate: Long, context: Context,
        onComplete: () -> Unit
    ) {
        if (name.isEmpty() || location.isEmpty() || _imageList.value.isEmpty()) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val journalDB = db.collection(USERS)
            .document(userId)
            .collection(JOURNALS)

        _imageList.value.forEach { bitmap ->
            val imageString = Utils.bitmapToBase64(bitmap)
            if (imageString==null){
                Log.e("ImageString", "Null Image")
            }
            imageString?.let { image ->
                val journal = Journal(name, location, story, startDate, endDate, image)
                journalDB.add(journal).addOnSuccessListener { task->
                    viewModelScope.launch {
                        val entity = JournalEntity(
                            id = task.id,
                            name = name,
                            story = story,
                            location = location,
                            startDate = startDate,
                            endDate = endDate,
                            image = image
                        )
                        repository.addJournal(entity)
                    }
                }
                    .addOnFailureListener {task->
                        Log.e("TaskCompletion", task.message.toString())
                    }
            }
        }
        onComplete()
    }
}