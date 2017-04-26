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
 * Created by hojat72elect on seshanbe 29 farvardin 1396 in kerman .
 */
public class HelpDialogfragment extends DialogFragment implements View.OnClickListener{

    TextView am1;
    TextView am2;
    TextView am3;
    TextView am4;
    TextView am5;
    TextView bazgasht;

    View backlayout;

    static HelpDialogfragment newInstance() {

        return new HelpDialogfragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.help_dialog_fragment, container, false);

        am1 = (TextView) v.findViewById(R.id.am1);
        am2 = (TextView) v.findViewById(R.id.am2);
        am3 = (TextView) v.findViewById(R.id.am3);
        am4 = (TextView) v.findViewById(R.id.am4);
        am5 = (TextView) v.findViewById(R.id.am5);
        bazgasht = (TextView) v.findViewById(R.id.bazgasht);
        backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(this);
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "Arabicgithub.ttf");

        am1.setTypeface(iransanserif);
        am2.setTypeface(iransanserif);
        am3.setTypeface(iransanserif);
        am4.setTypeface(iransanserif);
        am5.setTypeface(iransanserif);
        bazgasht.setTypeface(iransanserif);

        return v;
    }

    @Override
    public void onClick(View v) {
        getDialog().cancel();
    }
}
