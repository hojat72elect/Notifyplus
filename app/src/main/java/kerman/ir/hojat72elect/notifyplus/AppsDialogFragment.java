package kerman.ir.hojat72elect.notifyplus;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by hojat72elect on 26 esfand 1395 in kerman.
 */
public class AppsDialogFragment extends DialogFragment implements View.OnClickListener {

    int j = 0;//it saves the number of apps which are in good condition of launching.
    private List<ApplicationInfo> apps;
    private PackageManager mPm;
    private Context c;
    LayoutInflater infla;
    View mdialog;
    dialogclicked mlistener;

    static AppsDialogFragment newInstance() {
        AppsDialogFragment f = new AppsDialogFragment();
        return f;
    }

    public interface dialogclicked {
        void ondialogclick(ImageView imv, TextView tv);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        c = getActivity().getApplicationContext();
        mPm = c.getPackageManager();//this line throws Nullpointer exception.
        View v = inflater.inflate(R.layout.maindialog, container, false);
        mdialog = v;
        infla = inflater;
        apps = mPm.getInstalledApplications(0);

        for (int i = 0; i < apps.size(); i++) {
            //ls.add(apps.get(i).packageName);//this line of code throws an exception
            if (mPm.getLaunchIntentForPackage(apps.get(i).packageName) != null) {
                j++;
            }
        }

        String[] mashin = new String[j];
        int k = 0;
        for (int i = 0; i < apps.size(); i++) {

            if (mPm.getLaunchIntentForPackage(apps.get(i).packageName) != null) {
                mashin[k] = apps.get(i).packageName;
                k++;
            }
        }

        for (int i = 0; i < k; i++) {
            addapprow(mashin[i], i);
        }

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

    private void addapprow(String s, int i) {
        //this function does the adding of each row of table layout for us.
        View approw = infla.inflate(R.layout.appnameandicon, null);
        TableLayout appsTableLayout = (TableLayout) mdialog.findViewById(R.id.tableLayout);

        TextView apppackagename = (TextView) approw.findViewById(R.id.apppackagename);
        ImageView appicon = (ImageView) approw.findViewById(R.id.appicon);


        apppackagename.setText(s);

        try {
            appicon.setImageDrawable(mPm.getApplicationIcon(s));
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "error occured in creating the list!", Toast.LENGTH_LONG).show();

        }

        approw.setOnClickListener(this);
        appsTableLayout.addView(approw, i);//this one must come at the end.


    }


    @Override
    public void onClick(View v) {
//this one listens for click on each row of  the list.
// hala bayad dar in method , in fragment elemane entekhab shode tavasote karbar ra be activity bedahad.


        ImageView imv = (ImageView) v.findViewById(R.id.appicon);
        TextView tv = (TextView) v.findViewById(R.id.apppackagename);
        mlistener.ondialogclick(imv, tv);
        getDialog().cancel();//dismisses the dialog.
    }
}
