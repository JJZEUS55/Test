package com.example.test.ui.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.database.PersonObject
import com.example.test.databinding.ItemDatabaseBinding

class DatabaseAdapter: ListAdapter<PersonObject, DatabaseAdapter.ViewHolder>(DatabaseCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    class ViewHolder(private val itemBinding: ItemDatabaseBinding): RecyclerView.ViewHolder(itemBinding.root) {

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val view = ItemDatabaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: PersonObject?, position: Int) {
            itemBinding.person = item
            if(position%2 != 0){
                itemBinding.txtName.background = ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bottom_right_border)
                itemBinding.txtBirthDate.background = ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bottom_right_border)
                itemBinding.txtPosition.background = ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bottom_right_border)
            } else {
                itemBinding.txtName.background = ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bottom_right_border_orange)
                itemBinding.txtBirthDate.background = ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bottom_right_border_orange)
                itemBinding.txtPosition.background = ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bottom_right_border_orange)
            }

            itemBinding.executePendingBindings()
        }
    }

}

class DatabaseCallback:DiffUtil.ItemCallback<PersonObject>(){

    override fun areItemsTheSame(oldItem: PersonObject, newItem: PersonObject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonObject, newItem: PersonObject): Boolean {
        return oldItem == newItem
    }
}