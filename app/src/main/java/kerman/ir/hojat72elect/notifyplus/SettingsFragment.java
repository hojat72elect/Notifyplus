package kerman.ir.hojat72elect.notifyplus;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


/**
 * Created by hojat72elect on yekshanbe 22 esfand 1395 in kerman , ferdousi blvd in my mother home.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private View supernotlayout;
    private View rebootenlayout;
    private View nazardarbazarlayout;
    private Switch supernoton;
    private Switch onrebooton;
    listenerforchangeofsettings mListener;

    public interface listenerforchangeofsettings {
        void runsuper(int supernoton);
        //supernoton=0 bayad HomeFragmentJadid ra rah andazi konad.
        //supernoton=1 bayad SuperHomeFragment ra rah andazi konad.

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            mListener = (listenerforchangeofsettings) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.settingsfragmentlayout, container, false);


        supernotlayout = result.findViewById(R.id.suppernotification);
        rebootenlayout = result.findViewById(R.id.rebootenabling);
        nazardarbazarlayout = result.findViewById(R.id.reviewlayout);

        supernoton = (Switch) result.findViewById(R.id.supernotswitch);
        onrebooton = (Switch) result.findViewById(R.id.onrebootnotifyswitch);


        supernotlayout.setOnClickListener(this);
        rebootenlayout.setOnClickListener(this);
        nazardarbazarlayout.setOnClickListener(this);

        supernoton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
//ba yek listener be activity migoyim super ra rah andazi konad.
                    mListener.runsuper(1);
                } else {
                    // The toggle is disabled
//ba haman listener be activity migoyim super ra khamoosh konad.
                    mListener.runsuper(0);
                }


            }


        });

        onrebooton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
//TODO nemidoonam chekaresh konam. badan code barname tahvim ro mikhonam ta bebinam bayad chekaresh konam.
                } else {
                    // The toggle is disabled

                }


            }


        });

        return result;
    }

    @Override
    public void onClick(View v) {

        if (v == supernotlayout) {
            supernoton.toggle();

        } else if (v == rebootenlayout) {
            onrebooton.toggle();

        } else if (v == nazardarbazarlayout) {

            //TODO : naserkhosro - bayad inja be cafebazaar vasl shavim.

        }
    }
}
