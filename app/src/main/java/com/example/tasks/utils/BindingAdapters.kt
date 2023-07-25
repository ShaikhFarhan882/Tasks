package com.example.tasks.utils

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tasks.R
import java.text.DateFormat

@BindingAdapter("setPriority")

fun setPriority(textView : TextView, priority: Int) {
    when (priority) {
        0 -> {
            textView.text = "High Priority"
            textView.setTextColor(Color.parseColor("#E12A2A"))
        }
        1 -> {
            textView.text = "Medium Priority"
            textView.setTextColor(Color.parseColor("#BA9637"))

        }
        else -> {
            textView.text = "Low Priority"
            textView.setTextColor(Color.parseColor("#088E53"))
        }
    }
}


@BindingAdapter("setTimestamp")
fun setTimeStamp(textView : TextView, timestamp: Long) {
    textView.text = DateFormat.getInstance().format(timestamp)
}