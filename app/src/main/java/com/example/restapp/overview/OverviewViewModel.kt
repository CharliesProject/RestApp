package com.example.restapp.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapp.network.MyApi
import com.example.restapp.network.SinglePhoto
import kotlinx.coroutines.launch

enum class MyApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MyApiStatus>()
    val status: LiveData<MyApiStatus> = _status

    private val _jsonObj = MutableLiveData<SinglePhoto>()
    val jsonObj: LiveData<SinglePhoto> = _jsonObj

    private val _photoMeme = MutableLiveData<String>()
    val photoMeme: LiveData<String> = _photoMeme

    init {
        getMyPhotos()
    }

    fun getMyPhotos() {
        viewModelScope.launch {
            _status.value = MyApiStatus.LOADING
            try {
                _jsonObj.value = MyApi.retrofitService.getPhotos()
                _photoMeme.value = _jsonObj.value?.imgSrcUrl
                _status.value = MyApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MyApiStatus.ERROR
            }
        }
    }
}