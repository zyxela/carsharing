package com.example.carsharing

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing.data.DatabaseHandler
import com.example.carsharing.repository.MeetRepository
import kotlinx.coroutines.launch

class MeetViewModel(private val repository: MeetRepository) : ViewModel() {

    val enter = MutableLiveData(false)

    fun registration(login: String, password: String, context: Context) {
        viewModelScope.launch {
            enter.value = repository.regist(login, password, context)
        }
    }

    fun auth(login: String, password: String, context: Context) {
        viewModelScope.launch {
            enter.value = repository.auth(login, password, context)
        }
    }

    fun uploadPhoto(uri: Uri, context: Context, name: String){
        viewModelScope.launch{
            DatabaseHandler().uploadToStorage(uri, context, name, "profile")
        }
    }
}