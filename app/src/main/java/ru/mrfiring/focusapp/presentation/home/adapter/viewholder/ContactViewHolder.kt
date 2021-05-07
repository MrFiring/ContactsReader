package ru.mrfiring.focusapp.presentation.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mrfiring.focusapp.databinding.ItemListContactBinding
import ru.mrfiring.focusapp.domain.model.DomainContact

class ContactViewHolder(
    private val binding: ItemListContactBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: DomainContact, onClick: (DomainContact) -> Unit) {
        with(binding) {
            itemContactName.text = contact.name
            itemContactNumber.text = contact.number

            root.setOnClickListener { onClick(contact) }
        }
    }

    companion object {
        fun from(container: ViewGroup): ContactViewHolder {
            val layoutInflater = LayoutInflater.from(container.context)
            val binding = ItemListContactBinding.inflate(layoutInflater, container, false)

            return ContactViewHolder(binding)
        }
    }

}