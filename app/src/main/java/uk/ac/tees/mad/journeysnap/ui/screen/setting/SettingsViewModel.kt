package uk.ac.tees.mad.journeysnap.ui.screen.setting

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.journeysnap.roomdb.Repository
import uk.ac.tees.mad.journeysnap.utils.Constants.USERS
import uk.ac.tees.mad.journeysnap.utils.Utils
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val userId = auth.currentUser?.uid ?: ""

    private val _name = MutableStateFlow("Name")
    val name: StateFlow<String> get() = _name
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email
    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> get() = _profileImage

    init {
        loadProfile()
    }

    private fun loadProfile() {
        db.collection(USERS)
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _name.value = document.getString("name") ?: "Name"
                    _email.value = document.getString("email") ?: ""
                    _profileImage.value = document.getString("image") ?: ""
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching profile: ${exception.message}")
            }
    }

    fun changeProfile(newName: String, image: Bitmap?) {
        val user = db.collection(USERS)
            .document(userId)
        if (newName.isNotEmpty()){
            user.update("name", newName)
                .addOnSuccessListener {
                    loadProfile()
                }
        }

        if (image!=null){
            val imageString = Utils.bitmapToBase64(image)
            user.update("image", imageString)
                .addOnSuccessListener {
                    loadProfile()
                }
        }
    }

    fun logOut(onComplete:()->Unit){
        auth.signOut()
        viewModelScope.launch {
            repository.deleteAllJournal()
            onComplete()
        }
    }
}