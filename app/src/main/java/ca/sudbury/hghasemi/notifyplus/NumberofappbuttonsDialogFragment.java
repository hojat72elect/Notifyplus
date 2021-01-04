package ca.sudbury.hghasemi.notifyplus;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by Hojat_Ghasemi on Saturday , 1 April 2017 in kerman.
 */
public class NumberofappbuttonsDialogFragment extends DialogFragment {

    AppCompatButton b1;
    AppCompatButton b2;
    AppCompatButton b3;
    AppCompatButton b4;
    AppCompatButton b5;
    AppCompatButton b6;
    AppCompatButton b7;
    AppCompatButton b8;

    View backlayout;
    buttonclicked mbuttonlistener;

    static NumberofappbuttonsDialogFragment newInstance() {

        return new NumberofappbuttonsDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogbuttons, container, false);


        backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        b1 = v.findViewById(R.id.mb1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(1);
            }
        });

        b2 = v.findViewById(R.id.mb2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtohomefragmentjadid(2);
            }
        });

        b3 = v.findViewById(R.id.mb3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(3);
            }
        });

        b4 = v.findViewById(R.id.mb4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(4);
            }
        });

        b5 = v.findViewById(R.id.mb5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(5);
            }
        });

        b6 = v.findViewById(R.id.mb6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(6);
            }
        });

        b7 = v.findViewById(R.id.mb7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(7);
            }
        });

        b8 = v.findViewById(R.id.mb8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backtohomefragmentjadid(8);
            }
        });

        //////////////////////////////////////////////////////////////
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "kidfont.ttf");

        TextView backlayouttext = v.findViewById(R.id.bazgasht);
        backlayouttext.setTypeface(iransanserif);


        /////////////////////////////////////////////////////////////


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


    public interface buttonclicked {
        void dialogbuttonclicked(int nbc);

        //ma ba in interface be HomeFragmentJadid bar khahim gasht.
    }


}
