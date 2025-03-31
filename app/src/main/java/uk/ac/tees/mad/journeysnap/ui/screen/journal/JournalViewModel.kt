package uk.ac.tees.mad.journeysnap.ui.screen.journal

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor():ViewModel() {
    private val _imageList = MutableStateFlow(emptyList<Uri>())
    val imageList:StateFlow<List<Uri>> get() = _imageList

    fun addImage(uri:Uri){
        _imageList.value = _imageList.value.toMutableList().apply { add(uri) }
    }

    fun addImage(uris:List<Uri>){
        _imageList.value = _imageList.value.toMutableList().apply { addAll(uris) }
    }
}