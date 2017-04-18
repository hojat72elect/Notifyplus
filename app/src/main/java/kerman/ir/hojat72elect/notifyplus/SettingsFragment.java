package kerman.ir.hojat72elect.notifyplus;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hojat72elect on yekshanbe 22 esfand 1395 in kerman.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener{

    private View rateinbazarview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment_layout, container, false);

        rateinbazarview = v.findViewById(R.id.nazarbazarlayout);
        rateinbazarview.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==rateinbazarview){
            //TODO nazar dadan be app dar bazar.
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(Uri.parse("bazaar://details?id=" + "kerman.ir.hojat72elect.notifyplus"));
            intent.setPackage("com.farsitel.bazaar");
            startActivity(intent);
        }
    }
}
