package kerman.ir.hojat72elect.notifyplus;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hojat72elect on 12/03/2017.
 */
public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.settings_layout, container, false);

        return(result);
    }
}
