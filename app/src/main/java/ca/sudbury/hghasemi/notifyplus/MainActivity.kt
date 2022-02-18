package ca.sudbury.hghasemi.notifyplus

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
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
    ColorDialog.ColorDialogListener,
    AppsDialog.AppsDialogListener {

    private val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084

    // The LinearLayout that contains the buttons
    // (the background color will be applied to this layout)
    private var buttonsHolder: LinearLayout? = null

    // Position of the button clicked by user
    private var buttonPosition: Int? = null


    // All the buttons
    private var ab1: Button? = null
    private var ab2: Button? = null
    private var ab3: Button? = null
    private var ab4: Button? = null
    private var ab5: Button? = null
    private var ab6: Button? = null
    private var ab7: Button? = null
    private var ab8: Button? = null
    private var notificationToggle: SwitchCompat? = null
    private var floatingControlCenterToggle: SwitchCompat? = null

    // All the SharedPrefs used by this app
    private var colorSharedPref: SharedPreferences? = null
    private var firstButtonSharedPref: SharedPreferences? = null // package name of 1st button.
    private var secondButtonSharedPref: SharedPreferences? = null // package name of 2nd button.
    private var thirdButtonSharedPref: SharedPreferences? = null // package name of 3rd button.
    private var fourthButtonSharedPref: SharedPreferences? = null // package name of 4th button.
    private var fifthButtonSharedPref: SharedPreferences? = null // package name of 5th button.
    private var sixthButtonSharedPref: SharedPreferences? = null // package name of 6th button.
    private var seventhButtonSharedPref: SharedPreferences? = null // package name of 7th button.
    private var eighthButtonSharedPref: SharedPreferences? = null // package name of 8th button.
    private var notificationToggleSharedPref: SharedPreferences? =
        null // the state of the notification service.


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
    private val notificationToggleWriteKey = "notification_toggle_write_key"

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
        notificationToggle = findViewById(R.id.notify_switch)
        floatingControlCenterToggle = findViewById(R.id.floating_control_center_switch)

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
        notificationToggleSharedPref = getSharedPreferences("notification_toggle_prefs", 0)

        // when the app starts up, color should be applied to UI
        updateBackgroundColor(colorSharedPref)

        // Registering all the listeners
        findViewById<View>(R.id.bgcolorlayout).let {
            it.setOnClickListener {
                // show the dialog of "Background color".
                ColorDialog().show(supportFragmentManager, "color")
            }
        }
        findViewById<View>(R.id.notification_toggle_layout).let {
            it.setOnClickListener {
                // toggle the notification switch
                notificationToggle?.toggle()
            }
        }
        findViewById<View>(R.id.floating_control_center_layout).let {
            it.setOnClickListener {
                // toggle the floating control center switch
                floatingControlCenterToggle?.toggle()
            }
        }

        notificationToggle?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is turned on; show the notification.
                notificationToggleSharedPref?.edit().let {
                    it?.putBoolean(notificationToggleWriteKey, true)
                    it?.apply()
                }
                startNotification()
            } else {
                // stop showing the notification
                notificationToggleSharedPref?.edit().let {
                    it?.putBoolean(notificationToggleWriteKey, false)
                    it?.apply()
                }
                stopService(Intent(this, NotificationService::class.java))
            }
        }
        floatingControlCenterToggle?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // start the floating control center
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(
                        applicationContext
                    )
                ) {
                    // The API is more than 23 and the permission to draw over
                    // other apps is not yet granted. Ask them to grant the permission.
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
                    Toast.makeText(
                        applicationContext,
                        "You need to first accept the permission requests of this app.",
                        Toast.LENGTH_LONG
                    ).show()
                    floatingControlCenterToggle?.isChecked = false
                } else {
                    // User has already granted the permissions

                }

            } else {
                // stop showing the control center
            }
        }

        ab1 = findViewById<Button?>(R.id.button1).also {
            it.setOnClickListener {
                buttonPosition = 0
                showAppsDialog()
            }
        }
        ab2 = findViewById<Button?>(R.id.button2).also {
            it.setOnClickListener {
                buttonPosition = 1
                showAppsDialog()
            }
        }
        ab3 = findViewById<Button?>(R.id.button3).also {
            it.setOnClickListener {
                buttonPosition = 2
                showAppsDialog()
            }
        }
        ab4 = findViewById<Button?>(R.id.button4).also {
            it.setOnClickListener {
                buttonPosition = 3
                showAppsDialog()
            }
        }
        ab5 = findViewById<Button?>(R.id.button5).also {
            it.setOnClickListener {
                buttonPosition = 4
                showAppsDialog()
            }
        }
        ab6 = findViewById<Button?>(R.id.button6).also {
            it.setOnClickListener {
                buttonPosition = 5
                showAppsDialog()
            }
        }
        ab7 = findViewById<Button?>(R.id.button7).also {
            it.setOnClickListener {
                buttonPosition = 6
                showAppsDialog()
            }
        }
        ab8 = findViewById<Button?>(R.id.button8).also {
            it.setOnClickListener {
                buttonPosition = 7
                showAppsDialog()
            }
        }


        // Update the background drawable of all buttons in main UI according to SharedPrefs
        val packageManager = applicationContext.packageManager
        ab1?.background = firstButtonSharedPref?.getString(firstButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab2?.background = secondButtonSharedPref?.getString(secondButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab3?.background = thirdButtonSharedPref?.getString(thirdButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab4?.background = fourthButtonSharedPref?.getString(fourthButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab5?.background = fifthButtonSharedPref?.getString(fifthButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab6?.background = sixthButtonSharedPref?.getString(sixthButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab7?.background = seventhButtonSharedPref?.getString(seventhButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }
        ab8?.background = eighthButtonSharedPref?.getString(eighthButtonWriteKey, null).let {
            packageManager.getApplicationIcon(it ?: this.packageName)
        }

        // Update the state of notification switch according to SharedPref
        notificationToggleSharedPref?.getBoolean(notificationToggleWriteKey, false).let {
            if (it != null) {
                notificationToggle?.isChecked = it
            }
        }


    }

    // The ColorDialog receives a reference to MainActivity through the
    // DialogFragment.onAttach() callback, which it uses to call the following methods
    // defined by the ColorDialog.ColorDialogListener interface.
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
        // If the notification is on, the new color should be applied to it too
        // we just call the corresponding function after we have updated the SharedPref of color.
        if (notificationToggleSharedPref?.getBoolean(notificationToggleWriteKey, false) == true) {
            startNotification()
        }
    }

    // The AppsDialog receives a reference to MainActivity through the
    // DialogFragment.onAttach() callback, which it uses to call the following methods
    // defined by the AppsDialog.AppsDialogListener interface.
    override fun onAppChanged(imv: ImageView?, tv: TextView?) {
        // apply the icon to corresponding button
        // and update the related SharedPref
        when (buttonPosition) {
            0 -> {
                ab1?.background = imv?.drawable
                firstButtonSharedPref?.edit().let {
                    it?.putString(firstButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            1 -> {
                ab2?.background = imv?.drawable
                secondButtonSharedPref?.edit().let {
                    it?.putString(secondButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            2 -> {
                ab3?.background = imv?.drawable
                thirdButtonSharedPref?.edit().let {
                    it?.putString(thirdButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            3 -> {
                ab4?.background = imv?.drawable
                fourthButtonSharedPref?.edit().let {
                    it?.putString(fourthButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            4 -> {
                ab5?.background = imv?.drawable
                fifthButtonSharedPref?.edit().let {
                    it?.putString(fifthButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            5 -> {
                ab6?.background = imv?.drawable
                sixthButtonSharedPref?.edit().let {
                    it?.putString(sixthButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            6 -> {
                ab7?.background = imv?.drawable
                seventhButtonSharedPref?.edit().let {
                    it?.putString(seventhButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
            7 -> {
                ab8?.background = imv?.drawable
                eighthButtonSharedPref?.edit().let {
                    it?.putString(eighthButtonWriteKey, tv?.text.toString())
                    it?.apply()
                }
            }
        }
        // If the notification is on, the change in favorite apps should be applied to it too.
        // we just call the corresponding function after we have updated the SharedPref of favorite apps.
        if (notificationToggleSharedPref?.getBoolean(notificationToggleWriteKey, false) == true) {
            startNotification()
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

    // show the dialog of installed apps.
    private fun showAppsDialog() {
        AppsDialog().show(supportFragmentManager, "installed_apps")
    }

    // Suggesting the user to share a link to this app.
    private fun suggestShare() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT, """
     ${getString(R.string.share_text)}
     The app isn't currently published in any markets
     """.trimIndent()
        )
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    // Whenever you want to update the background color according to
    // sharedPrefs, just call this function
    private fun updateBackgroundColor(color: SharedPreferences?) {
        buttonsHolder?.setBackgroundColor(
            color?.getInt(colorWriteKey, 0) ?: Color.WHITE
        )
    }

    private fun startNotification() {
        // First make a list of all the current favorite apps
        val faveApps = arrayOf(
            firstButtonSharedPref?.getString(firstButtonWriteKey, null)
                ?: this.packageName,
            secondButtonSharedPref?.getString(secondButtonWriteKey, null)
                ?: this.packageName,
            thirdButtonSharedPref?.getString(thirdButtonWriteKey, null)
                ?: this.packageName,
            fourthButtonSharedPref?.getString(fourthButtonWriteKey, null)
                ?: this.packageName,
            fifthButtonSharedPref?.getString(fifthButtonWriteKey, null)
                ?: this.packageName,
            sixthButtonSharedPref?.getString(sixthButtonWriteKey, null)
                ?: this.packageName,
            seventhButtonSharedPref?.getString(seventhButtonWriteKey, null)
                ?: this.packageName,
            eighthButtonSharedPref?.getString(eighthButtonWriteKey, null)
                ?: this.packageName
        )
        try {
            // Creating the view hierarchy that will be shown in the notification
            val remoteView = RemoteViews(this.packageName, R.layout.notify8)
            remoteView.setInt(
                R.id.notificationlayout,
                "setBackgroundColor",
                colorSharedPref?.getInt(colorWriteKey, 0) ?: 0
            )

            // Starting up the notification
            val notificationIntent = Intent(this, NotificationService::class.java)
            notificationIntent.putExtra("ufa", faveApps)
            notificationIntent.putExtra("viewgroup", remoteView)
            startService(notificationIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "unable to start notification\n${e.message}", Toast.LENGTH_LONG)
                .show()
        }

    }

}