package uk.ac.tees.mad.journeysnap.ui.screen.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import uk.ac.tees.mad.journeysnap.model.Journal
import uk.ac.tees.mad.journeysnap.model.JournalEntity
import uk.ac.tees.mad.journeysnap.roomdb.Repository
import uk.ac.tees.mad.journeysnap.utils.Constants.JOURNALS
import uk.ac.tees.mad.journeysnap.utils.Constants.USERS
import uk.ac.tees.mad.journeysnap.utils.Utils.getImageUri
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val userId = auth.currentUser?.uid?:""

    private val _journalList = MutableStateFlow(emptyList<JournalEntity>())
    val journalList:StateFlow<List<JournalEntity>> get() = _journalList
    private val _searchQuery =  MutableStateFlow("")
    val searchQuery:StateFlow<String> get() = _searchQuery

    init {
        viewModelScope.launch {
            db.collection(USERS)
                .document(userId)
                .collection(JOURNALS)
                .get()
                .addOnSuccessListener {documents->
                    for (doc in documents.documents){
                        val mJournal = doc.toObject(Journal::class.java)
                        if (mJournal!=null) {
                            val entity = JournalEntity(
                                id = doc.id,
                                name = mJournal.name,
                                story = mJournal.story,
                                location = mJournal.location,
                                startDate = mJournal.startDate,
                                endDate = mJournal.endDate,
                                image = mJournal.photo
                            )
                            viewModelScope.launch {
                                repository.addJournal(entity)
                            }
                        }
                    }
                }
        }
        viewModelScope.launch {
            combine(
                repository.getAllJournal(),
                _searchQuery
            ) { journals, query ->
                journals.filter { journal ->
                    journal.name.contains(query, ignoreCase = true) ||
                            journal.location.contains(query, ignoreCase = true)
                }.sortedByDescending { it.startDate }
            }.collect {
                _journalList.value = it
            }
        }
    }

    fun deleteJournal(entity: JournalEntity, context: Context){
        db.collection(USERS)
            .document(userId)
            .collection(JOURNALS)
            .document(entity.id)
            .delete()
            .addOnSuccessListener {
                viewModelScope.launch {
                    repository.deleteJournal(entity)
                }
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
    }

    fun onQueryChange(query:String){
        _searchQuery.value = query
    }

    fun shareImage(context: Context, image: String) {
        val uri = getImageUri(context, image)
        if (uri != null) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share Image"))
        } else {
            Toast.makeText(context, "Failed to share image", Toast.LENGTH_SHORT).show()
        }
    }
}