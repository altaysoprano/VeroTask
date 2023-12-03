package com.example.verotask.presentation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.verotask.data.models.Task
import com.example.verotask.databinding.TaskItemBinding

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.TaskViewHolder>() {

    private var tasks: MutableList<Task> = mutableListOf()

    fun updateList(newList: List<Task>) {
        tasks.clear()
        tasks.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textTask.text = task.task
            binding.textTitle.text = task.title
            binding.textDescription.text = task.description

            if (task.colorCode.isNotEmpty()) {
                val color = Color.parseColor(task.colorCode)
                val transparentColor = Color.argb(32, Color.red(color), Color.green(color), Color.blue(color))
                binding.cardView.setCardBackgroundColor(transparentColor)
            } else {
                binding.cardView.setCardBackgroundColor(Color.WHITE)
            }
        }
    }
}
