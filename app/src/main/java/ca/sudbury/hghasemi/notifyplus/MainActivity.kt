package ca.sudbury.hghasemi.notifyplus

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.navigation.NavigationView

/*
 First created by Hojat Ghasemi on Saturday , 11 March 2017.
 Contact the author at "https://github.com/hojat72elect"
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    ColorDialog.ColorDialogListener {

    // The LinearLayout that contains the buttons
    // (the background color will be applied to this layout)
    private var buttonsHolder: LinearLayout? = null

    // All the buttons
    private var ab1: Button? = null
    private var ab2: Button? = null
    private var ab3: Button? = null
    private var ab4: Button? = null
    private var ab5: Button? = null
    private var ab6: Button? = null
    private var ab7: Button? = null
    private var ab8: Button? = null

    // All the SharedPrefs used by this app
    private var colorSharedPref: SharedPreferences? = null
    private var firstButtonSharedPref: SharedPreferences? = null
    private var secondButtonSharedPref: SharedPreferences? = null
    private var thirdButtonSharedPref: SharedPreferences? = null
    private var fourthButtonSharedPref: SharedPreferences? = null
    private var fifthButtonSharedPref: SharedPreferences? = null
    private var sixthButtonSharedPref: SharedPreferences? = null
    private var seventhButtonSharedPref: SharedPreferences? = null
    private var eighthButtonSharedPref: SharedPreferences? = null

    // All the keys for reading from and writing to SharedPrefs
    private val colorWriteKey = "color_shared_preferences_read/write_key"
    private val firstButtonWriteKey = "first_button_write_key"
    private val secondButtonWriteKey = "second_button_write_key"
    private val thirdButtonWriteKey = "third_button_write_key"
    private val fourthButtonWriteKey = "fourth_button_write_key"
    private val fifthButtonWriteKey = "fifth_button_write_key"
    private val sixthButtonWriteKey = "sixth_button_write_key"
    private val seventhButtonWriteKey = "seventh_button_write_key"
    private val eighthButtonWriteKey = "eighth_button_write_key"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up everything for NavigationView and NavigationDrawer
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Loading all the other UI elements
        buttonsHolder = findViewById(R.id.newbuttonsholder)
        ab1 = findViewById(R.id.button1)
        ab2 = findViewById(R.id.button2)
        ab3 = findViewById(R.id.button3)
        ab4 = findViewById(R.id.button4)
        ab5 = findViewById(R.id.button5)
        ab6 = findViewById(R.id.button6)
        ab7 = findViewById(R.id.button7)
        ab8 = findViewById(R.id.button8)

        // Loading all the needed SharedPreferences
        colorSharedPref = getSharedPreferences("rang_prefs", 0)
        firstButtonSharedPref = getSharedPreferences("first_button_prefs", 0)
        secondButtonSharedPref = getSharedPreferences("second_button_prefs", 0)
        thirdButtonSharedPref = getSharedPreferences("third_button_prefs", 0)
        fourthButtonSharedPref = getSharedPreferences("forth_button_prefs", 0)
        fifthButtonSharedPref = getSharedPreferences("fifth_button_prefs", 0)
        sixthButtonSharedPref = getSharedPreferences("sixth_button_prefs", 0)
        seventhButtonSharedPref = getSharedPreferences("seventh_button_prefs", 0)
        eighthButtonSharedPref = getSharedPreferences("eighth_button_prefs", 0)


        // when the app starts up, color should be applied to UI
        updateBackgroundColor(colorSharedPref)

        // Registering all the listeners
        findViewById<View>(R.id.bgcolorlayout).let {
            it.setOnClickListener {
                // show the dialog of "Background color".
                ColorDialog().show(supportFragmentManager, "color")
            }
        }

    }

    /**
     * When user presses the back button in the app.
     */
    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Inflate the menu items in the action bar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                suggestShare()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Handle navigation view item clicks here.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_contactus -> {
                ContactUsDialogFragment().show(supportFragmentManager, "contactus")
            }
            R.id.nav_help -> {
                HelpDialogFragment().show(supportFragmentManager, "help")
            }
            R.id.nav_share -> {
                suggestShare()
            }
        }
        findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
        return true
    }

    // Suggesting the user to share a link to this app.
    private fun suggestShare() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT, """
     ${getString(R.string.sharetext)}
     The app isn't currently published in any markets
     """.trimIndent()
        )
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    // The ColorDialog receives a reference to MainActivity through the
    // DialogFragment.onAttach() callback, which it uses to call the following methods
    // defined by the ColorDialog.ColorDialogListener interface
    override fun onColorChanged(dialog: DialogFragment, newColor: Int) {
        // Update the SharedPrefs with the new color
        colorSharedPref?.edit().let {
            it?.putInt(
                colorWriteKey, Color.argb(
                    Color.alpha(newColor),
                    Color.red(newColor),
                    Color.green(newColor),
                    Color.blue(newColor)
                )
            )
            it?.apply()
        }
        updateBackgroundColor(colorSharedPref)
    }

    private fun updateBackgroundColor(color: SharedPreferences?) {
        // Whenever you want to update the background color with
        // sharedPrefs, just call this function
        buttonsHolder?.setBackgroundColor(
            color?.getInt(colorWriteKey, 0) ?: Color.WHITE
        )
    }
}