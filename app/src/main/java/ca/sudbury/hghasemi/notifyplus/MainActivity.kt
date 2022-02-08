package ca.sudbury.hghasemi.notifyplus

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import ca.sudbury.hghasemi.notifyplus.AppsDialogFragment.DialogClicked
import ca.sudbury.hghasemi.notifyplus.ButtonCountDialogFragment.ButtonCountChangedListener
import ca.sudbury.hghasemi.notifyplus.ColorDialogFragment.buttonclicked
import ca.sudbury.hghasemi.notifyplus.HomeFragment.Companion.newInstance
import com.google.android.material.navigation.NavigationView

/*
 First created by Hojat Ghasemi on Saturday , 11 March 2017.
 Contact the author at "https://github.com/hojat72elect"
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    DialogClicked, ButtonCountChangedListener, buttonclicked {
    private var mbc = 0

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
                callAboutAppFragment()
            }
            R.id.nav_help -> {
                callhelpdialogfragment()
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
    override fun rangdialogclicked(color: Int) {
        callhomefragment(null, 0, -1, color)
    }

    // The number of shortcuts should be changed.
    override fun buttonCountChanged(numButtons: Int) {
        callhomefragment(null, 0, numButtons, -100)
    }

    // One app was chosen in the apps dialog.
    override fun ondialogclick(imv: ImageView?, tv: TextView?) {
        callhomefragment(tv, mbc, -1, -100)
    }

    // A very simple dialog containing info about app's capabilities.
    private fun callhelpdialogfragment() {
        val newFragment: DialogFragment = HelpDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        )
        newFragment.show(supportFragmentManager, "dialog")
    }

    private fun callbackgroundcolordialogfragment() {
        val newFragment: DialogFragment = ColorDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(supportFragmentManager, "dialog")
    }

    private fun callnumberofappbuttonsdialogfragment() {
        val newFragment: DialogFragment = ButtonCountDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(supportFragmentManager, "dialog")
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

    private fun callAboutAppFragment() {
        val newFragment: DialogFragment = ContactUsDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(supportFragmentManager, "dialog")
    }

    /**
     * tv: The name of the app which was clicked.
     * bc: The number of the button which was clicked.
     * nb: Number of the buttons shown in the home fragment.
     *
     * Each time you call this method, HomeFragment will be rebuilt.
     */
    private fun callhomefragment(tv: TextView?, bc: Int, nb: Int, color: Int) {
        val hfj = newInstance(tv, bc, nb, color)
//        supportFragmentManager.beginTransaction().replace(R.id.maincontent, hfj).commit()
    }

    private fun callAppsDialogFragment(bc: Int) {
        val newFragment: DialogFragment = AppsDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(supportFragmentManager, "dialog")
        mbc = bc
    }

}