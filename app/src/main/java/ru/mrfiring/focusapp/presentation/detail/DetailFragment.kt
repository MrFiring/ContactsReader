package ru.mrfiring.focusapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import ru.mrfiring.focusapp.databinding.FragmentDetailBinding
import ru.mrfiring.focusapp.di.viewmodel.ViewModelFactory
import ru.mrfiring.focusapp.domain.model.DomainContact
import javax.inject.Inject


class DetailFragment : DaggerFragment() {

    companion object {
        private const val CONTACT_ID_ARG = "contactId"

        fun newInstance(contactId: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(CONTACT_ID_ARG, contactId)
            }
        }
    }

    private var contactId: Int = -1

    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactId = it.getInt(CONTACT_ID_ARG)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        viewModel.initialLoading()

        viewModel.useStorage.observe(viewLifecycleOwner) { storage ->
            viewModel.loadContact(contactId, storage)
        }

        viewModel.detailState.observe(viewLifecycleOwner) { newState ->
            renderState(newState)
        }

        binding.detailSaveButton.setOnClickListener {
            updateContact()
        }

        return binding.root
    }

    private fun renderState(state: DetailState) {
        when (state) {
            is DetailState.Loading -> {
                with(binding) {
                    detailProgressBar.visibility = View.VISIBLE
                    detailContactNameInputLayout.visibility = View.GONE
                    detailContactNumberInputLayout.visibility = View.GONE
                    detailSaveButton.visibility = View.GONE
                }

            }
            is DetailState.Success -> {
                with(binding) {
                    detailContactNameEdit.setText(state.contact.name)
                    detailContactNumberEdit.setText(state.contact.number)

                    detailContactNumberInputLayout.visibility = View.VISIBLE
                    detailContactNameInputLayout.visibility = View.VISIBLE
                    detailSaveButton.visibility = View.VISIBLE
                    detailProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun updateContact() {
        val newName = binding.detailContactNameEdit.text.toString()
        val newNumber = binding.detailContactNumberEdit.text.toString()

        viewModel.updateContact(
            DomainContact(
                id = contactId,
                name = newName,
                number = newNumber
            )
        )
    }

}