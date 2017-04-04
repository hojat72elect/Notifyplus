package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by hojat72elect on panjshanbe 10 farvardin 1396 in kerman.
 */
public class HomeFragmentJadid extends Fragment implements View.OnClickListener {

    private static ImageView imvofappclicked;
    private static TextView tvofappclicked;
    private static int bc;// the number of button which is clicked.
    private static int kelidsnumber;//total number of buttons which are shown in app.


    private LinearLayout buttonsholder;//linear layout that contains the linear layout with buttons.
    private PackageManager mPm;
    private Context c;

    private SharedPreferences fapps; //shared preferences that saves user's favorite apps.
    private static SharedPreferences number_of_app_buttons; //shared preferences for saving the number of app buttons.
    private SharedPreferences isnotifon;

    LayoutInflater buttons_inflater;
    // listenerfordialog mListener;
    listenerfornoab mnoabListener;
    private Button ab1;
    private Button ab2;
    private Button ab3;
    private Button ab4;
    private Button ab5;
    private Button ab6;
    private Button ab7;
    private static String write_key = "noab";//the key for writing on the shared preferences that contains the number of app buttons.
    private static String write_key_notif = "noton";

    private LinearLayout buttons_row;//linear layout with the buttons inside of it.
    private View noabview;
    private View bgcolorview;
    private View mtogglenot;

    private Switch bnot;
    private boolean notification_state;


    static HomeFragmentJadid newInstance(ImageView imv, TextView tv, int mbc, int nb) {
        imvofappclicked = imv;
        tvofappclicked = tv;// in va balayi ash faghat agar az dialoge entekhabe app bargardim
        //null nakhahand bood.

        bc = mbc;//faghat agar az dialoge entekhabe app bargardim
        //0 nakhahad bood.

        kelidsnumber = nb;//faghat agar az dialoge entekhabe tedade kelid ha bargardim
        //-1 nakhahad bood.


        //dar tamame mavared , in fragment ba estefade az in methode newInstance(ImageView,TextView)
        //sakhte mishavad . vaghti baraye avalin bar sakhte mishavad ba null por mishavad
        //va vaghti ke az dialog be an bar migardim ba etelaate haghighi porash mikonim.


        HomeFragmentJadid f = new HomeFragmentJadid();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.home_layout_jadid, container, false);

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
                    service_notify(notification_state, fapps, number_of_app_buttons);
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
        number_of_app_buttons = getActivity().getSharedPreferences("numberofappbuttons", 0);

        //dar zamani ke app baraye
        // avalin bar ejra mishavad in khat mostaede
        //error ast.

        fapps = getActivity().getSharedPreferences("apps", 0);//va hamchenin in khat.
        isnotifon = getActivity().getSharedPreferences("notifswitch", 0);

        buttons_inflater = inflater;

        noabview.setOnClickListener(this);
        bgcolorview.setOnClickListener(this);
        mtogglenot.setOnClickListener(this);
        loading_the_app_buttons_in_main_ui();
        preconfigbuttons();


        return (result);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //  mListener = (listenerfordialog) activity;
            mnoabListener = (listenerfornoab) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    private void preconfigbuttons() { //this function , shows the apps which are chosen
        // earlier in the row in app's main page.
        if ((imvofappclicked == null) || (tvofappclicked == null)) {
            //az dialog bar nagashteim . ya application ra baraye avalin bar baz karde ast
            //va ya dar drawer elemane home ra zade ast.

            appbuttonloading();
        } else {
            // bayad akse morede nazar dar haman kelidi ke user click karde ast load shavad.


            switch (bc) {
                //bc shomareye buttoni ast ke click shode ast.


                //here:we adopt this function for 8 cases.
                case 1:
                    ab1.setBackgroundDrawable(imvofappclicked.getDrawable());
                    updateFavoriteApps("a", tvofappclicked.getText().toString());//first parameter is the key.
                    break;

                case 2:
                    ab2.setBackgroundDrawable(imvofappclicked.getDrawable());
                    updateFavoriteApps("b", tvofappclicked.getText().toString());

                    break;

                case 3:
                    ab3.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    updateFavoriteApps("c", tvofappclicked.getText().toString());
                    break;

                case 4:
                    ab4.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    updateFavoriteApps("d", tvofappclicked.getText().toString());
                    break;

                case 5:
                    ab5.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    updateFavoriteApps("e", tvofappclicked.getText().toString());
                    break;

                case 6:
                    ab6.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    updateFavoriteApps("f", tvofappclicked.getText().toString());
                    break;

                case 7:
                    ab7.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    updateFavoriteApps("g", tvofappclicked.getText().toString());
                    break;

                default:
                    Toast.makeText(getActivity().getApplicationContext(), "default happened!!!", Toast.LENGTH_LONG).show();

                    break;


            }
            appbuttonloading();
            imvofappclicked = null;
            tvofappclicked = null;


        }

    }//end of the preconfigbuttons() method.


    private void appbuttonloading() {
        try {
            String[] favoriteapps = fapps.getAll().keySet().toArray(new String[0]);
            Arrays.sort(favoriteapps, String.CASE_INSENSITIVE_ORDER);
            int noab = number_of_app_buttons.getInt(write_key, 0);
            if (noab > 0) {
                for (int i = 0; i < noab; i++) {
                    favoriteapps[i] = fapps.getString(favoriteapps[i], "");
                }
            } else {

                Toast.makeText(getActivity().getApplicationContext(), "first for app running", Toast.LENGTH_LONG).show();

            }
            ab1.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[0]));
            ab2.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[1]));
            ab3.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[2]));
            ab4.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[3]));
            ab5.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[4]));
            ab6.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[5]));
            ab7.setBackgroundDrawable(mPm.getApplicationIcon(favoriteapps[6]));
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.toString() + "in the preconfigbuttons", Toast.LENGTH_LONG).show();


        }
    }//end of appbuttonloading().


    private void service_notify(boolean notify_user, SharedPreferences user_favorite_apps, SharedPreferences number_of_app_buttons) {


        try {
            //ma dar inja service ra baraye sakhtan notification seda mizanim
            //bayad hamrah ba intent yek seri etelaat barash befrestim ta befahme notification ra besazad ya na.


            //	here : depending on number_app_buttons which is in the shared preferences,
            //we send an int for service to build different numbers of app buttons in the notification.
            //

            String[] favoriteapps =
                    user_favorite_apps.getAll().keySet().toArray(new String[0]);
            Arrays.sort(favoriteapps, String.CASE_INSENSITIVE_ORDER);


            int number_app_buttons = number_of_app_buttons.getInt(write_key, 0);

            for (int i = 0; i < number_app_buttons; i++) {


                favoriteapps[i] = user_favorite_apps.getString(favoriteapps[i], "");

            }

            //////////////////////////////////////////////////////////////////////////////////////////////
            RemoteViews remote;
            switch (number_app_buttons) {
                case 1:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify1);
                    break;

                case 2:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify2);
                    break;

                case 3:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify3);
                    break;

                case 4:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify4);
                    break;

                case 5:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify5);
                    break;

                case 6:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify6);
                    break;

                case 7:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify7);
                    break;
                default:
                    remote = new RemoteViews(getActivity().getPackageName(), R.layout.notify7);
                    break;
            }


            //////////////////////////////////////////////////////////////////////////////////////////////
            Intent notification_intent = new Intent(getActivity(), NotificationService.class);

            notification_intent.putExtra("ufa", favoriteapps);
            notification_intent.putExtra("notifyuser", notify_user);//doesn't have any utility for now.
            notification_intent.putExtra("viewgroup", remote);
            notification_intent.putExtra("numberofappbuttons", number_app_buttons);


            getActivity().startService(notification_intent);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.toString() + "in the service_notify", Toast.LENGTH_LONG).show();


        }

    }//end of service_notify.

    private void showlist(int bc) {


        mnoabListener.noabmethod(1, bc);
    }


    protected void updateFavoriteApps(String i, String s) {
        //az in method dar listenere mazkoor dar bala estefade mishavad.
        //updates the shared preferences which contain the favorite apps package names.

        SharedPreferences.Editor fappseditor = fapps.edit();


        //here:no changes would be necessary for now

        fappseditor.putString(i, s);

        fappseditor.commit();

        if (notification_state) {
            service_notify(notification_state, fapps, number_of_app_buttons);
            //agar notification ghablan neshan dade shode bashad , an ra update mikonim.
        }
    }//end of updateFavoriteApps.

    //TODO bayad code namayesh dadane dialogi ke az karbar tedade kelid ha(app button ha) ra miporsad
    //be in kelas ezafe konam
    //TODO va hamchenin bayad yek kelas digar ke an dialoge kelid ha ra neshan midahad ra bayad besazam.


    private void loading_the_app_buttons_in_main_ui() {

        if (kelidsnumber != -1) {
            SharedPreferences.Editor noabeditor = number_of_app_buttons.edit();
            noabeditor.putInt(write_key, kelidsnumber);
            noabeditor.commit();
        }

        try {

            int m = number_of_app_buttons.getInt(write_key, 0);
            switch (m) {
                case 1:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_1_button, null);
                    ab1 = (Button) buttons_row.findViewById(R.id.button1);
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
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
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
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
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
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
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
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
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
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = (Button) buttons_row.findViewById(R.id.button6);
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
                    ab1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 1;
                            showlist(bc);
                        }
                    });

                    ab2 = (Button) buttons_row.findViewById(R.id.button2);
                    ab2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 2;
                            showlist(bc);
                        }
                    });


                    ab3 = (Button) buttons_row.findViewById(R.id.button3);
                    ab3.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 3;
                            showlist(bc);
                        }
                    });


                    ab4 = (Button) buttons_row.findViewById(R.id.button4);
                    ab4.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 4;
                            showlist(bc);
                        }
                    });

                    ab5 = (Button) buttons_row.findViewById(R.id.button5);
                    ab5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 5;
                            showlist(bc);
                        }
                    });

                    ab6 = (Button) buttons_row.findViewById(R.id.button6);
                    ab6.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 6;
                            showlist(bc);
                        }
                    });

                    ab7 = (Button) buttons_row.findViewById(R.id.button7);
                    ab7.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            bc = 7;
                            showlist(bc);
                        }
                    });


                    break;

                default:
                    buttons_row = (LinearLayout) buttons_inflater.inflate(R.layout.apps_7_button, null);//it must be coded this way.

                    //  Toast.makeText(getActivity().getApplicationContext(), "the default happened!!!", Toast.LENGTH_LONG).show();
                    //felan in khat ro gheyr faalkardam.


                    break;
            }

            buttonsholder.removeAllViews();
            buttonsholder.addView(buttons_row);

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.toString() + "in the loading_the_app_buttons_in_main_ui", Toast.LENGTH_LONG).show();


        }

        ///////////////////////////////////////////////////////
        //inja switch marboot be notification ra meghdar dehi mikonim.
        boolean isnotifyon = isnotifon.getBoolean(write_key_notif, false);
        if (isnotifyon) {
            bnot.setChecked(true);
        } else {
            bnot.setChecked(false);
        }
        ///////////////////////////////////////////////////////


    }//end of loading_the_app_buttons_in_main_ui.

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


    public interface listenerfornoab {
        void noabmethod(int dialognumber, int bc);
        //bc !=0 yani inke bayad app haye daroone dastgah ra neshan bedahim.
        //dialognumber=0  dialogi ke tedade kelid haye daroone safhe asli ra az karbar miporsad.
        //dialognumber=1 dialogi ke app haye nasb shode dar dastgah ra neshan midahad.
        //dialognumber=2 dialogi ke background color ra miporsad.
    }

}
