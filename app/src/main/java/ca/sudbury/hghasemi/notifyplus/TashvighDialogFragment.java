package ca.sudbury.hghasemi.notifyplus;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ca.sudbury.hghasemi.notifyplus.views.ButtonRectangle;

/**
 * Created by hojat_ghasemi on Thursday , 1 June 2017 in kerman .
 */
public class TashvighDialogFragment extends DialogFragment implements View.OnClickListener {

    ButtonRectangle kharidb;
    ButtonRectangle bkhialb;
    View backlayout;

    static TashvighDialogFragment newInstance() {

        return new TashvighDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tashvigh_dialog_fragment, container, false);
        kharidb = v.findViewById(R.id.bkharid);
        bkhialb = v.findViewById(R.id.bkhial);
        backlayout = v.findViewById(R.id.backlayout);
        kharidb.setOnClickListener(this);
        bkhialb.setOnClickListener(this);
        backlayout.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == kharidb) {
            try {
                //be safhe barname pooli miravim.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("bazaar://details?id=" + "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus.pro"));
                intent.setPackage("com.farsitel.bazaar");
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "مشکل در اتصال به سرور کافه بازار", Toast.LENGTH_LONG).show();

            }


        } else if ((v == bkhialb) || (v == backlayout)) {
            getDialog().cancel();
        }

    }

}
