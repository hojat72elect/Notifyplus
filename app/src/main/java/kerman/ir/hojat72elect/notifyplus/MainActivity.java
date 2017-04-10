package kerman.ir.hojat72elect.notifyplus;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



/*created by hojat72elect on shanbe 21 esfand 1395 in kerman.
*
* */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragmentJadid.listenerfornoab,
        AppsDialogFragment.dialogclicked,
        NumberofappbuttonsDialogFragment.buttonclicked,
        BackgroundColorDialogFragment.buttonclicked {

    private SettingsFragment sf = null;
    private HomeFragmentJadid hfj = null;
    private AboutappFragment af = null;
    private int mbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getFragmentManager().findFragmentById(R.id.maincontent) == null) {
            callhomefragment(null, null, 0, -1, -100);
        }


    }//end of onCreate.

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            callhomefragment(null, null, 0, -1, -100);
        } else if (id == R.id.nav_settings) {
            callsettingsfragment();
        } else if (id == R.id.nav_aboutapp) {
            callaboutappfragment();
        } else if (id == R.id.nav_likeus) {
//TODO bayad inja yek intent baraye raftan be safheye app dar market ra ijad konam.
        } else if (id == R.id.nav_exit) {
            super.onBackPressed();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callaboutappfragment() {
        if (af == null) {
            af = new AboutappFragment();

        }
        if (!af.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.maincontent, af).commit();//
        }
    }

    private void callhomefragment(ImageView imv, TextView tv, int bc, int nb, int color) {

        //ImageView imv , akse applicationi ke click shode ast.
        //TextView tv , name applicationi ke click shode ast.
        //int bc , shomareye kelidi ast ke click shode ast.
        //int nb , tedade kelid haye neshan dade shode dar barname ast.
        hfj = HomeFragmentJadid.newInstance(imv, tv, bc, nb, color);


        getFragmentManager().beginTransaction()
                .replace(R.id.maincontent, hfj).commit();


        //ba kari ke toye in method kardim ,
        //har bar ke in method farakhani shavad , kole homefragment az aval sakhte shode va be
        //user neshan dade khahad shod.

    }

    private void callsettingsfragment() {
        if (sf == null) {
            sf = new SettingsFragment();

        }
        if (!sf.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.maincontent, sf).commit();
        }
    }


    private void callAppsdialogfragment(int bc) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment adf = getFragmentManager().findFragmentByTag("dialog");
        if (adf != null) {
            ft.remove(adf);
        }
        ft.addToBackStack(null);
        mbc = bc;

        DialogFragment newFragment = AppsDialogFragment.newInstance();
        newFragment.show(ft, "dialog");

    }

    @Override
    public void ondialogclick(ImageView imv, TextView tv) {

        callhomefragment(imv, tv, mbc, -1, -100);
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

            default:
                //in hamishe rokh midahad???
                break;
        }

    }

    private void callbackgroundcolordialogfragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment bgcdf = getFragmentManager().findFragmentByTag("dialog");
        if (bgcdf != null) {
            ft.remove(bgcdf);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = BackgroundColorDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
    }

    private void callnumberofappbuttonsdialogfragment() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment noabdf = getFragmentManager().findFragmentByTag("dialog");

        if (noabdf != null) {
            ft.remove(noabdf);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = NumberofappbuttonsDialogFragment.newInstance();
        newFragment.show(ft, "dialog");

    }

    @Override
    public void dialogbuttonclicked(int nbc) {
        //inja homefragmentjadid ra seda mizanim.
        callhomefragment(null, null, 0, nbc, -100);
    }


    @Override
    public void rangdialogclicked(int color) {
        callhomefragment(null, null, 0, -1, color);
    }
}
