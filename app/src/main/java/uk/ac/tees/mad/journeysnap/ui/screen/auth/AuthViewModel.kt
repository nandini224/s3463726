package uk.ac.tees.mad.journeysnap.ui.screen.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.ac.tees.mad.journeysnap.utils.Constants
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

) :ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun loginUser(email:String, password:String, context: Context){
        if(email.isEmpty()){
            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isEmpty()){
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            _isLoading.value = false
            _isLoginSuccess.value = it.isSuccessful
            if (it.isSuccessful){
                Toast.makeText(context,"Login success", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun registerUser(name:String, email:String, password:String, context: Context){
        if(name.isEmpty()){
            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(email.isEmpty()){
            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isEmpty()){
            Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener{ task->
            val id = task.user?.uid?:""
            val user = hashMapOf(
                "id" to id,
                "name" to name,
                "email" to email,
                "image" to ""
            )
            db.collection(Constants.USERS)
                .document(id)
                .set(user, SetOptions.merge()).addOnSuccessListener {
                    _isLoading.value = false
                    _isLoginSuccess.value = true
                    Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    _isLoading.value = false
                    _isLoginSuccess.value = false
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
            .addOnFailureListener {
                _isLoading.value = false
                _isLoginSuccess.value = false
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}