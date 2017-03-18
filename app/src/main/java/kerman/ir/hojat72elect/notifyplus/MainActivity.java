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



/*created by hojat72elect on shanbe 21 esfand 1395 in kerman.
*
* */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.listenerfordialog {

    private SettingsFragment sf=null;
    private HomeFragment hf=null;
    private AboutappFragment af=null;

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
           callhomefragment();
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
         callhomefragment();
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
            af= new AboutappFragment();

        }
        if (!af.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.maincontent, af).commit();//
        }
    }

    private void callhomefragment() {
        if (hf == null) {
            hf=new HomeFragment();

        }
        if (!hf.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.maincontent, hf).commit();//
        }
    }

    private void callsettingsfragment() {
        if (sf == null) {
            sf=new SettingsFragment();

        }
        if (!sf.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.maincontent, sf).commit();//
        }
    }

    @Override
    public void ondialogshow(int bc) {


callappsdialogfragment(bc);

    }

    private void callappsdialogfragment(int bc) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment adf = getFragmentManager().findFragmentByTag("dialog");
        if (adf != null) {
            ft.remove(adf);
        }
        ft.addToBackStack(null);


        DialogFragment newFragment = AppsDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
    }
}
