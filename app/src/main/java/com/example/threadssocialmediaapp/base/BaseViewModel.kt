package com.example.threadssocialmediaapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel<T> : ViewModel() {
    protected val _events = MutableLiveData<T>()
    val events: LiveData<T> = _events


}