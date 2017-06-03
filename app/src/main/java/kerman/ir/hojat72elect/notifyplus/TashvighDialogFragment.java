package kerman.ir.hojat72elect.notifyplus;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kerman.ir.hojat72elect.notifyplus.views.ButtonRectangle;

/**
 * Created by hojat72elect on panjshanbe 11 khordad 1396 in kerman .
 */
public class TashvighDialogFragment extends DialogFragment implements View.OnClickListener {

    ButtonRectangle kharidb;
    ButtonRectangle bkhialb;
    View backlayout;

    static TashvighDialogFragment newInstance() {

        return new TashvighDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tashvigh_dialog_fragment, container, false);
        kharidb = (ButtonRectangle) v.findViewById(R.id.bkharid);
        bkhialb = (ButtonRectangle) v.findViewById(R.id.bkhial);
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
                intent.setData(Uri.parse("bazaar://details?id=" + "kerman.ir.hojat72elect.notifyplus.pro"));
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
