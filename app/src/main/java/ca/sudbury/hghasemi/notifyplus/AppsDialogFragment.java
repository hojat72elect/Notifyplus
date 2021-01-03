package ca.sudbury.hghasemi.notifyplus;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        String[] a = {"a", "s"};
//        infla = inflater;
        v = inflater.inflate(R.layout.maindialog, container, false);


        RecyclerView listView = v.findViewById(android.R.id.list);

        apkListAdapter = new ApkListAdapter((MainActivity) getActivity(), this);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(apkListAdapter);

        progressBar = v.findViewById(android.R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) v.findViewById(R.id.searchView1);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    searchView.getQuery().length();
                }//bayad bebandamesh
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

    @SuppressLint("StaticFieldLeak")
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


