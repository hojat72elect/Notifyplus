package kerman.ir.hojat72elect.notifyplus;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hojat72elect on doshanbe 23 esfand 1395 in kerman.
 */
public class AboutappFragment extends DialogFragment implements View.OnClickListener {

    Button btelegram;
    Button binstagram;

    static AboutappFragment newInstance() {

        return new AboutappFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.about_app_fragment, container, false);
        btelegram = (Button) result.findViewById(R.id.btelegram);
        btelegram.setOnClickListener(this);

        binstagram = (Button) result.findViewById(R.id.binstagram);
        binstagram.setOnClickListener(this);

        return (result);
    }


    @Override
    public void onClick(View v) {
        if (v == btelegram) {
            // yek peygham dar telegram ersal konid.
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setPackage("org.telegram.messenger");
                i.setData(Uri.parse("https://t.me/hojat72elect"));
                i.putExtra(Intent.EXTRA_TEXT, "hello telegram!");//chera matni ra neshan nemidahad?

                startActivity(i);
            } catch (Exception e) {

            }
        } else if (v == binstagram) {
            //TODO be instagram hedayatash konid.
            Uri uri = Uri.parse("http://instagram.com/_u/hojat72elect");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/hojat72elect")));
            }


        }
    }
}
