package com.example.threadssocialmediaapp.utils

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.example.threadssocialmediaapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateString: String): String {
    // Parse the date string to an Instant
    val instant = Instant.parse(dateString)

    // Convert the Instant to LocalDateTime in UTC
    val dateTime = instant.toLocalDateTime(TimeZone.UTC)

    // Get the current time in UTC
    val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    // Calculate the duration between the two dates
    val duration = now.toInstant(TimeZone.UTC) - dateTime.toInstant(TimeZone.UTC)

    // Format the time components
    val days = duration.inWholeDays
    val hours = duration.inWholeHours % 24
    val minutes = duration.inWholeMinutes % 60
    val seconds = duration.inWholeSeconds % 60

    // Define the date formatter
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Convert LocalDateTime to ZonedDateTime for formatting
    val zonedDateTime = ZonedDateTime.ofInstant(instant.toJavaInstant(), ZoneId.of("UTC"))

    // Determine the largest non-zero time component and return it
    return if (days > 5) {
        // Format the date if more than 5 days have passed
        zonedDateTime.format(dateFormatter)
    } else {
        // Otherwise, return the largest non-zero time component
        when {
            days > 0 -> "${days}d"
            hours > 0 -> "${hours}h"
            minutes > 0 -> "${minutes}m"
            seconds > 0 -> "${seconds}s"
            else -> "0s" // In case all components are zero
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Instant.toJavaInstant(): java.time.Instant = java.time.Instant.ofEpochSecond(epochSeconds, nanosecondsOfSecond.toLong())

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

fun highlightMentions(textView: TextView, text: String) {
    val spannableString = SpannableString(text)
    val mentionColor = ContextCompat.getColor(textView.context, R.color.tagColor)
    val mentionPattern = Regex("@\\w+")

    mentionPattern.findAll(text).forEach { matchResult ->
        val start = matchResult.range.first
        val end = matchResult.range.last + 1
        spannableString.setSpan(
            ForegroundColorSpan(mentionColor),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    textView.text = spannableString
}