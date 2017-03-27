package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


/**
 * Created by hojat72elect on doshanbe 23 esfand 1395 in kerman.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    listenerfordialog mListener;
    RadioGroup rg = null;//the radio group that enabes user to choose number of apps he/she likes to be shown.
    LayoutInflater buttons_inflater;
    private Button bnoton;
    private Button bnotoff;
    private Button ab1;
    private Button ab2;
    private Button ab3;
    private Button ab4;
    private Button ab5;
    private Button ab6;
    private Button ab7;
    private LinearLayout buttonsholder;//linear layout that contains the linear layout with buttons.
    private LinearLayout buttons_row;//linear layout with the buttons inside of it.
    private String write_key = "noab";//the key for writing on the shared preferences that contains the number of app buttons.

    private SharedPreferences fapps; //shared preferences that saves user's favorite apps.
    private SharedPreferences number_of_app_buttons; //shared preferences for saving the number of app buttons.
    private PackageManager mPm;
    private Context c;
    private boolean notification_state;

    private static ImageView imvofappclicked;
    private static TextView tvofappclicked;
    private static int bc;// the number of button which is clicked.

    static HomeFragment newInstance(ImageView imv, TextView tv, int mbc) {
        imvofappclicked = imv;
        tvofappclicked = tv;
        bc = mbc;
        //dar tamame mavared , in fragment ba estefade az in methode newInstance(ImageView,TextView)
        //sakhte mishavad . vaghti baraye avalin bar sakhte mishavad ba null por mishavad
        //va vaghti ke az dialog be an bar migardim ba etelaate haghighi porash mikonim.


        HomeFragment f = new HomeFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.home_layout, container, false);


        buttonsholder = (LinearLayout) result.findViewById(R.id.buttonsholder);

        rg = (RadioGroup) result.findViewById(R.id.radioGroup1);
        rg.setOnCheckedChangeListener(this);
        c = getActivity().getApplicationContext();
        mPm = c.getPackageManager();
        bnoton = (Button) result.findViewById(R.id.button1);
        bnoton.setOnClickListener(this);
        bnotoff = (Button) result.findViewById(R.id.button2);
        bnotoff.setOnClickListener(this);


        number_of_app_buttons = getActivity().getSharedPreferences("numberofappbuttons", 0);//dar zamani ke app baraye
        // avalin bar ejra mishavad in khat mostaede
        //error asr.
        fapps = getActivity().getSharedPreferences("apps", 0);//va hamchenin in khat.

        buttons_inflater = inflater;

        loading_the_app_buttons_in_main_ui();
        preconfigbuttons();
        return (result);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (listenerfordialog) activity;
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
    }

    @Override
    public void onClick(View v) {


        if (v == bnoton) {
            notification_state = true;
            service_notify(notification_state, fapps, number_of_app_buttons);


            //starting the notification with the shared preferences we have


        } else if (v == bnotoff) {
            notification_state = false;
            getActivity().stopService(new Intent(getActivity(), NotificationService.class));

            //stoping the  whole notification thing.

        }


    }

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

        mListener.ondialogshow(bc);
    }

    protected void updateFavoriteApps(String i, String s) {
        //az in method dar listenere mazkoor dar bala estefade mishavad.
        //updates the shared preferences which contain the favorite apps package names.

        SharedPreferences.Editor fappseditor = fapps.edit();


        //here:no changes would be necessary for now

        fappseditor.putString(i, s);

        fappseditor.commit();

          if(notification_state){
        service_notify(notification_state, fapps, number_of_app_buttons);
        //agar notification ghablan neshan dade shode bashad , an ra update mikonim.
         }
    }//end of updateFavoriteApps.

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        SharedPreferences.Editor noabeditor = number_of_app_buttons.edit();


        switch (checkedId) {
            case R.id.radio0:
                noabeditor.putInt(write_key, 1);
                break;

            case R.id.radio1:

                noabeditor.putInt(write_key, 2);
                break;

            case R.id.radio2:
                noabeditor.putInt(write_key, 3);

                break;

            case R.id.radio3:

                noabeditor.putInt(write_key, 4);
                break;

            case R.id.radio4:

                noabeditor.putInt(write_key, 5);
                break;

            case R.id.radio5:
                noabeditor.putInt(write_key, 6);

                break;

            case R.id.radio6:

                noabeditor.putInt(write_key, 7);
                break;


            default:

                break;
        }
        noabeditor.commit();
        //here we need a function for loading the keys to the app and notification
        loading_the_app_buttons_in_main_ui();

    }//end of onCheckedChanged().

    private void loading_the_app_buttons_in_main_ui() {

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

    }//end of loading_the_app_buttons_in_main_ui.


    public interface listenerfordialog {
        void ondialogshow(int bc);
    }


}
