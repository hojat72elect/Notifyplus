package kerman.ir.hojat72elect.notifyplus;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by hojat72elect on 26 esfand 1395 in kerman.
 */
public class AppsDialogFragment extends DialogFragment {


    dialogclicked mlistener;
    TableLayout appsTableLayout;
    TextView apppackagename;
    ImageView appicon;
    ProgressDialog pd;
    AsyncTask at;
    LayoutInflater infla;
    ScrollView sv;
    private View v;

    static AppsDialogFragment newInstance() {
        AppsDialogFragment f = new AppsDialogFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] a ={"a","s"};
        infla = inflater;
        v = inflater.inflate(R.layout.maindialog, container, false);
        appsTableLayout = (TableLayout) inflater.inflate(R.layout.tablelayoutview, container, false);
        sv = (ScrollView) v.findViewById(R.id.queryScrollView);
        pd = new ProgressDialog(getActivity());
        at = new LoadHome();
        at.execute(a);





        ////////////////////////////////////
        View backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "Arabicgithub.ttf");
        TextView backlayouttext = (TextView) v.findViewById(R.id.bazgasht);
        backlayouttext.setTypeface(iransanserif);

        /////////////////////////////////////


        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mlistener = (dialogclicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    public interface dialogclicked {
        void ondialogclick(ImageView imv, TextView tv);
    }


    private class LoadHome extends AsyncTask<String, Integer, View> {

        /////////////////////////////////////////////
        private View.OnClickListener approwlistener = new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ImageView imv = (ImageView) v.findViewById(R.id.appicon);
                TextView tv = (TextView) v.findViewById(R.id.apppackagename);
                mlistener.ondialogclick(imv, tv);
                getDialog().cancel();//dismisses the dialog.

            }
        };

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // initialize the dialog
            pd.setTitle("Loading...");
            pd.setMessage("Please wait while loading...");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();

        }

        @Override
        protected View doInBackground(String... params) {

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    Context c = getActivity().getApplicationContext();
                    PackageManager mPm = c.getPackageManager();

                    List<ApplicationInfo> apps = mPm.getInstalledApplications(0);
                    int p = 0;


                    for (int i = 0; i < apps.size(); i++) {
                        if (mPm.getLaunchIntentForPackage(apps.get(i).packageName) != null) {

                            View approw = infla.inflate(R.layout.appnameandicon, null);//1
                            apppackagename = (TextView) approw.findViewById(R.id.apppackagename);//3
                            appicon = (ImageView) approw.findViewById(R.id.appicon);//4
                            apppackagename.setText(apps.get(i).packageName);

                            try {
                                appicon.setImageDrawable(mPm.getApplicationIcon(apps.get(i)));
                            } catch (Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "error in creating the list!", Toast.LENGTH_LONG).show();
                            }

                            approw.setOnClickListener(approwlistener);
                            appsTableLayout.addView(approw, p);//this one must come at the end.

                            p++;
                        }
                    }


                }
            });


            // Toast.makeText(getActivity().getApplicationContext(), "dialog loaded", Toast.LENGTH_LONG).show();

            return appsTableLayout;

        }

        /////////////////////////////////////////////

        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
        }

        protected void onPostExecute(View result) {
            super.onPostExecute(result);
            pd.dismiss();
            sv.removeAllViews();
            sv.addView(result);

        }


    }


}

