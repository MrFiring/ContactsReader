package ru.mrfiring.focusapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import ru.mrfiring.focusapp.R
import ru.mrfiring.focusapp.databinding.FragmentHomeBinding
import ru.mrfiring.focusapp.di.viewmodel.ViewModelFactory
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.presentation.detail.DetailFragment
import ru.mrfiring.focusapp.presentation.home.adapter.ContactsRecyclerViewAdapter
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel

    private lateinit var adapter: ContactsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        viewModel.initialLoading()

        initAdapter()

        binding.homeToolbar.setOnMenuItemClickListener {
            respondToMenuItemClick(it)
        }

        viewModel.homeState.observe(viewLifecycleOwner) { newState ->
            renderState(newState)
        }

        viewModel.prefState.observe(viewLifecycleOwner) { newPrefs ->
            viewModel.respondToPrefsState(newPrefs)
        }

        viewModel.navigateToDetail.observe(viewLifecycleOwner) { contact ->
            navigateToDetailFragment(contactId = contact.id)
        }

        viewModel.showStorageChangedToast.observe(viewLifecycleOwner) {
            showStorageToast(it)
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.savePrefs()
    }

    private fun respondToMenuItemClick(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.homeMenuStorageSwitch -> {
            viewModel.storageTypeChanged()
            true
        }

        else -> false
    }


    private fun initAdapter() {
        adapter = ContactsRecyclerViewAdapter {
            viewModel.onNavigateToDetail(it)
        }
        binding.homeContactsList.adapter = adapter
    }

    private fun renderState(homeState: HomeState) = when (homeState) {
        is HomeState.Loading -> {
            with(binding) {
                homeProgressBar.visibility = View.VISIBLE
                homeContactsList.visibility = View.GONE
            }

        }

        is HomeState.Success -> {
            with(binding) {
                homeContactsList.visibility = View.VISIBLE
                homeProgressBar.visibility = View.GONE
            }
            adapter.submitList(homeState.contacts)
        }
    }

    private fun showStorageToast(useStorage: UseStorage) {
        val message = when (useStorage) {
            UseStorage.DATABASE -> {
                getString(R.string.msg_selected_room)
            }
            UseStorage.FILE -> {
                getString(R.string.msg_selected_file)
            }
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDetailFragment(contactId: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, DetailFragment.newInstance(contactId))
            .addToBackStack(null)
            .commit()
    }

}