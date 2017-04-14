package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hojat72elect on shanbe 12 farvardin 1396  , ferdousi blvd in my mother home.
 */
public class NumberofappbuttonsDialogFragment extends DialogFragment {

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    buttonclicked mbuttonlistener;


    public interface buttonclicked {
        void dialogbuttonclicked(int nbc);

        //ma ba in interface be HomeFragmentJadid bar khahim gasht.
    }


    static NumberofappbuttonsDialogFragment newInstance() {

        return new NumberofappbuttonsDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogbuttons, container, false);

        b1 = (Button) v.findViewById(R.id.mb1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(1);
            }
        });

        b2 = (Button) v.findViewById(R.id.mb2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtohomefragmentjadid(2);
            }
        });

        b3 = (Button) v.findViewById(R.id.mb3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(3);
            }
        });

        b4 = (Button) v.findViewById(R.id.mb4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(4);
            }
        });

        b5 = (Button) v.findViewById(R.id.mb5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(5);
            }
        });

        b6 = (Button) v.findViewById(R.id.mb6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(6);
            }
        });

        b7 = (Button) v.findViewById(R.id.mb7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(7);
            }
        });

        return v;
    }

    private void backtohomefragmentjadid(int mint) {

        mbuttonlistener.dialogbuttonclicked(mint);
        getDialog().cancel();//dismisses the dialog.

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mbuttonlistener = (buttonclicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


}
