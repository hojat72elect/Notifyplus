package kerman.ir.hojat72elect.notifyplus;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hojat72elect on seshanbe 29 farvardin 1396 in kerman .
 */
public class HelpDialogfragment  extends DialogFragment {



    static HelpDialogfragment newInstance() {

        return new HelpDialogfragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.help_dialog_fragment, container, false);








getDialog().setCancelable(true);
        getDialog().setTitle(R.string.darbaretitle);




        return v;
    }
}
