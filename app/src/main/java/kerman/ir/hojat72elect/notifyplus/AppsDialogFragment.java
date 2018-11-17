package kerman.ir.hojat72elect.notifyplus;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by hojat_ghasemi on 16 March 2017 in kerman.
 */
public class AppsDialogFragment extends DialogFragment {

    dialogclicked mlistener;
    private ApkListAdapter apkListAdapter;
    private ProgressBar progressBar;
    //    TableLayout appsTableLayout;
//    TextView apppackagename;
//    TextView appname;
//    ImageView appicon;
//    AsyncTask at;
//    LayoutInflater infla;
//    ScrollView sv;
//    View backlayout;
    private View v;

    static AppsDialogFragment newInstance() {
        AppsDialogFragment f = new AppsDialogFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        String[] a = {"a", "s"};
//        infla = inflater;
        v = inflater.inflate(R.layout.maindialog, container, false);


        RecyclerView listView = (RecyclerView) v.findViewById(android.R.id.list);

        apkListAdapter = new ApkListAdapter((MainActivity) getActivity(), this);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(apkListAdapter);

        progressBar = (ProgressBar) v.findViewById(android.R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) v.findViewById(R.id.searchView1);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused && searchView.getQuery().length() < 1) {
                    //bayad bebandamesh
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                apkListAdapter.setSearchPattern(s);
                return true;
            }
        });
//        sv = (ScrollView) v.findViewById(R.id.queryScrollView);
//        at = new LoadHome();
//        at.execute(a);
//        appsTableLayout.setVisibility(View.INVISIBLE);
//
//        ////////////////////////////////////
//        backlayout = v.findViewById(R.id.backlayout);
//        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "kidfont.ttf");
//        TextView backlayouttext = (TextView) v.findViewById(R.id.bazgasht);
//        backlayouttext.setTypeface(iransanserif);
//
//        /////////////////////////////////////
        new Loader(this).execute();

        return v;
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void addItem(ApplicationInfo item) {
        apkListAdapter.addItem(item);

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

    public void backtomainactivity(ImageView imv,TextView tv) {
        mlistener.ondialogclick(imv, tv);
        getDialog().cancel();//dismisses the dialog.
    }

    public interface dialogclicked {
        void ondialogclick(ImageView imv, TextView tv);
    }

    class Loader extends AsyncTask<Void, ApplicationInfo, Void> {
        ProgressDialog dialog;
        AppsDialogFragment adf;

        public Loader(AppsDialogFragment a) {
            dialog = ProgressDialog.show(getActivity(), getString(R.string.dlg_loading_title), getString(R.string.dlg_loading_body));
            adf = a;
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<ApplicationInfo> packages = getActivity().getPackageManager().getInstalledApplications(0);
            for (ApplicationInfo packageInfo : packages) {
                if ((getActivity().getPackageManager().getLaunchIntentForPackage(packageInfo.packageName)) != null) {
                    publishProgress(packageInfo);
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ApplicationInfo... values) {
            super.onProgressUpdate(values);
            adf.addItem(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }

}






/*

    private class LoadHome extends AsyncTask<String, Integer, View> {


        ///////////////////////////////////

        private View.OnClickListener approwlistener = new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ImageView imv = (ImageView) v.findViewById(R.id.appicon);
                TextView tv = (TextView) v.findViewById(R.id.apppackagename);
                mlistener.ondialogclick(imv, tv);
                getDialog().cancel();//dismisses the dialog.

            }
        };

        /////////////////////////////////////////

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity().getApplicationContext(), "اندکی صبر کنید!", Toast.LENGTH_SHORT).show();

            appsTableLayout = (TableLayout) infla.inflate(R.layout.tablelayoutview, sv, false);


        }

        @Override
        protected View doInBackground(String... params) {


            new Thread(new Runnable() {
                @Override
                public void run() {

                    int k = 0;
                    Context c = getActivity().getApplicationContext();
                    PackageManager mPm = c.getPackageManager();
                    List<ApplicationInfo> apps = mPm.getInstalledApplications(0);
                    int j = 0;

                    for (int i = 0; i < apps.size(); i++) {
                        //ls.add(apps.get(i).packageName);//this line of code throws an exception
                        if (mPm.getLaunchIntentForPackage(apps.get(i).packageName) != null) {
                            j++;
                        }
                    }

                    String[] mashin = new String[j];
                    for (int i = 0; i < apps.size(); i++) {

                        if (mPm.getLaunchIntentForPackage(apps.get(i).packageName) != null) {
                            mashin[k] = apps.get(i).packageName;
                            k++;
                        }
                    }


                    for (int i = 0; i < k; i++) {

                        final View approw = infla.inflate(R.layout.appnameandicon, null);//1
                        apppackagename = (TextView) approw.findViewById(R.id.apppackagename);//3
                        appicon = (ImageView) approw.findViewById(R.id.appicon);//4
                        apppackagename.setText(mashin[i]);
                        appname = (TextView) approw.findViewById(R.id.appname);

                        try {

                            appicon.setImageDrawable(mPm.getApplicationIcon(mashin[i]));
                            appname.setText(mPm.getApplicationLabel(mPm.getApplicationInfo(mashin[i], PackageManager.GET_META_DATA)));

                        } catch (Exception e) {

                        }

                        approw.setOnClickListener(approwlistener);

                        final int finalP = i;
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                appsTableLayout.addView(approw, finalP);//this one must come at the end.
                            }
                        });

                        if (i == k - 1) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    appsTableLayout.setVisibility(View.VISIBLE);

                                    backlayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getDialog().cancel();
                                        }
                                    });

                                }
                            });
                        }

                    }


                }
            }).start();


            return appsTableLayout;

        }

        /////////////////////////////////////////////

        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
        }

        protected void onPostExecute(View result) {
            super.onPostExecute(result);

            sv.removeAllViews();
            sv.addView(result);

        }


    }
*/

