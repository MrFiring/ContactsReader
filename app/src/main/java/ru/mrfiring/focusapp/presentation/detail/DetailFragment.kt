package ru.mrfiring.focusapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import ru.mrfiring.focusapp.databinding.FragmentDetailBinding
import ru.mrfiring.focusapp.di.viewmodel.ViewModelFactory
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

    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]



        return binding.root
    }

}