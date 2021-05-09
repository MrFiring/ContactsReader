package ru.mrfiring.focusapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import dagger.android.support.DaggerAppCompatActivity
import ru.mrfiring.focusapp.R
import ru.mrfiring.focusapp.presentation.home.HomeFragment


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            checkPermissionsAndNavigate()
        }
    }

    private fun navigateToHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, HomeFragment.newInstance())
            .commit()
    }

    private fun checkPermissionsAndNavigate() {
        if (isReadContactsPermissionGranted()) {
            navigateToHomeFragment()
        } else {
            askReadContactsPermission()
        }
    }

    private fun isReadContactsPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    private fun askReadContactsPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            navigateToHomeFragment()
        } else {
            finish()
        }
    }

    private companion object {
        const val REQUEST_CODE = 145
    }
}