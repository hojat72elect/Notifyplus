package ca.sudbury.hghasemi.notifyplus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import java.util.Objects;


/**
 * Created by Hojat_Ghasemi on Thursday , 30 March 2017 in kerman.
 */
public class HomeFragmentJadid extends Fragment implements View.OnClickListener, View.OnTouchListener {

    @SuppressLint("StaticFieldLeak")
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
    private LinearLayout buttonsholder;//linear layout that contains the linear layout with buttons.
    private PackageManager mPm;
    private SharedPreferences isnotifon;
    private SharedPreferences baval;
    private SharedPreferences bdovom;
    private SharedPreferences bsevom;
    private SharedPreferences bcharom;
    private SharedPreferences bpanjom;
    private SharedPreferences bshishom;
    private SharedPreferences bhaftom;
    private SharedPreferences bhashtom;

    private SharedPreferences rangshpref;
    private Button ab1;
    private Button ab2;
    private Button ab3;
    private Button ab4;
    private Button ab5;
    private Button ab6;
    private Button ab7;
    private Button ab8;
    private String write_key_aval = "bavalsharedpref";
    private String write_key_dovom = "bdovomsharedpref";
    private String write_key_sevom = "bsevomsharedpref";
    private String write_key_charom = "bcharomsharedpref";
    private String write_key_panjom = "bpanjomsharedpref";
    private String write_key_shishom = "bshishomsharedpref";
    private String write_key_haftom = "bhaftomsharedpref";
    private String write_key_hashtom = "bhashtomsharedpref";

    private String write_key_rang = "rangsharedpref";

    private View noabview;
    private View bgcolorview;
    private View mtogglenot;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
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


        return new HomeFragmentJadid();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View result = inflater.inflate(R.layout.home_layout_jadid, container, false);

        TextView tv1 = result.findViewById(R.id.entext);
        TextView tv2 = result.findViewById(R.id.entextrigho);
        TextView tv3 = result.findViewById(R.id.appsnumber);
        TextView tv4 = result.findViewById(R.id.appsnumberrigho);
        TextView tv5 = result.findViewById(R.id.bgc);
        TextView tv6 = result.findViewById(R.id.bgcrigho);

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
                    roudaki(notification_state, number_of_app_buttons);
                    //starting the notification with the shared preferences we have
                } else {
                    // The toggle is disabled
                    notification_state = false;
                    notifeditor.putBoolean(write_key_notif, false);
                    getActivity().stopService(new Intent(getActivity(), NotificationService.class));
                    //stoping the  whole notification thing.
                }
                notifeditor.apply();
            }


        });
        Context c = getActivity().getApplicationContext();
        mPm = c.getPackageManager();
        number_of_app_buttons = getActivity().getSharedPreferences("number_of_app_buttons_prefs", 0);

        SharedPreferences tashvigh_number = getActivity().getSharedPreferences("tashvigh_prefs", 0);

        isnotifon = getActivity().getSharedPreferences("notify_on_orefs", 0);

        baval = getActivity().getSharedPreferences("first_button_prefs", 0);
        bdovom = getActivity().getSharedPreferences("second_button_prefs", 0);
        bsevom = getActivity().getSharedPreferences("third_button_prefs", 0);
        bcharom = getActivity().getSharedPreferences("forth_button_prefs", 0);
        bpanjom = getActivity().getSharedPreferences("fifth_button_prefs", 0);
        bshishom = getActivity().getSharedPreferences("sixth_button_prefs", 0);
        bhaftom = getActivity().getSharedPreferences("seventh_button_prefs", 0);
        bhashtom = getActivity().getSharedPreferences("eighth_button_prefs", 0);

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
            noabeditor.apply();
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
            rangeitor.apply();

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
                    bavaleditor.apply();
                    break;

                case 2:
                    // ab2.setBackgroundDrawable(imvofappclicked.getDrawable());
                    SharedPreferences.Editor bdovomeditor = bdovom.edit();
                    bdovomeditor.putString(write_key_dovom, tvofappclicked.getText().toString());
                    bdovomeditor.apply();

                    break;

                case 3:
                    // ab3.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bsevomeditor = bsevom.edit();
                    bsevomeditor.putString(write_key_sevom, tvofappclicked.getText().toString());
                    bsevomeditor.apply();
                    break;

                case 4:
                    // ab4.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bcharomeditor = bcharom.edit();
                    bcharomeditor.putString(write_key_charom, tvofappclicked.getText().toString());
                    bcharomeditor.apply();
                    break;

                case 5:
                    // ab5.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bpanjomeditor = bpanjom.edit();
                    bpanjomeditor.putString(write_key_panjom, tvofappclicked.getText().toString());
                    bpanjomeditor.apply();
                    break;

                case 6:
                    //ab6.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bshishomeditor = bshishom.edit();
                    bshishomeditor.putString(write_key_shishom, tvofappclicked.getText().toString());
                    bshishomeditor.apply();
                    break;

                case 7:
                    // ab7.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bhaftomeditor = bhaftom.edit();
                    bhaftomeditor.putString(write_key_haftom, tvofappclicked.getText().toString());
                    bhaftomeditor.apply();
                    break;


                case 8:
                    // ab7.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    SharedPreferences.Editor bhashtomeditor = bhashtom.edit();
                    bhashtomeditor.putString(write_key_hashtom, tvofappclicked.getText().toString());
                    bhashtomeditor.apply();
                    break;

                default:

                    break;


            }


        }
        hafez();

        if (isnotifyon) {
            roudaki(notification_state, number_of_app_buttons);
            //agar notification ghablan neshan dade shode bashad ,
            // an ra update mikonim.
        }

        /////////////////////////////////////////////////////////
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "kidfont.ttf");
        tv1.setTypeface(iransanserif);
        tv2.setTypeface(iransanserif);
        tv3.setTypeface(iransanserif);
        tv4.setTypeface(iransanserif);
        tv5.setTypeface(iransanserif);
        tv6.setTypeface(iransanserif);

        return (result);
    }//end of oncreateview().

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


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
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                break;

            case 2:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                break;

            case 3:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bsevom.getString(write_key_sevom, null))));
                } catch (Exception ignored) {
                }
                break;

            case 4:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bsevom.getString(write_key_sevom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bcharom.getString(write_key_charom, null))));
                } catch (Exception ignored) {
                }
                break;

            case 5:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bsevom.getString(write_key_sevom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bcharom.getString(write_key_charom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bpanjom.getString(write_key_panjom, null))));
                } catch (Exception ignored) {
                }
                break;

            case 6:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bsevom.getString(write_key_sevom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bcharom.getString(write_key_charom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bpanjom.getString(write_key_panjom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab6.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bshishom.getString(write_key_shishom, null))));
                } catch (Exception ignored) {
                }
                break;

            case 7:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bsevom.getString(write_key_sevom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bcharom.getString(write_key_charom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bpanjom.getString(write_key_panjom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab6.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bshishom.getString(write_key_shishom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab7.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bhaftom.getString(write_key_haftom, null))));
                } catch (Exception ignored) {
                }
                break;

            default:
                try {
                    ab1.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(baval.getString(write_key_aval, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab2.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bdovom.getString(write_key_dovom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab3.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bsevom.getString(write_key_sevom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab4.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bcharom.getString(write_key_charom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab5.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bpanjom.getString(write_key_panjom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab6.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bshishom.getString(write_key_shishom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab7.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bhaftom.getString(write_key_haftom, null))));
                } catch (Exception ignored) {
                }
                try {
                    ab8.setBackgroundDrawable(mPm.getApplicationIcon(Objects.requireNonNull(bhashtom.getString(write_key_hashtom, null))));
                } catch (Exception ignored) {
                }
                break;


        }


        tvofappclicked = null;
        bc = 0;

    }//end of  hafez().


    private void roudaki(boolean notify_user, SharedPreferences number_of_app_buttons) {


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


                case 8:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify8);
                    favoriteapps = new String[]{baval.getString(write_key_aval, null),
                            bdovom.getString(write_key_dovom, null)
                            , bsevom.getString(write_key_sevom, null)
                            , bcharom.getString(write_key_charom, null)
                            , bpanjom.getString(write_key_panjom, null)
                            , bshishom.getString(write_key_shishom, null)
                            , bhaftom.getString(write_key_haftom, null)
                            , bhashtom.getString(write_key_hashtom, null)};
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
        } catch (Exception ignored) {
        }

    }//end of roudaki().

    private void showlist(int bc) {


        mnoabListener.noabmethod(1, bc);
    }


    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    private void sadie() {

        try {

            int m = number_of_app_buttons.getInt(write_key, 0);
            //linear layout with the buttons inside of it.
            LinearLayout buttons_row;
            switch (m) {
                case 1:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_1_button, null);
                    ab1 = buttons_row.findViewById(R.id.button1);
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

                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
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


                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
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


                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = buttons_row.findViewById(R.id.button4);
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


                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = buttons_row.findViewById(R.id.button5);
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


                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = buttons_row.findViewById(R.id.button6);
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

                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = buttons_row.findViewById(R.id.button6);
                    ab6.setOnTouchListener(this);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });

                    ab7 = buttons_row.findViewById(R.id.button7);
                    ab7.setOnTouchListener(this);
                    ab7.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 7;
                            showlist(bc);
                        }
                    });


                    break;

                case 8:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_8_button, null);

                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = buttons_row.findViewById(R.id.button6);
                    ab6.setOnTouchListener(this);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });

                    ab7 = buttons_row.findViewById(R.id.button7);
                    ab7.setOnTouchListener(this);
                    ab7.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 7;
                            showlist(bc);
                        }
                    });

                    ab8 = buttons_row.findViewById(R.id.button8);
                    ab8.setOnTouchListener(this);
                    ab8.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 8;
                            showlist(bc);
                        }
                    });

                    break;


                default:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_8_button, null);

                    ab1 = buttons_row.findViewById(R.id.button1);
                    ab1.setOnTouchListener(this);
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = buttons_row.findViewById(R.id.button2);
                    ab2.setOnTouchListener(this);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = buttons_row.findViewById(R.id.button3);
                    ab3.setOnTouchListener(this);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = buttons_row.findViewById(R.id.button4);
                    ab4.setOnTouchListener(this);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = buttons_row.findViewById(R.id.button5);
                    ab5.setOnTouchListener(this);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = buttons_row.findViewById(R.id.button6);
                    ab6.setOnTouchListener(this);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });

                    ab7 = buttons_row.findViewById(R.id.button7);
                    ab7.setOnTouchListener(this);
                    ab7.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 7;
                            showlist(bc);
                        }
                    });

                    ab8 = buttons_row.findViewById(R.id.button8);
                    ab8.setOnTouchListener(this);
                    ab8.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 8;
                            showlist(bc);
                        }
                    });

                    SharedPreferences.Editor bavaleditor = baval.edit();
                    bavaleditor.putString(write_key_aval, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bavaleditor.apply();

                    SharedPreferences.Editor bdovomeditor = bdovom.edit();
                    bdovomeditor.putString(write_key_dovom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bdovomeditor.apply();

                    SharedPreferences.Editor bsevomeditor = bsevom.edit();
                    bsevomeditor.putString(write_key_sevom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bsevomeditor.apply();

                    SharedPreferences.Editor bcharomeditor = bcharom.edit();
                    bcharomeditor.putString(write_key_charom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bcharomeditor.apply();

                    SharedPreferences.Editor bpanjomeditor = bpanjom.edit();
                    bpanjomeditor.putString(write_key_panjom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bpanjomeditor.apply();


                    SharedPreferences.Editor bshishomeditor = bshishom.edit();
                    bshishomeditor.putString(write_key_shishom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bshishomeditor.apply();


                    SharedPreferences.Editor bhaftomeditor = bhaftom.edit();
                    bhaftomeditor.putString(write_key_haftom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bhaftomeditor.apply();

                    SharedPreferences.Editor bhashtomeditor = bhashtom.edit();
                    bhashtomeditor.putString(write_key_hashtom, "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus");
                    bhashtomeditor.apply();

                    SharedPreferences.Editor noabeditor = number_of_app_buttons.edit();
                    noabeditor.putInt(write_key, 8);
                    noabeditor.apply();


                    SharedPreferences.Editor rangeditor = rangshpref.edit();
                    rangeditor.putInt(write_key_rang, Color.argb(255, 255, 255, 255));
                    rangeditor.apply();
                    //dar avalin bare ejraye app hamishe in default ast ke rokh midahad.

                    Toast.makeText(getActivity().getApplicationContext(), "خوش آمدید!", Toast.LENGTH_LONG).show();

                    // be activity begoim ke helpdialogfragment ra rah andazi konad.
                    mnoabListener.noabmethod(3, 0);
                    break;
            }

            buttonsholder.removeAllViews();
            buttonsholder.addView(buttons_row);
            //in 2 khate bala ra be hich vajh taghir nadahid bayad hatman be hamin shekl bashand.

        } catch (Exception ignored) {
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setAlpha(0);
        }

        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            v.setAlpha(1);
        }

        //ma bedin vasile yek effecte click shodan ra be in kelidhaye toye safhe asli emal mikonim.
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
