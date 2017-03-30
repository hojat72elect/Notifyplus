package kerman.ir.hojat72elect.notifyplus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hojat72elect on panjshanbe 10 farvardin 1396 in kerman.
 */
public class HomeFragmentJadid extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      View result = inflater.inflate(R.layout.home_layout_jadid, container, false);


        return result;
    }
}
