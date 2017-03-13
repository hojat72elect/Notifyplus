package kerman.ir.hojat72elect.notifyplus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hojat72elect on doshanbe 23 esfand 1395 in kerman.
 */
public class HomeFragnent extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.home_layout, container, false);

        return(result);
    }



}
