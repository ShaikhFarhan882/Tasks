package com.example.tasks.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_db")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var title : String,
    var priority : Int,
    var timeStamp : Long
):Parcelable
