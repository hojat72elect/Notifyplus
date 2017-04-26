package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by hojat72elect on doshanbe 20 farvardin 1396 .
 */
public class BackgroundColorDialogFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    buttonclicked mbuttonlistener;
    Button setColorButton;
    TextView tvshedat;
    TextView tvred;
    TextView tvgreen;
    TextView tvblue;
    TextView bazgasht;
    View backlayout;
    private SeekBar blueSeekBar;
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private View rangpallete;

    static BackgroundColorDialogFragment newInstance() {

        return new BackgroundColorDialogFragment();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        rangpallete.setBackgroundColor(Color.argb(
                alphaSeekBar.getProgress(), redSeekBar.getProgress(),
                greenSeekBar.getProgress(), blueSeekBar.getProgress()));
    }// end method onProgressChanged

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //  method onStartTrackingTouch
        // required method of interface OnSeekBarChangeListener
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // end method onStopTrackingTouch
        // required method of interface OnSeekBarChangeListener
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.background_color_dialog, container, false);

        backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(this);
        tvshedat = (TextView) v.findViewById(R.id.shedatrang);
        tvred = (TextView) v.findViewById(R.id.ghermez);
        tvgreen = (TextView) v.findViewById(R.id.sabz);
        tvblue = (TextView) v.findViewById(R.id.abi);
        bazgasht = (TextView) v.findViewById(R.id.bazgasht);

        alphaSeekBar = (SeekBar) v.findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) v.findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) v.findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) v.findViewById(R.id.blueSeekBar);
        rangpallete = v.findViewById(R.id.colorView);

        alphaSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        alphaSeekBar.setProgress(255);
        redSeekBar.setProgress(120);
        greenSeekBar.setProgress(120);
        blueSeekBar.setProgress(120);

        rangpallete.setBackgroundColor(Color.argb(
                alphaSeekBar.getProgress(),
                redSeekBar.getProgress(),
                greenSeekBar.getProgress(),
                blueSeekBar.getProgress()));


        setColorButton = (Button) v.findViewById(R.id.setColorButton);
        setColorButton.setOnClickListener(this);
        ///////////////////////////////////////////////////////////////////////////
        //dar inja font ra emal mikonim.
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "Arabicgithub.ttf");
        setColorButton.setTypeface(iransanserif);
        tvshedat.setTypeface(iransanserif);
        tvred.setTypeface(iransanserif);
        tvgreen.setTypeface(iransanserif);
        tvblue.setTypeface(iransanserif);
        bazgasht.setTypeface(iransanserif);
        ////////////////////////////////////////////////////////////////////////////

        return v;
    }//end of onCreateView().

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mbuttonlistener = (buttonclicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == setColorButton) {
            mbuttonlistener.rangdialogclicked(Color.argb(
                    alphaSeekBar.getProgress(),
                    redSeekBar.getProgress(),
                    greenSeekBar.getProgress(),
                    blueSeekBar.getProgress()));
            getDialog().cancel();//dismisses the dialog.
        } else if (v == backlayout) {
            getDialog().cancel();
        }
    }


    public interface buttonclicked {
        void rangdialogclicked(int color);

        //ma ba in interface be HomeFragmentJadid bar khahim gasht.
    }

}
