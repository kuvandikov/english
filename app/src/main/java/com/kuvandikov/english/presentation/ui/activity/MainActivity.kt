package com.kuvandikov.english.presentation.ui.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.BuildConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.kuvandikov.english.R
import com.kuvandikov.english.data.local.preferences.PreferencesHelper
import com.kuvandikov.english.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    @Inject
    lateinit var firebaseDatabaseReference: DatabaseReference

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFirebaseRealtimeDatabase()

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.mainFragment)
        /*when {
            UserData.isAuthorized -> {
                navGraph.setStartDestination(R.id.mainFragment)
            }
            !UserData.isAuthorized -> {
                navGraph.setStartDestination(R.id.signFlowFragment)
            }
        }*/
        navController.graph = navGraph
    }

    private fun setupFirebaseRealtimeDatabase(){
        lifecycleScope.launch(Dispatchers.IO){
            firebaseDatabaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val applicationId = dataSnapshot.child("application_id").getValue<String>()
                    val homeUnitId = dataSnapshot.child("home_unit_id").getValue<String>()
                    val savedUnitId = dataSnapshot.child("saved_unit_id").getValue<String>()
                    val settingsUnitId = dataSnapshot.child("settings_unit_id").getValue<String>()


                    applicationId?.let {
                        preferencesHelper.setAppId(it)
                        putMetaData(it)
                    }
                    homeUnitId?.let {
                        preferencesHelper.setHomeUnitId(it)
                    }
                    savedUnitId?.let {
                        preferencesHelper.setSavedUnitId(it)
                    }
                    settingsUnitId?.let {
                        preferencesHelper.setSettingsUnitId(it)
                    }
                    Log.d("TAG", "Value is: $applicationId $homeUnitId $savedUnitId $settingsUnitId")
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }
    }


    private fun putMetaData(appId: String){
        val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
        } else {
            @Suppress("DEPRECATION") packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA)
        }

        val bundle = applicationInfo.metaData
        bundle.putString("com.google.android.gms.ads.APPLICATION_ID", appId)
    }
}