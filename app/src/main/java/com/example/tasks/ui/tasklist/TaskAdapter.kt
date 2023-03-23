package com.example.tasks.ui.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.database.TaskEntity
import com.example.tasks.databinding.SingleRowBinding
import java.nio.file.Files.size

class TaskAdapter(private val clickListener: TaskClickListener) : ListAdapter<TaskEntity,TaskAdapter.ViewHolder>(TaskDiffCallback) {

    companion object TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>(){
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity) = oldItem == newItem

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(SingleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current,clickListener)
    }

    class ViewHolder(val binding: SingleRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(taskEntity: TaskEntity,clickListener: TaskClickListener){
            binding.taskEntity = taskEntity
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class TaskClickListener(val clickListener: (taskEntity : TaskEntity)-> Unit){
    fun onClick(taskEntity: TaskEntity) = clickListener(taskEntity)
}
