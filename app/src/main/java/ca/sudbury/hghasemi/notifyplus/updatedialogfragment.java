package ca.sudbury.hghasemi.notifyplus;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * Created by hojat_ghasemi on Thursday , 25 May 2017 in kerman.
 */
public class updatedialogfragment extends DialogFragment implements View.OnClickListener {

    AppCompatButton updateb;
    AppCompatButton cancelb;
    View backlayout;
    TextView bazgasht;
    TextView t1;

    static updatedialogfragment newInstance() {

        return new updatedialogfragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.update_dialog_fragment, container, false);
        updateb = v.findViewById(R.id.bupdate);
        cancelb = v.findViewById(R.id.bcancel);
        backlayout = v.findViewById(R.id.backlayout);
        bazgasht = v.findViewById(R.id.bazgasht);
        t1 = v.findViewById(R.id.t1);
        updateb.setOnClickListener(this);
        cancelb.setOnClickListener(this);
        backlayout.setOnClickListener(this);
        Typeface iransanserif = Typeface.createFromAsset(requireActivity().getAssets(), "kidfont.ttf");
        bazgasht.setTypeface(iransanserif);
        t1.setTypeface(iransanserif);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == updateb) {
            // be safheye barname beravid.
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("bazaar://details?id=" + "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"));
                intent.setPackage("com.farsitel.bazaar");
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(requireActivity().getApplicationContext(), "مشکل در اتصال به سرور کافه بازار", Toast.LENGTH_LONG).show();

            }


        } else if ((v == cancelb) || (v == backlayout)) {
            Objects.requireNonNull(getDialog()).cancel();
        }
    }
}
