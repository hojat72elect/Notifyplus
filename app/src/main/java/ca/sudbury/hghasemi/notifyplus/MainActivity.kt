package ca.sudbury.hghasemi.notifyplus

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import ca.sudbury.hghasemi.notifyplus.AppsDialogFragment.DialogClicked
import com.google.android.material.navigation.NavigationView

/*
 First created by Hojat Ghasemi on Saturday , 11 March 2017.
 Contact the author at "https://github.com/hojat72elect"
 */
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    DialogClicked,
    ColorDialog.ColorDialogListener {

    private var buttonsHolder: LinearLayout? = null // The LinearLayout that contains the buttons
    private var mbc = 0
    private var rangshpref: SharedPreferences? = null // SharedPrefs for background color
    private val colorWriteKey = "rangsharedpref"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        buttonsHolder = findViewById(R.id.newbuttonsholder)

        // Loading all the needed SharedPreferences
        rangshpref = getSharedPreferences("rang_prefs", 0)

        // when the app starts up, color should be applied to UI
        updateBackgroundColor(rangshpref)

        findViewById<View>(R.id.bgcolorlayout).let {
            it.setOnClickListener {
                // show the dialog of "Background color".
                ColorDialog().show(supportFragmentManager, "color")
            }
        }


        // The spinner that shows the number of shortcuts
        val mySpinner = findViewById<Spinner>(R.id.shortcuts_count_picker)
        ArrayAdapter.createFromResource(
            this,
            R.array.number_of_shortcuts,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mySpinner.adapter = adapter
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

//    override fun noabmethod(dialognumber: Int, bc: Int) {
//        // mitoonam inja bar asase meghdare yek moteghayere integer , tasmim begiram ke
//        //kodam yek az 3 ta diloge mazkoor dar in fragment ra neshan bedaham.
//        when (dialognumber) {
//            0 -> callnumberofappbuttonsdialogfragment()
//            1 ->                 //  app haye nasb shode dar dastgah ra neshan midahad.
//                callAppsDialogFragment(bc)
//            2 ->                 // changes background color.
//                callbackgroundcolordialogfragment()
//            3 -> callhelpdialogfragment()
//            5 -> callrequestupdatefragment()
//            else -> {}
//        }
//    }

    // The color should be changed.
//    override fun rangdialogclicked(color: Int) {
////        callhomefragment(null, 0, -1, color)
//    }

    // The number of shortcuts should be changed.
//    override fun buttonCountChanged(numButtons: Int) {
////        callhomefragment(null, 0, numButtons, -100)
//    }

    // One app was chosen in the apps dialog.
    override fun ondialogclick(imv: ImageView?, tv: TextView?) {
//        callhomefragment(tv, mbc, -1, -100)
    }

//    private fun callnumberofappbuttonsdialogfragment() {
//        val newFragment: DialogFragment = ButtonCountDialogFragment.newInstance()
//        newFragment.setStyle(
//            DialogFragment.STYLE_NO_TITLE,
//            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
//        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
//        newFragment.show(supportFragmentManager, "dialog")
//    }

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

    /**
     * tv: The name of the app which was clicked.
     * bc: The number of the button which was clicked.
     * nb: Number of the buttons shown in the home fragment.
     *
     * Each time you call this method, HomeFragment will be rebuilt.
     */

//    private fun callhomefragment(tv: TextView?, bc: Int, nb: Int, color: Int) {
//        val hfj = newInstance(tv, bc, nb, color)
////        supportFragmentManager.beginTransaction().replace(R.id.maincontent, hfj).commit()
//    }

    private fun callAppsDialogFragment(bc: Int) {
        val newFragment: DialogFragment = AppsDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(supportFragmentManager, "dialog")
        mbc = bc
    }

    // The ColorDialog receives a reference to MainActivity through the
    // DialogFragment.onAttach() callback, which it uses to call the following methods
    // defined by the ColorDialog.ColorDialogListener interface
    override fun onColorChanged(dialog: DialogFragment, newColor: Int) {
        // Update the SharedPrefs with the new color
        rangshpref?.edit().let {
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
        updateBackgroundColor(rangshpref)
    }

    private fun updateBackgroundColor(color: SharedPreferences?) {
        // Whenever you want to update the background color with
        // sharedPrefs, just call this function
        buttonsHolder?.setBackgroundColor(
            color?.getInt(colorWriteKey, 0) ?: Color.WHITE
        )
    }

}
