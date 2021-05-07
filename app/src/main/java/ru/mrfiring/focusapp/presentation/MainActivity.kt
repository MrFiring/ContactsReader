package ru.mrfiring.focusapp.presentation

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.mrfiring.focusapp.R
import ru.mrfiring.focusapp.presentation.home.HomeFragment


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, HomeFragment.newInstance())
            .commit()
    }
}