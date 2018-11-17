package kerman.ir.hojat72elect.notifyplus;

import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hojat_ghasemi on Tuesday , 18 April 2017 in kerman .
 */
public class HelpDialogfragment extends DialogFragment {

    TextView am1;
    TextView am2;
    TextView am3;
    TextView am4;

    TextView bazgasht;

    View backlayout;

    static HelpDialogfragment newInstance() {

        return new HelpDialogfragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.help_dialog_fragment, container, false);

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);

        am1 = (TextView) v.findViewById(R.id.am1);
        am2 = (TextView) v.findViewById(R.id.am2);
        am3 = (TextView) v.findViewById(R.id.am3);
        am4 = (TextView) v.findViewById(R.id.am4);

        bazgasht = (TextView) v.findViewById(R.id.bazgasht);
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "kidfont.ttf");

        am1.setTypeface(iransanserif);
        am2.setTypeface(iransanserif);
        am3.setTypeface(iransanserif);
        am4.setTypeface(iransanserif);

        bazgasht.setTypeface(iransanserif);

        backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });


        return v;
    }

}
