package com.example.tasks.utils

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DateFormat

@BindingAdapter("setPriority")
fun setPriority(textView : TextView, priority: Int) {
    when (priority) {

        0 -> {
            textView.text = "High Priority"
            textView.setTextColor(Color.RED)
        }
        1 -> {
            textView.text = "Medium Priority"
            textView.setTextColor(Color.YELLOW)

        }
        else -> {
            textView.text = "Low Priority"
            textView.setTextColor(Color.GREEN)
        }
    }
}


@BindingAdapter("setTimestamp")
fun setTimeStamp(textView : TextView, timestamp: Long) {
    textView.text = DateFormat.getInstance().format(timestamp)
}