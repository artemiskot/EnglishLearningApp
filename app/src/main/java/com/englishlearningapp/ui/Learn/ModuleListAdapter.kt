package com.englishlearningapp.ui.Learn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.englishlearningapp.R
import com.englishlearningapp.data.Module

class ModuleListAdapter(private val onModuleClick: (Int) -> Unit) :
    ListAdapter<Module, ModuleListAdapter.ModuleViewHolder>(ModuleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_module, parent, false)
        return ModuleViewHolder(view, onModuleClick)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = getItem(position)
        holder.bind(module)
    }

    inner class ModuleViewHolder(
        itemView: View,
        private val onModuleClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val moduleNameTextView: TextView = itemView.findViewById(R.id.moduleNameTextView)

        fun bind(module: Module) {
            moduleNameTextView.text = module.name
            itemView.setOnClickListener {
                onModuleClick(module.id)
            }
        }
    }
}

class ModuleDiffCallback : DiffUtil.ItemCallback<Module>() {
    override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean {
        return oldItem == newItem
    }
}
