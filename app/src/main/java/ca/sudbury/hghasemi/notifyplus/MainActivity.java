package ca.sudbury.hghasemi.notifyplus;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;

/*created by Hojat_Ghasemi on Saturday , 11 March 2017.
 *
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragmentJadid.listenerfornoab,
        AppsDialogFragment.dialogclicked,
        NumberofappbuttonsDialogFragment.buttonclicked,
        BackgroundColorDialogFragment.buttonclicked {


    private int mbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getFragmentManager().findFragmentById(R.id.maincontent) == null) {
            callhomefragment(null, 0, -1, -100);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            sharemethod();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            callhomefragment(null, 0, -1, -100);
        } else if (id == R.id.nav_contactus) {
            callaboutappfragment();
        } else if (id == R.id.nav_exit) {
            super.onBackPressed();
        } else if (id == R.id.nav_help) {
            callhelpdialogfragment();
        } else if (id == R.id.nav_share) {
            sharemethod();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sharemethod() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharetext) + "\n" + "The app isn't currently published in any markets");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private void callaboutappfragment() {
        DialogFragment newFragment = AboutappFragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);//in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(getSupportFragmentManager(), "dialog");

    }

    private void callhomefragment(TextView tv, int bc, int nb, int color) {

        //ImageView imv , akse applicationi ke click shode ast.
        //TextView tv , name applicationi ke click shode ast.
        //int bc , shomareye kelidi ast ke click shode ast.
        //int nb , tedade kelid haye neshan dade shode dar barname ast.
        HomeFragmentJadid hfj = HomeFragmentJadid.newInstance(tv, bc, nb, color);
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, hfj).commit();
        //ba kari ke toye in method kardim ,
        //har bar ke in method farakhani shavad , kole homefragment az aval sakhte shode va be
        //user neshan dade khahad shod.

    }


    private void callAppsdialogfragment(int bc) {


        DialogFragment newFragment = AppsDialogFragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);//in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(getSupportFragmentManager(), "dialog");
        mbc = bc;


    }

    @Override
    public void ondialogclick(ImageView imv, TextView tv) {

        callhomefragment(tv, mbc, -1, -100);
    }


    @Override
    public void noabmethod(int dialognumber, int bc) {
        // mitoonam inja bar asase meghdare yek moteghayere integer , tasmim begiram ke
        //kodam yek az 3 ta diloge mazkoor dar in fragment ra neshan bedaham.

        switch (dialognumber) {
            case 0:
                callnumberofappbuttonsdialogfragment();

                break;

            case 1:
                //  app haye nasb shode dar dastgah ra neshan midahad.
                callAppsdialogfragment(bc);
                break;

            case 2:
                // changes background color.
                callbackgroundcolordialogfragment();
                break;

            case 3:
                callhelpdialogfragment();
                break;

            case 5:
                callrequestupdatefragment();
                break;

            default:

                break;
        }

    }


    private void callrequestupdatefragment() {
        // dialogfragment baraye update ra neshan bedahid.
        DialogFragment newFragment = updatedialogfragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);//in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.setCancelable(true);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }


    private void callhelpdialogfragment() {

        DialogFragment newFragment = HelpDialogfragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);//in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void callbackgroundcolordialogfragment() {
        DialogFragment newFragment = BackgroundColorDialogFragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);//in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(getSupportFragmentManager(), "dialog");

    }

    private void callnumberofappbuttonsdialogfragment() {
        DialogFragment newFragment = NumberofappbuttonsDialogFragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);//in khat baraye inke kar konad bayad hatman az daroone acrivity seda zade shavad.
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void dialogbuttonclicked(int nbc) {
        //inja homefragmentjadid ra seda mizanim.
        callhomefragment(null, 0, nbc, -100);
    }


    @Override
    public void rangdialogclicked(int color) {
        callhomefragment(null, 0, -1, color);
    }
}
