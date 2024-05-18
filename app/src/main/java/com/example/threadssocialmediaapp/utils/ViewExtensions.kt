package com.example.threadssocialmediaapp.utils

import android.view.View
import android.widget.EditText

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
fun EditText.clear() { text.clear() }

//fun EditText.clear() { text.clear() }

