package com.example.test.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.test.R
import com.example.test.ui.button.ButtonActivity
import com.example.test.ui.database.DatabaseActivity
import com.example.test.ui.map.MapsActivity
import com.example.test.ui.service.ServiceActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()




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

    override fun onDestroy() {

        super.onDestroy()
    }

}
