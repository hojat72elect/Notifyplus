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
import ca.sudbury.hghasemi.notifyplus.AppsDialogFragment.dialogclicked
import ca.sudbury.hghasemi.notifyplus.ButtonCountDialogFragment.ButtonCountChangedListener
import ca.sudbury.hghasemi.notifyplus.ColorDialogFragment.buttonclicked
import ca.sudbury.hghasemi.notifyplus.HomeFragment.Companion.newInstance
import ca.sudbury.hghasemi.notifyplus.HomeFragment.Listenerfornoab
import com.google.android.material.navigation.NavigationView

/*created by Hojat_Ghasemi on Saturday , 11 March 2017.
 *
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    Listenerfornoab, dialogclicked, ButtonCountChangedListener, buttonclicked {
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
        if (fragmentManager.findFragmentById(R.id.maincontent) == null) {
            callhomefragment(null, 0, -1, -100)
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            sharemethod()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                callhomefragment(null, 0, -1, -100)
            }
            R.id.nav_contactus -> {
                callAboutAppFragment()
            }
            R.id.nav_exit -> {
                super.onBackPressed()
            }
            R.id.nav_help -> {
                callhelpdialogfragment()
            }
            R.id.nav_share -> {
                sharemethod()
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun sharemethod() {
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

    private fun callhomefragment(tv: TextView?, bc: Int, nb: Int, color: Int) {

        //ImageView imv , akse applicationi ke click shode ast.
        //TextView tv , name applicationi ke click shode ast.
        //int bc , shomareye kelidi ast ke click shode ast.
        //int nb , tedade kelid haye neshan dade shode dar barname ast.
        val hfj = newInstance(tv, bc, nb, color)
        supportFragmentManager.beginTransaction().replace(R.id.maincontent, hfj).commit()
        //ba kari ke toye in method kardim ,
        //har bar ke in method farakhani shavad , kole homefragment az aval sakhte shode va be
        //user neshan dade khahad shod.
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

    override fun ondialogclick(imv: ImageView, tv: TextView) {
        callhomefragment(tv, mbc, -1, -100)
    }

    override fun noabmethod(dialognumber: Int, bc: Int) {
        // mitoonam inja bar asase meghdare yek moteghayere integer , tasmim begiram ke
        //kodam yek az 3 ta diloge mazkoor dar in fragment ra neshan bedaham.
        when (dialognumber) {
            0 -> callnumberofappbuttonsdialogfragment()
            1 ->                 //  app haye nasb shode dar dastgah ra neshan midahad.
                callAppsDialogFragment(bc)
            2 ->                 // changes background color.
                callbackgroundcolordialogfragment()
            3 -> callhelpdialogfragment()
            5 -> callrequestupdatefragment()
            else -> {}
        }
    }

    private fun callrequestupdatefragment() {
        // dialogfragment baraye update ra neshan bedahid.
        val newFragment: DialogFragment = UpdateDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.isCancelable = true
        newFragment.show(supportFragmentManager, "dialog")
    }

    private fun callhelpdialogfragment() {
        val newFragment: DialogFragment = HelpDialogFragment.newInstance()
        newFragment.setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ) //in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
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

    override fun rangdialogclicked(color: Int) {
        callhomefragment(null, 0, -1, color)
    }

    override fun buttonCountChanged(numButtons: Int) {
        //inja homefragmentjadid ra seda mizanim.
        callhomefragment(null, 0, numButtons, -100)
    }
}