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


    listenerforchangeofsettings mListener;
    private View rebootenlayout;
    private View nazardarbazarlayout;
    private Switch onrebooton;

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


        rebootenlayout = result.findViewById(R.id.rebootenabling);
        nazardarbazarlayout = result.findViewById(R.id.reviewlayout);
        onrebooton = (Switch) result.findViewById(R.id.onrebootnotifyswitch);
        rebootenlayout.setOnClickListener(this);
        nazardarbazarlayout.setOnClickListener(this);
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

        if (v == rebootenlayout) {
            onrebooton.toggle();

        } else if (v == nazardarbazarlayout) {

            //TODO : naserkhosro - bayad inja be cafebazaar vasl shavim.

        }
    }

    public interface listenerforchangeofsettings {
        void runsuper(int supernoton);
        //supernoton=0 bayad HomeFragmentJadid ra rah andazi konad.
        //supernoton=1 bayad SuperHomeFragment ra rah andazi konad.

    }
}
