package com.example.test.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.test.R
import com.example.test.ui.button.ButtonActivity
import com.example.test.ui.database.DatabaseActivity
import com.example.test.ui.map.MapsActivity
import com.example.test.ui.service.ServiceActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest.permission
import android.content.Context


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        verifyStoragePermissions()


        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_map -> {
                val intentMap = Intent(this, MapsActivity::class.java)
                startActivity(intentMap)
                return true
            }
            R.id.nav_button -> {
                val intentButton = Intent(this, ButtonActivity::class.java)
                startActivity(intentButton)
                return true
            }
            R.id.nav_database -> {
                val intentDatabase = Intent(this, DatabaseActivity::class.java)
                startActivity(intentDatabase)
                return true
            }
            R.id.nav_rest-> {
                val intentService = Intent(this, ServiceActivity::class.java)
                startActivity(intentService)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun verifyStoragePermissions() {
        val listPermission:MutableList<String> = arrayListOf(
            permission.ACCESS_WIFI_STATE,
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.ACCESS_FINE_LOCATION,
            permission.INTERNET
        )

        if(!hasPermissions(applicationContext, listPermission)){
            ActivityCompat.requestPermissions(this, listPermission.toTypedArray(), 1)
        }

    }

    private fun hasPermissions(context: Context, permissions: MutableList<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

}
