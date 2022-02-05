package ca.sudbury.hghasemi.notifyplus;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import ca.sudbury.hghasemi.notifyplus.utils.ColorPalette;
import uz.shift.colorpicker.LineColorPicker;

/**
 * Created by Hojat_Ghasemi on Monday , 9 April 2017 .
 */
public class BackgroundColorDialogFragment extends DialogFragment implements View.OnClickListener {

    buttonclicked mbuttonlistener;
    AppCompatButton setColorButton;
    View backlayout;
    private View rangpallete;
    private LineColorPicker colorPicker;
    private LineColorPicker colorPicker2;


    static BackgroundColorDialogFragment newInstance() {

        return new BackgroundColorDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.background_color_dialog_jadid, container, false);


        backlayout = v.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(v1 -> Objects.requireNonNull(getDialog()).cancel());
        rangpallete = v.findViewById(R.id.colorView);
        colorPicker = v.findViewById(R.id.pickerPrimary);
        colorPicker2 = v.findViewById(R.id.pickerPrimary2);

        // set color palette
        colorPicker.setColors(ColorPalette.getBaseColors(requireActivity().getApplicationContext()));
        colorPicker2.setColors(ColorPalette.getSecondaryColors(requireActivity().getApplicationContext(), colorPicker.getColor()));
        rangpallete.setBackgroundColor(colorPicker2.getColor());

        // set on change listener
        colorPicker.setOnColorChangedListener(c -> {
            //c is the selected color
            // rang ra be view emal konid.

            rangpallete.setBackgroundColor(colorPicker.getColor());
            colorPicker2.setColors(ColorPalette.getSecondaryColors(requireActivity().getApplicationContext(), colorPicker.getColor()));
            colorPicker2.setSelectedColor(colorPicker.getColor());
        });

        colorPicker2.setOnColorChangedListener(c -> rangpallete.setBackgroundColor(colorPicker2.getColor()));


        setColorButton = v.findViewById(R.id.setColorButton);
        setColorButton.setOnClickListener(this);

        ///////////////////////////////////////////////////////////////////////////
        // dar inja font ra emal mikonim.
        Typeface iransanserif = Typeface.createFromAsset(requireActivity().getAssets(), "kidfont.ttf");
        TextView backlayouttext = v.findViewById(R.id.bazgasht);
        backlayouttext.setTypeface(iransanserif);
        ////////////////////////////////////////////////////////////////////////////

        return v;
    }//end of onCreateView().

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mbuttonlistener = (buttonclicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == setColorButton) {
            mbuttonlistener.rangdialogclicked(colorPicker2.getColor());
            Objects.requireNonNull(getDialog()).cancel();//dismisses the dialog.

        }
    }


    public interface buttonclicked {
        void rangdialogclicked(int color);

        //ma ba in interface be HomeFragmentJadid bar khahim gasht.
    }

}
