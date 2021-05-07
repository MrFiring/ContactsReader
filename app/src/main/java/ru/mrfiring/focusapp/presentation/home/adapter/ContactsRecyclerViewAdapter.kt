package ru.mrfiring.focusapp.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.presentation.home.adapter.viewholder.ContactViewHolder

class ContactsRecyclerViewAdapter(
    private val onClick: (DomainContact) -> Unit
) : ListAdapter<DomainContact, ContactViewHolder>(ContactsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    private class ContactsDiffUtil : DiffUtil.ItemCallback<DomainContact>() {
        override fun areItemsTheSame(oldItem: DomainContact, newItem: DomainContact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainContact, newItem: DomainContact): Boolean {
            return oldItem == newItem
        }
    }
}