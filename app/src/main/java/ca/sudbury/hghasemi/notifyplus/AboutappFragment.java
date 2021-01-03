package ca.sudbury.hghasemi.notifyplus;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import ca.sudbury.hghasemi.notifyplus.views.ButtonRectangle;

/**
 * Created by Hojat_Ghasemi on Monday ,13 March 2017 in kerman.
 */
public class AboutappFragment extends DialogFragment implements View.OnClickListener {

    ButtonRectangle btelegram;
    ButtonRectangle binstagram;
    TextView t1;
    View bp;
    TextView t2;
    View backlayout;

    static AboutappFragment newInstance() {

        return new AboutappFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.about_app_fragment, container, false);

        //////////////////////////////////////////
        btelegram = result.findViewById(R.id.btelegram);
        btelegram.setOnClickListener(this);
        backlayout = result.findViewById(R.id.backlayout);
        backlayout.setOnClickListener(this);
        binstagram = result.findViewById(R.id.binstagram);
        binstagram.setOnClickListener(this);

        t1 = result.findViewById(R.id.t1);
        t2 = result.findViewById(R.id.t2);
        bp = result.findViewById(R.id.bp);
        ///////////////////////////////////////
        //here we load the animations:

        Animation BaseAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.dialoganim);
        Animation SecondAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.dialoganimtwo);
        Animation ThirdAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.dialoganimthree);

        ///////////////////////////////////////////////////////////////
        Typeface iransanserif = Typeface.createFromAsset(getActivity().getAssets(), "kidfont.ttf");
        t1.setTypeface(iransanserif);
        t2.setTypeface(iransanserif);
        ///////////////////////////////////////////////////////////////

        TextView backlayouttext = result.findViewById(R.id.bazgasht);
        backlayouttext.setTypeface(iransanserif);
        ///////////////////////////////////////
        //here we apply the animations.

        t1.startAnimation(BaseAnimation);
        t2.startAnimation(SecondAnimation);
        bp.startAnimation(ThirdAnimation);

        return (result);
    }


    @Override
    public void onClick(View v) {
        if (v == btelegram) {


            Uri uri = Uri.parse("https://t.me/hojat72elect");
            Intent liketel = new Intent(Intent.ACTION_VIEW, uri);
            liketel.setPackage("org.telegram.messenger");

            try {
                startActivity(liketel);
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.errortelegram, Toast.LENGTH_LONG).show();

            }


        } else if (v == binstagram) {
            // be instagram hedayatash konid.
            Uri uri = Uri.parse("http://instagram.com/_u/hojat72elect");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/hojat72elect")));
            }


        } else if (v == backlayout) {
            getDialog().cancel();
        }
    }
}
