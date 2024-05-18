package com.example.threadssocialmediaapp.utils

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.example.threadssocialmediaapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


inline fun <reified T> handleResponse(response: Response<T>): Flow<T?> = flow {
    if (response.isSuccessful) {
        val responseBody = response.body()
        responseBody?.let {
            Log.d("dataJSON", it.toString())
            emit(it)
        } ?: run {
            Log.d("response", "Response body is null")
            emit(null)
        }
    } else {
        Log.d("response", response.code().toString())
        emit(null)
    }
}

fun formatDateTime(dateString: String): String {
    // Parse the date string to an Instant
    val instant = Instant.parse(dateString)

    // Convert the Instant to LocalDateTime in UTC
    val dateTime = instant.toLocalDateTime(TimeZone.UTC)

    // Format the time components
    val hours = dateTime.hour
    val minutes = dateTime.minute
    val seconds = dateTime.second

    // Determine the largest non-zero time component and return it
    return when {
        hours > 0 -> "${hours}h"
        minutes > 0 -> "${minutes}m"
        seconds > 0 -> "${seconds}s"
        else -> "0s" // In case all components are zero
    }
}

fun calculateTotalPages(limit: Int, total: Int): Int {
    return if (total % limit == 0) {
        Log.d("totalPages", (total / limit).toString())
        total / limit
    } else {
        Log.d("totalPages", ((total / limit) + 1).toString())
        (total / limit) + 1
    }
}

fun showToast(
    activity: FragmentActivity,
    inflater: LayoutInflater,
    message: String,
    bg: Int
) {
    val layout: View = inflater.inflate(
        bg,
        activity.findViewById(R.id.toast_root)
    )
    val text: TextView = layout.findViewById(R.id.toast_text)
    text.text = message

    val toast = Toast(activity)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.show()
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = Date()
    return dateFormat.format(currentDate)
}