package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kerman.ir.hojat72elect.notifyplus.utils.ColorPalette;
import kerman.ir.hojat72elect.notifyplus.uz.shift.colorpicker.LineColorPicker;
import kerman.ir.hojat72elect.notifyplus.uz.shift.colorpicker.OnColorChangedListener;

/**
 * Created by hojat72elect on doshanbe 20 farvardin 1396 .
 */
public class BackgroundColorDialogFragment extends DialogFragment implements View.OnClickListener {

    buttonclicked mbuttonlistener;
    Button setColorButton;
    View backlayout;
    private View rangpallete;
    private LineColorPicker colorPicker;
    private LineColorPicker colorPicker2;


    static BackgroundColorDialogFragment newInstance() {

        return new BackgroundColorDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.background_color_dialog_jadid, container, false);


        backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        rangpallete = v.findViewById(R.id.colorView);
        colorPicker = (LineColorPicker) v.findViewById(R.id.pickerPrimary);
        colorPicker2 = (LineColorPicker) v.findViewById(R.id.pickerPrimary2);

        // set color palette
        colorPicker.setColors(ColorPalette.getBaseColors(getActivity().getApplicationContext()));
        colorPicker2.setColors(ColorPalette.getColors(getActivity().getApplicationContext(), colorPicker.getColor()));
        rangpallete.setBackgroundColor(colorPicker2.getColor());

        // set on change listener
        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                //c is the selected color
                // rang ra be view emal konid.

                rangpallete.setBackgroundColor(colorPicker.getColor());
                colorPicker2.setColors(ColorPalette.getColors(getActivity().getApplicationContext(), colorPicker.getColor()));
                colorPicker2.setSelectedColor(colorPicker.getColor());
            }
        });

        colorPicker2.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                rangpallete.setBackgroundColor(colorPicker2.getColor());
            }
        });


        // get selected color
        int color = colorPicker.getColor();


      /*  rangpallete.setBackgroundColor(Color.argb(
                alphaSeekBar.getProgress(),
                redSeekBar.getProgress(),
                greenSeekBar.getProgress(),
                blueSeekBar.getProgress()));*/


        setColorButton = (Button) v.findViewById(R.id.setColorButton);
        setColorButton.setOnClickListener(this);
        ///////////////////////////////////////////////////////////////////////////
        //dar inja font ra emal mikonim.
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
            mbuttonlistener.rangdialogclicked(colorPicker2.getColor());
            getDialog().cancel();//dismisses the dialog.

        }
    }


    public interface buttonclicked {
        void rangdialogclicked(int color);

        //ma ba in interface be HomeFragmentJadid bar khahim gasht.
    }

}
