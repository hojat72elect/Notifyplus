package kerman.ir.hojat72elect.notifyplus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hojat72elect on 25 farvardin 1396 in mahan khone aghbaba.
 */
public class SuperHomeFragment extends Fragment implements View.OnClickListener {


    static SuperHomeFragment newInstance() {


        SuperHomeFragment f = new SuperHomeFragment();
        return f;
    }


    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.superhomefragmentlayout, container, false);


        return result;
    }
}
