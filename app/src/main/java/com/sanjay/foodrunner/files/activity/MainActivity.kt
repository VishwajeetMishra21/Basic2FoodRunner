package com.sanjay.foodrunner.files.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sanjay.foodrunner.R
import com.sanjay.foodrunner.files.fragment.FAQsFragment
import com.sanjay.foodrunner.files.fragment.FavoriteRestaurantFragment
import com.sanjay.foodrunner.files.fragment.HomeFragment
import com.sanjay.foodrunner.files.fragment.ProfileFragment
import kotlinx.android.synthetic.main.drawer_layout.view.*

class MainActivity : AppCompatActivity() {

    lateinit var userPreference: SharedPreferences
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout

    lateinit var txtNameOfTheUser:TextView
    lateinit var txtEmailOfTheUser:TextView



    var previousMenuItem: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userPreference =
            getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE)

        val name = userPreference.getString("Name", "Name") as String
        val email = userPreference.getString("Email", "Email") as String

        val convertView=LayoutInflater.from(this@MainActivity).inflate(R.layout.drawer_layout,null)

        txtNameOfTheUser=convertView.findViewById(R.id.txtNameOfTheUser)
        txtEmailOfTheUser=convertView.findViewById(R.id.txtUserEmail)


        txtNameOfTheUser.text=name
        txtEmailOfTheUser.text=email


        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)
        navigationView.addHeaderView(convertView)

        setupToolbar(name)

        openHome()

        drawerLayout = findViewById(R.id.drawerLayout)
        frameLayout = findViewById(R.id.frameLayout)

        coordinatorLayout = findViewById(R.id.coordinatorLayout)


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        navigationView.setNavigationItemSelectedListener setNavigationItemSelectedListner@{

            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.menuHome -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.menuProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()

                }
                R.id.menuFavoriteRestaurants -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavoriteRestaurantFragment())
                        .commit()
                    supportActionBar?.title = "Favorite Restaurants"
                    drawerLayout.closeDrawers()
                }
                R.id.menuFaqs -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FAQsFragment())
                        .commit()
                    supportActionBar?.title = "FAQs"
                    drawerLayout.closeDrawers()
                }
                R.id.menuLogOut -> {
                    drawerLayout.closeDrawers()
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("Logout")
                    dialog.setMessage("Do you want to logout")
                    dialog.setPositiveButton("Logout") { text, listner ->
                        userPreference.edit().putBoolean("isLoggedIn", false).apply()
                        val intent = Intent(
                            this@MainActivity,
                            LoginActivity::class.java
                        )
                        startActivity(intent)
                        finish()

                    }
                    dialog.setNegativeButton("Cancel") { text, listner ->
                        openHome()

                    }
                    dialog.create()
                    dialog.show()

                }
            }
            return@setNavigationItemSelectedListner true
        }


    }

    fun setupToolbar(name: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Welcome $name"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)

        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
        supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.menuHome)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when (frag) {
            !is HomeFragment -> openHome()

            else -> super.onBackPressed()
        }
    }
}


