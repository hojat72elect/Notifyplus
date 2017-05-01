package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ir.adad.client.Adad;

/**
 * Created by hojat72elect on panjshanbe 10 farvardin 1396 in kerman.
 */
public class HomeFragmentJadid extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private static TextView tvofappclicked;
    private static int bc;// the number of button which is clicked.
    private static int kelidsnumber;//total number of buttons which are shown in app.
    private static int rangbackground;//rang pas zamine
    private static SharedPreferences number_of_app_buttons; //shared preferences for saving the number of app buttons.
    private static String write_key = "noab";//the key for writing on the shared preferences that contains the number of app buttons.
    private static String write_key_notif = "noton";//the key for writing on the shared preferences that contains the state of notification.
    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    LayoutInflater buttons_inflater;
    listenerfornoab mnoabListener;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private LinearLayout buttonsholder;//linear layout that contains the linear layout with buttons.
    private PackageManager mPm;
    private Context c;
    private SharedPreferences isnotifon;
    private SharedPreferences baval;
    private SharedPreferences bdovom;
    private SharedPreferences bsevom;
    private SharedPreferences bcharom;
    private SharedPreferences bpanjom;
    private SharedPreferences bshishom;
    private SharedPreferences bhaftom;
    private SharedPreferences rangshpref;
    private Button ab1;
    private Button ab2;
    private Button ab3;
    private Button ab4;
    private Button ab5;
    private Button ab6;
    private Button ab7;
    private String write_key_aval = "bavalsharedpref";
    private String write_key_dovom = "bdovomsharedpref";
    private String write_key_sevom = "bsevomsharedpref";
    private String write_key_charom = "bcharomsharedpref";
    private String write_key_panjom = "bpanjomsharedpref";
    private String write_key_shishom = "bshishomsharedpref";
    private String write_key_haftom = "bhaftomsharedpref";
    private String write_key_rang = "rangsharedpref";

    private LinearLayout buttons_row;//linear layout with the buttons inside of it.
    private View noabview;
    private View bgcolorview;
    private View mtogglenot;

    private Switch bnot;
    private boolean notification_state;


    static HomeFragmentJadid newInstance(TextView tv, int mbc, int nb, int color) {

        tvofappclicked = tv;// in va balayi ash faghat agar az dialoge entekhabe app bargardim
        //null nakhahand bood.

        bc = mbc;//faghat agar az dialoge entekhabe app bargardim
        //0 nakhahad bood.

        kelidsnumber = nb;//faghat agar az dialoge entekhabe tedade kelid ha bargardim
        //-1 nakhahad bood.

        rangbackground = color;//faghat agar az dialoge entekhabe range pas zamine bargardim
        //-100 nakhahad bood.

        //dar tamame mavared , in fragment ba estefade az in methode newInstance(ImageView,TextView)
        //sakhte mishavad . vaghti baraye avalin bar sakhte mishavad ba null por mishavad
        //va vaghti ke az dialog be an bar migardim ba etelaate haghighi porash mikonim.


        HomeFragmentJadid f = new HomeFragmentJadid();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Adad.initialize(getActivity().getApplicationContext());
        View result = inflater.inflate(R.layout.home_layout_jadid, container, false);

        tv1 = (TextView) result.findViewById(R.id.entext);
        tv2 = (TextView) result.findViewById(R.id.entextrigho);
        tv3 = (TextView) result.findViewById(R.id.appsnumber);
        tv4 = (TextView) result.findViewById(R.id.appsnumberrigho);
        tv5 = (TextView) result.findViewById(R.id.bgc);
        tv6 = (TextView) result.findViewById(R.id.bgcrigho);

        buttonsholder = (LinearLayout) result.findViewById(R.id.newbuttonsholder);

        noabview = result.findViewById(R.id.noalayout);
        bgcolorview = result.findViewById(R.id.bgcolorlayout);
        bnot = (Switch) result.findViewById(R.id.notifyswitch);
        mtogglenot = result.findViewById(R.id.togglenot);

        bnot.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor notifeditor = isnotifon.edit();

                if (isChecked) {
                    // The toggle is enabled
                    notification_state = true;
                    notifeditor.putBoolean(write_key_notif, true);
                    roudaki(notification_state, number_of_app_buttons, rangshpref.getInt(write_key_rang, 0));
                    //starting the notification with the shared preferences we have
                } else {
                    // The toggle is disabled
                    notification_state = false;
                    notifeditor.putBoolean(write_key_notif, false);
                    getActivity().stopService(new Intent(getActivity(), NotificationService.class));
                    //stoping the  whole notification thing.
                }
                notifeditor.commit();
            }


        });
        c = getActivity().getApplicationContext();
        mPm = c.getPackageManager();
        number_of_app_buttons = getActivity().getSharedPreferences("number_of_app_buttons_prefs", 0);
        isnotifon = getActivity().getSharedPreferences("notify_on_orefs", 0);

        baval = getActivity().getSharedPreferences("first_button_prefs", 0);
        bdovom = getActivity().getSharedPreferences("second_button_prefs", 0);
        bsevom = getActivity().getSharedPreferences("third_button_prefs", 0);
        bcharom = getActivity().getSharedPreferences("forth_button_prefs", 0);
        bpanjom = getActivity().getSharedPreferences("fifth_button_prefs", 0);
        bshishom = getActivity().getSharedPreferences("sixth_button_prefs", 0);
        bhaftom = getActivity().getSharedPreferences("seventh_button_prefs", 0);
        rangshpref = getActivity().getSharedPreferences("rang_prefs", 0);

        buttons_inflater = inflater;

        noabview.setOnClickListener(this);
        bgcolorview.setOnClickListener(this);
        mtogglenot.setOnClickListener(this);


        ///////////////////////////////////////////////////////
        //inja switch marboot be notification ra meghdar dehi mikonim.
        boolean isnotifyon = isnotifon.getBoolean(write_key_notif, false);
        if (isnotifyon) {
            bnot.setChecked(true);
        } else {
            bnot.setChecked(false);
        }
        ///////////////////////////////////////////////////////


        if (kelidsnumber != -1) {
            //az dialoge entekhabe tedade kelidha bargashte im.
            SharedPreferences.Editor noabeditor = number_of_app_buttons.edit();
            noabeditor.putInt(write_key, kelidsnumber);
            noabeditor.commit();
        }
        sadie();
        //////////////////////////////////////
        //  dar inja rang ra emal mikonim.
        if (rangbackground != -100) {
            SharedPreferences.Editor rangeitor = rangshpref.edit();
            rangeitor.putInt(write_key_rang, Color.argb(Color.alpha(rangbackground),
                    Color.red(rangbackground),
                    Color.green(rangbackground),
                    Color.blue(rangbackground)
            ));
            rangeitor.commit();

        }

        ////////////////////////////////////// ferdousi();

        buttonsholder.setBackgroundColor(rangshpref.getInt(write_key_rang, 0));
        //////////////////////////////////////

        if (bc != 0) {

            switch (bc) {
                //bc shomareye buttoni ast ke click shode ast.
                //here:we adopt this function for 8 cases.
                case 1:
                    //  ab1.setBackgroundDrawable(imvofappclicked.getDrawable());

                    SharedPreferences.Editor bavaleditor = baval.edit();
                    bavaleditor.putString(write_key_aval, tvofappclicked.getText().toString());
                    bavaleditor.commit();
                    break;

                case 2:
                    // ab2.setBackgroundDrawable(imvofappclicked.getDrawable());
                    SharedPreferences.Editor bdovomeditor = bdovom.edit();
                    bdovomeditor.putString(write_key_dovom, tvofappclicked.getText().toString());
                    bdovomeditor.commit();

                    break;

                case 3:
                    // ab3.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bsevomeditor = bsevom.edit();
                    bsevomeditor.putString(write_key_sevom, tvofappclicked.getText().toString());
                    bsevomeditor.commit();
                    break;

                case 4:
                    // ab4.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bcharomeditor = bcharom.edit();
                    bcharomeditor.putString(write_key_charom, tvofappclicked.getText().toString());
                    bcharomeditor.commit();
                    break;

                case 5:
                    // ab5.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bpanjomeditor = bpanjom.edit();
                    bpanjomeditor.putString(write_key_panjom, tvofappclicked.getText().toString());
                    bpanjomeditor.commit();
                    break;

                case 6:
                    //ab6.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bshishomeditor = bshishom.edit();
                    bshishomeditor.putString(write_key_shishom, tvofappclicked.getText().toString());
                    bshishomeditor.commit();
                    break;

                case 7:
                    // ab7.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bhaftomeditor = bhaftom.edit();
                    bhaftomeditor.putString(write_key_haftom, tvofappclicked.getText().toString());
                    bhaftomeditor.commit();
                    break;

                default:

                    break;


            }


        }
        hafez();

        if (isnotifyon) {
            roudaki(notification_state, number_of_app_buttons, rangshpref.getInt(write_key_rang, 0));
            //agar notification ghablan neshan dade shode bashad ,
            // an ra update mikonim.
        }
        /////////////////////////////////////////////////////////
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "Arabicgithub.ttf");
        tv1.setTypeface(iransanserif);
        tv2.setTypeface(iransanserif);
        tv3.setTypeface(iransanserif);
        tv4.setTypeface(iransanserif);
        tv5.setTypeface(iransanserif);
        tv6.setTypeface(iransanserif);

        /////////////////////////////////////////////////////////


        return (result);
    }//end of oncreateview().


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            mnoabListener = (listenerfornoab) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    private void hafez() {
        //this function , shows the apps which are chosen
        // earlier in the row in app's main page.

        // bayad akse morede nazar dar haman kelidi ke user click karde ast load shavad.
        int noab = number_of_app_buttons.getInt(write_key, 0);
        switch (noab) {
            case 1:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                } catch (Exception e) {

                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            case 2:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            case 3:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(bsevom.getString(write_key_sevom, null)));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            case 4:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(bsevom.getString(write_key_sevom, null)));
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(bcharom.getString(write_key_charom, null)));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            case 5:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(bsevom.getString(write_key_sevom, null)));
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(bcharom.getString(write_key_charom, null)));
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(bpanjom.getString(write_key_panjom, null)));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            case 6:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(bsevom.getString(write_key_sevom, null)));
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(bcharom.getString(write_key_charom, null)));
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(bpanjom.getString(write_key_panjom, null)));
                    ab6.setBackgroundDrawable(mPm.getApplicationIcon(bshishom.getString(write_key_shishom, null)));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            case 7:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(bsevom.getString(write_key_sevom, null)));
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(bcharom.getString(write_key_charom, null)));
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(bpanjom.getString(write_key_panjom, null)));
                    ab6.setBackgroundDrawable(mPm.getApplicationIcon(bshishom.getString(write_key_shishom, null)));
                    ab7.setBackgroundDrawable(mPm.getApplicationIcon(bhaftom.getString(write_key_haftom, null)));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(baval.getString(write_key_aval, null)));
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(bdovom.getString(write_key_dovom, null)));
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(bsevom.getString(write_key_sevom, null)));
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(bcharom.getString(write_key_charom, null)));
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(bpanjom.getString(write_key_panjom, null)));
                    ab6.setBackgroundDrawable(mPm.getApplicationIcon(bshishom.getString(write_key_shishom, null)));
                    ab7.setBackgroundDrawable(mPm.getApplicationIcon(bhaftom.getString(write_key_haftom, null)));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in hafez", Toast.LENGTH_LONG).show();

                }
                break;


        }


        tvofappclicked = null;
        bc = 0;

    }//end of  hafez().


    private void roudaki(boolean notify_user, SharedPreferences number_of_app_buttons, int color) {


        try {
            String[] favoriteapps;
            int number_app_buttons = number_of_app_buttons.getInt(write_key, 0);
            //////////////////////////////////////////////////////////////////////////////////////////////
            RemoteViews remote;
            switch (number_app_buttons) {
                case 1:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify1);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null)};
                    break;

                case 2:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify2);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)};
                    break;

                case 3:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify3);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)};
                    break;

                case 4:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify4);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)
                            , bcharom.getString(write_key_charom, null)};
                    break;

                case 5:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify5);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)
                            , bcharom.getString(write_key_charom, null)
                            , bpanjom.getString(write_key_panjom, null)};
                    break;

                case 6:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify6);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)
                            , bcharom.getString(write_key_charom, null)
                            , bpanjom.getString(write_key_panjom, null)
                            , bshishom.getString(write_key_shishom, null)};
                    break;

                case 7:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify7);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)
                            , bcharom.getString(write_key_charom, null)
                            , bpanjom.getString(write_key_panjom, null)
                            , bshishom.getString(write_key_shishom, null)
                            , bhaftom.getString(write_key_haftom, null)};
                    break;
                default:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify7);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)
                            , bcharom.getString(write_key_charom, null)
                            , bpanjom.getString(write_key_panjom, null)
                            , bshishom.getString(write_key_shishom, null)
                            , bhaftom.getString(write_key_haftom, null)};
                    break;
            }

            remote.setInt(R.id.notificationlayout, "setBackgroundColor", rangshpref.getInt(write_key_rang, 0));
            //////////////////////////////////////////////////////////////////////////////////////////////
            Intent notification_intent = new Intent(getActivity(), NotificationService.class);

            notification_intent.putExtra("ufa", favoriteapps);
            notification_intent.putExtra("notifyuser", notify_user);//doesn't have any utility for now.
            notification_intent.putExtra("viewgroup", remote);
            notification_intent.putExtra("numberofappbuttons", number_app_buttons);


            getActivity().startService(notification_intent);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.toString() + " in roudaki()", Toast.LENGTH_LONG).show();


        }

    }//end of roudaki().

    private void showlist(int bc) {


        mnoabListener.noabmethod(1, bc);
    }


    private void sadie() {

        try {

            int m = number_of_app_buttons.getInt(write_key, 0);
            switch (m) {
                case 1:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_1_button, null);
                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    break;

                case 2:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_2_button, null);

                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    break;

                case 3:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_3_button, null);


                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    break;

                case 4:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_4_button, null);


                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });
                    break;

                case 5:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_5_button, null);


                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });


                    break;

                case 6:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_6_button, null);


                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = (Button) buttons_row.findViewById(R.id.button6);
                    ab6.setOnTouchListener(this);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });


                    break;

                case 7:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_7_button, null);

                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = (Button) buttons_row.findViewById(R.id.button6);
                    ab6.setOnTouchListener(this);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });

                    ab7 = (Button) buttons_row.findViewById(R.id.button7);
                    ab7.setOnTouchListener(this);
                    ab7.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 7;
                            showlist(bc);
                        }
                    });


                    break;

                default:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_7_button, null);

                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = (Button) buttons_row.findViewById(R.id.button6);
                    ab6.setOnTouchListener(this);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });

                    ab7 = (Button) buttons_row.findViewById(R.id.button7);
                    ab7.setOnTouchListener(this);
                    ab7.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 7;
                            showlist(bc);
                        }
                    });

                    SharedPreferences.Editor bavaleditor = baval.edit();
                    bavaleditor.putString(write_key_aval, "kerman.ir.hojat72elect.notifyplus");
                    bavaleditor.apply();

                    SharedPreferences.Editor bdovomeditor = bdovom.edit();
                    bdovomeditor.putString(write_key_dovom, "kerman.ir.hojat72elect.notifyplus");
                    bdovomeditor.apply();

                    SharedPreferences.Editor bsevomeditor = bsevom.edit();
                    bsevomeditor.putString(write_key_sevom, "kerman.ir.hojat72elect.notifyplus");
                    bsevomeditor.apply();

                    SharedPreferences.Editor bcharomeditor = bcharom.edit();
                    bcharomeditor.putString(write_key_charom, "kerman.ir.hojat72elect.notifyplus");
                    bcharomeditor.apply();

                    SharedPreferences.Editor bpanjomeditor = bpanjom.edit();
                    bpanjomeditor.putString(write_key_panjom, "kerman.ir.hojat72elect.notifyplus");
                    bpanjomeditor.apply();


                    SharedPreferences.Editor bshishomeditor = bshishom.edit();
                    bshishomeditor.putString(write_key_shishom, "kerman.ir.hojat72elect.notifyplus");
                    bshishomeditor.apply();


                    SharedPreferences.Editor bhaftomeditor = bhaftom.edit();
                    bhaftomeditor.putString(write_key_haftom, "kerman.ir.hojat72elect.notifyplus");
                    bhaftomeditor.apply();

                    SharedPreferences.Editor noabeditor = number_of_app_buttons.edit();
                    noabeditor.putInt(write_key, 7);
                    noabeditor.apply();


                    SharedPreferences.Editor rangeitor = rangshpref.edit();
                    rangeitor.putInt(write_key_rang, Color.argb(255, 255, 255, 255));
                    rangeitor.apply();
                    //dar avalin bare ejraye app hamishe in default ast ke rokh midahad.

                    Toast.makeText(getActivity().getApplicationContext(), "خوش آمدید!", Toast.LENGTH_LONG).show();

                    // be activity begoim ke helpdialogfragment ra rah andazi konad.
                    mnoabListener.noabmethod(3, 0);
                    break;
            }

            buttonsholder.removeAllViews();
            buttonsholder.addView(buttons_row);
            //in 2 khate bala ra be hich vajh taghir nadahid bayad hatman be hamin shekl bashand.

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.toString() + "in the sadie", Toast.LENGTH_LONG).show();


        }


    }//end of sadie().

    @Override
    public void onClick(View v) {

        if (v == noabview) {
            mnoabListener.noabmethod(0, 0);
        } else if (v == bgcolorview) {
            mnoabListener.noabmethod(2, 0);
        } else if (v == mtogglenot) {
            bnot.toggle();

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setAlpha(0);
        }

        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            v.setAlpha(1);
        }

        return false;
    }


    public interface listenerfornoab {
        void noabmethod(int dialognumber, int bc);
        //bc !=0 yani inke bayad app haye daroone dastgah ra neshan bedahim.
        //dialognumber=0  dialogi ke tedade kelid haye daroone safhe asli ra az karbar miporsad.
        //dialognumber=1 dialogi ke app haye nasb shode dar dastgah ra neshan midahad.
        //dialognumber=2 dialogi ke background color ra miporsad.
    }

}
