package kerman.ir.hojat72elect.notifyplus;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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


    private HomeFragmentJadid hfj = null;
    private int mbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getFragmentManager().findFragmentById(R.id.maincontent) == null) {
            callhomefragment(null, 0, -1, -100);
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

            sharemethod();
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
            callhomefragment(null, 0, -1, -100);
        } else if (id == R.id.nav_nazardarbazar) {
            callsettingsfragment();
        } else if (id == R.id.nav_contactus) {
            callaboutappfragment();
        } else if (id == R.id.nav_exit) {
            super.onBackPressed();
        } else if (id == R.id.nav_help) {
            callhelpdialogfragment();
        } else if (id == R.id.nav_share) {
            sharemethod();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sharemethod() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharetext) + "\n" + "https://cafebazaar.ir/app/kerman.ir.hojat72elect.notifyplus/?l=fa");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private void callaboutappfragment() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment af = getFragmentManager().findFragmentByTag("dialog");
        if (af != null) {
            ft.remove(af);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = AboutappFragment.newInstance();
        newFragment.show(ft, "dialog");

    }

    private void callhomefragment(TextView tv, int bc, int nb, int color) {

        //ImageView imv , akse applicationi ke click shode ast.
        //TextView tv , name applicationi ke click shode ast.
        //int bc , shomareye kelidi ast ke click shode ast.
        //int nb , tedade kelid haye neshan dade shode dar barname ast.
        hfj = HomeFragmentJadid.newInstance(tv, bc, nb, color);


        getFragmentManager().beginTransaction()
                .replace(R.id.maincontent, hfj).commit();


        //ba kari ke toye in method kardim ,
        //har bar ke in method farakhani shavad , kole homefragment az aval sakhte shode va be
        //user neshan dade khahad shod.

    }

    private void callsettingsfragment() {
        try {
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(Uri.parse("bazaar://details?id=" + "kerman.ir.hojat72elect.notifyplus"));
            intent.setPackage("com.farsitel.bazaar");
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "مشکل در اتصال به سرور کافه بازار", Toast.LENGTH_LONG).show();


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

            default:
                //in hamishe rokh midahad???
                break;
        }

    }

    private void callhelpdialogfragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment bgcdf = getFragmentManager().findFragmentByTag("dialog");
        if (bgcdf != null) {
            ft.remove(bgcdf);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = HelpDialogfragment.newInstance();
        newFragment.show(ft, "dialog");
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
        callhomefragment(null, 0, nbc, -100);
    }


    @Override
    public void rangdialogclicked(int color) {
        callhomefragment(null, 0, -1, color);
    }
}
