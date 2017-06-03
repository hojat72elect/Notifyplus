package kerman.ir.hojat72elect.notifyplus;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import kerman.ir.hojat72elect.notifyplus.views.ButtonRectangle;

/**
 * Created by hojat72elect on panjshanbe 4 khordad 1396 in kerman.
 */
public class updatedialogfragment extends DialogFragment implements View.OnClickListener {

    ButtonRectangle updateb;
    ButtonRectangle cancelb;
    View backlayout;
    TextView bazgasht;
    TextView t1;

    static updatedialogfragment newInstance() {

        return new updatedialogfragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.update_dialog_fragment, container, false);
        updateb = (ButtonRectangle) v.findViewById(R.id.bupdate);
        cancelb = (ButtonRectangle) v.findViewById(R.id.bcancel);
        backlayout = v.findViewById(R.id.backlayout);
        bazgasht = (TextView) v.findViewById(R.id.bazgasht);
        t1 = (TextView) v.findViewById(R.id.t1);
        updateb.setOnClickListener(this);
        cancelb.setOnClickListener(this);
        backlayout.setOnClickListener(this);
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "Arabicgithub.ttf");
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
                intent.setData(Uri.parse("bazaar://details?id=" + "kerman.ir.hojat72elect.notifyplus"));
                intent.setPackage("com.farsitel.bazaar");
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "مشکل در اتصال به سرور کافه بازار", Toast.LENGTH_LONG).show();

            }


        } else if ((v == cancelb) || (v == backlayout)) {
            getDialog().cancel();
        }
    }
}
