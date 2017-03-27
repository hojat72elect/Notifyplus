package kerman.ir.hojat72elect.notifyplus;

import android.os.Bundle;
import android.preference.PreferenceFragment;


/**
 * Created by hojat72elect on yekshanbe 22 esfand 1395 in kerman.
 */
public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settingspreferences);
    }
}
