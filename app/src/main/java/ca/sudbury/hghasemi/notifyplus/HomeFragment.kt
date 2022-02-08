package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import java.util.*

/**
 * Created by Hojat_Ghasemi on Thursday , 30 March 2017 in kerman.
 */
class HomeFragment : Fragment(), OnTouchListener {
    var buttonsInflater: LayoutInflater? = null
    private var mnoabListener: HomeFragmentInterface? = null
    private var buttonsholder //linear layout that contains the linear layout with buttons.
            : LinearLayout? = null
    private var mPm: PackageManager? = null
    private var isnotifon: SharedPreferences? = null
    private var baval: SharedPreferences? = null
    private var bdovom: SharedPreferences? = null
    private var bsevom: SharedPreferences? = null
    private var bcharom: SharedPreferences? = null
    private var bpanjom: SharedPreferences? = null
    private var bshishom: SharedPreferences? = null
    private var bhaftom: SharedPreferences? = null
    private var bhashtom: SharedPreferences? = null
    private var rangshpref: SharedPreferences? = null
    private var isfloatingon: SharedPreferences? = null
    private var ab1: Button? = null
    private var ab2: Button? = null
    private var ab3: Button? = null
    private var ab4: Button? = null
    private var ab5: Button? = null
    private var ab6: Button? = null
    private var ab7: Button? = null
    private var ab8: Button? = null
    private val firstWriteKey = "bavalsharedpref"
    private val secondWriteKey = "bdovomsharedpref"
    private val thirdWriteKey = "bsevomsharedpref"
    private val fourthWriteKey = "bcharomsharedpref"
    private val fifthWriteKey = "bpanjomsharedpref"
    private val sixthWriteKey = "bshishomsharedpref"
    private val seventhWriteKey = "bhaftomsharedpref"
    private val eighthWriteKey = "bhashtomsharedpref"
    private val colorWriteKey = "rangsharedpref"
    private var bftool: SwitchCompat? = null


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var bnot: SwitchCompat? = null
    private var notificationState = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.home_layout_jadid, container, false)
        val tv1 = result.findViewById<TextView>(R.id.entext)
        val tv2 = result.findViewById<TextView>(R.id.entextrigho)
        val tv3 = result.findViewById<TextView>(R.id.appsnumber)
        val tv4 = result.findViewById<TextView>(R.id.appsnumberrigho)
        val tv5 = result.findViewById<TextView>(R.id.bgc)
        val tv6 = result.findViewById<TextView>(R.id.bgcrigho)
        buttonsholder = result.findViewById<View>(R.id.newbuttonsholder) as LinearLayout

        result.findViewById<View>(R.id.noalayout).let {
            it.setOnClickListener {
                mnoabListener?.noabmethod(0, 0)
            }
        }


        result.findViewById<View>(R.id.bgcolorlayout).let {
            it.setOnClickListener {
                mnoabListener?.noabmethod(2, 0)
            }
        }



        bnot = result.findViewById(R.id.notifyswitch)
        result.findViewById<View>(R.id.togglenot).let {
            it.setOnClickListener {
                bnot?.toggle()
            }
        }


        bnot!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            val notifeditor = isnotifon!!.edit()
            if (isChecked) {
                // The toggle is enabled
                notificationState = true
                notifeditor.putBoolean(write_key_notif, true)
                roudaki(notificationState, number_of_app_buttons)
                //starting the notification with the shared preferences we have
            } else {
                // The toggle is disabled
                notificationState = false
                notifeditor.putBoolean(write_key_notif, false)
                requireActivity().stopService(Intent(activity, NotificationService::class.java))
                //stoping the  whole notification thing.
            }
            notifeditor.apply()
        }
        val c = requireActivity().applicationContext
        mPm = c.packageManager
        number_of_app_buttons =
            requireActivity().getSharedPreferences("number_of_app_buttons_prefs", 0)
        isnotifon = requireActivity().getSharedPreferences("notify_on_orefs", 0)
        baval = requireActivity().getSharedPreferences("first_button_prefs", 0)
        bdovom = requireActivity().getSharedPreferences("second_button_prefs", 0)
        bsevom = requireActivity().getSharedPreferences("third_button_prefs", 0)
        bcharom = requireActivity().getSharedPreferences("forth_button_prefs", 0)
        bpanjom = requireActivity().getSharedPreferences("fifth_button_prefs", 0)
        bshishom = requireActivity().getSharedPreferences("sixth_button_prefs", 0)
        bhaftom = requireActivity().getSharedPreferences("seventh_button_prefs", 0)
        bhashtom = requireActivity().getSharedPreferences("eighth_button_prefs", 0)
        rangshpref = requireActivity().getSharedPreferences("rang_prefs", 0)
        buttonsInflater = inflater


        ///////////////////////////////////////////////////////
        //inja switch marboot be notification ra meghdar dehi mikonim.
        val isnotifyon = isnotifon?.getBoolean(write_key_notif, false)
        if (isnotifyon != null) {
            bnot?.isChecked = isnotifyon
        }
        ///////////////////////////////////////////////////////
        if (kelidsnumber != -1) {
            //az dialoge entekhabe tedade kelidha bargashte im.
            val noabeditor = number_of_app_buttons?.edit()
            noabeditor?.putInt(write_key, kelidsnumber)
            noabeditor?.apply()
        }
        sadie()
        //////////////////////////////////////
        //  dar inja rang ra emal mikonim.
        if (rangbackground != -100) {
            val rangeitor = rangshpref?.edit()
            rangeitor?.putInt(
                colorWriteKey, Color.argb(
                    Color.alpha(rangbackground),
                    Color.red(rangbackground),
                    Color.green(rangbackground),
                    Color.blue(rangbackground)
                )
            )
            rangeitor?.apply()
        }

        ////////////////////////////////////// ferdousi();
        rangshpref?.getInt(colorWriteKey, 0)?.let { buttonsholder?.setBackgroundColor(it) }
        //////////////////////////////////////
        if (bc != 0) {
            when (bc) {
                1 -> {
                    //  ab1.setBackgroundDrawable(imvofappclicked.getDrawable());
                    val bavaleditor = baval?.edit()
                    bavaleditor?.putString(firstWriteKey, tvofappclicked!!.text.toString())
                    bavaleditor?.apply()
                }
                2 -> {
                    // ab2.setBackgroundDrawable(imvofappclicked.getDrawable());
                    val bdovomeditor = bdovom?.edit()
                    bdovomeditor?.putString(secondWriteKey, tvofappclicked!!.text.toString())
                    bdovomeditor?.apply()
                }
                3 -> {
                    // ab3.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    val bsevomeditor = bsevom?.edit()
                    bsevomeditor?.putString(thirdWriteKey, tvofappclicked!!.text.toString())
                    bsevomeditor?.apply()
                }
                4 -> {
                    // ab4.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    val bcharomeditor = bcharom?.edit()
                    bcharomeditor?.putString(fourthWriteKey, tvofappclicked!!.text.toString())
                    bcharomeditor?.apply()
                }
                5 -> {
                    // ab5.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    val bpanjomeditor = bpanjom?.edit()
                    bpanjomeditor?.putString(fifthWriteKey, tvofappclicked!!.text.toString())
                    bpanjomeditor?.apply()
                }
                6 -> {
                    //ab6.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    val bshishomeditor = bshishom?.edit()
                    bshishomeditor?.putString(sixthWriteKey, tvofappclicked!!.text.toString())
                    bshishomeditor?.apply()
                }
                7 -> {
                    // ab7.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    val bhaftomeditor = bhaftom?.edit()
                    bhaftomeditor?.putString(seventhWriteKey, tvofappclicked!!.text.toString())
                    bhaftomeditor?.apply()
                }
                8 -> {
                    // ab7.setBackgroundDrawable(imvofappclicked.getDrawable());//loads the app icon for image view in app's main page.
                    val bhashtomeditor = bhashtom?.edit()
                    bhashtomeditor?.putString(eighthWriteKey, tvofappclicked!!.text.toString())
                    bhashtomeditor?.apply()
                }
                else -> {}
            }
        }
        hafez()
        if (isnotifyon == true) {
            roudaki(notificationState, number_of_app_buttons)
            //agar notification ghablan neshan dade shode bashad ,
            // an ra update mikonim.
        }

        /////////////////////////////////////////////////////////
        val iransanserif = Typeface.createFromAsset(requireActivity().assets, "kidfont.ttf")
        tv1.setTypeface(iransanserif)
        tv2.setTypeface(iransanserif)
        tv3.setTypeface(iransanserif)
        tv4.setTypeface(iransanserif)
        tv5.setTypeface(iransanserif)
        tv6.setTypeface(iransanserif)

        result.findViewById<View>(R.id.floatingtool).let {
            it.setOnClickListener {

            }
        }

        bftool = result.findViewById(R.id.toolswitch)
        bftool?.setOnCheckedChangeListener { _, isChecked ->
            val floatEditor = isfloatingon?.edit()
            if (isChecked) {
                floatEditor?.putBoolean(write_key_floating, true)
                // The toggle is enabled
                //  :show the floating view.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    !Settings.canDrawOverlays(activity?.applicationContext)
                ) {
                    //If the "draw over" permission is not available open the settings screen
                    //to grant the permission.

                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity?.packageName)
                    )
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
                    Toast.makeText(
                        activity?.applicationContext,
                        "You need to first accept the permission requests of this app.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    parvinetesami()
                }
            } else {
                // The toggle is disabled
                // clear the floating view.
                floatEditor?.putBoolean(write_key_floating, false)
                requireActivity().stopService(
                    Intent(
                        requireContext(),
                        FloatingViewService::class.java
                    )
                )
            }
            floatEditor?.commit()
        }

        val isFloatOn = isfloatingon?.getBoolean(write_key_floating, false)

        bftool?.isChecked = isFloatOn ?: false

        result.findViewById<View>(R.id.floatingtool).let {
            it.setOnClickListener {
                bftool?.toggle()
            }
        }



        return result
    }

    private fun parvinetesami() {
        //cals the FloatingViewService.
        try {
            val fli = Intent(activity, FloatingViewService::class.java)
            activity?.startService(fli)
        } catch (e: Exception) {
            Toast.makeText(
                requireActivity().applicationContext,
                "Error in starting the floating control center",
                Toast.LENGTH_LONG
            ).show()
        }

    }



    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mnoabListener = try {
            activity as HomeFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnArticleSelectedListener")
        }
    }

    private fun hafez() {
        //this function , shows the apps which are chosen
        // earlier in the row in app's main page.

        // bayad akse morede nazar dar haman kelidi ke user click karde ast load shavad.
        val noab = number_of_app_buttons!!.getInt(write_key, 0)
        when (noab) {
            1 -> try {
                ab1!!.setBackgroundDrawable(
                    Objects.requireNonNull(
                        baval!!.getString(firstWriteKey, null)
                    )?.let {
                        mPm!!.getApplicationIcon(
                            it
                        )
                    }
                )
            } catch (ignored: Exception) {
            }
            2 -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
            3 -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab3!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bsevom!!.getString(thirdWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
            4 -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab3!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bsevom!!.getString(thirdWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab4!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bcharom!!.getString(fourthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
            5 -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab3!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bsevom!!.getString(thirdWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab4!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bcharom!!.getString(fourthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab5!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bpanjom!!.getString(fifthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
            6 -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab3!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bsevom!!.getString(thirdWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab4!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bcharom!!.getString(fourthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab5!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bpanjom!!.getString(fifthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab6!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bshishom!!.getString(sixthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
            7 -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab3!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bsevom!!.getString(thirdWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab4!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bcharom!!.getString(fourthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab5!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bpanjom!!.getString(fifthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab6!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bshishom!!.getString(sixthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab7!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bhaftom!!.getString(seventhWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
            else -> {
                try {
                    ab1!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            baval!!.getString(firstWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab2!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bdovom!!.getString(secondWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab3!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bsevom!!.getString(thirdWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab4!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bcharom!!.getString(fourthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab5!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bpanjom!!.getString(fifthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab6!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bshishom!!.getString(sixthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab7!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bhaftom!!.getString(seventhWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
                try {
                    ab8!!.setBackgroundDrawable(
                        Objects.requireNonNull(
                            bhashtom!!.getString(eighthWriteKey, null)
                        )?.let {
                            mPm!!.getApplicationIcon(
                                it
                            )
                        }
                    )
                } catch (ignored: Exception) {
                }
            }
        }
        tvofappclicked = null
        bc = 0
    } //end of  hafez().

    private fun roudaki(notify_user: Boolean, number_of_app_buttons: SharedPreferences?) {
        try {
            val favoriteapps: Array<String?>
            val numberAppButtons = number_of_app_buttons!!.getInt(write_key, 0)
            //////////////////////////////////////////////////////////////////////////////////////////////
            val remote: RemoteViews
            when (numberAppButtons) {
                1 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify1)
                    favoriteapps = arrayOf(baval!!.getString(firstWriteKey, null))
                }
                2 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify2)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null)
                    )
                }
                3 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify3)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null),
                        bsevom!!.getString(thirdWriteKey, null)
                    )
                }
                4 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify4)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null),
                        bsevom!!.getString(thirdWriteKey, null),
                        bcharom!!.getString(fourthWriteKey, null)
                    )
                }
                5 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify5)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null),
                        bsevom!!.getString(thirdWriteKey, null),
                        bcharom!!.getString(fourthWriteKey, null),
                        bpanjom!!.getString(fifthWriteKey, null)
                    )
                }
                6 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify6)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null),
                        bsevom!!.getString(thirdWriteKey, null),
                        bcharom!!.getString(fourthWriteKey, null),
                        bpanjom!!.getString(fifthWriteKey, null),
                        bshishom!!.getString(sixthWriteKey, null)
                    )
                }
                8 -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify8)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null),
                        bsevom!!.getString(thirdWriteKey, null),
                        bcharom!!.getString(fourthWriteKey, null),
                        bpanjom!!.getString(fifthWriteKey, null),
                        bshishom!!.getString(sixthWriteKey, null),
                        bhaftom!!.getString(seventhWriteKey, null),
                        bhashtom!!.getString(eighthWriteKey, null)
                    )
                }
                else -> {
                    remote = RemoteViews(requireActivity().packageName, R.layout.notify7)
                    favoriteapps = arrayOf(
                        baval!!.getString(firstWriteKey, null),
                        bdovom!!.getString(secondWriteKey, null),
                        bsevom!!.getString(thirdWriteKey, null),
                        bcharom!!.getString(fourthWriteKey, null),
                        bpanjom!!.getString(fifthWriteKey, null),
                        bshishom!!.getString(sixthWriteKey, null),
                        bhaftom!!.getString(seventhWriteKey, null)
                    )
                }
            }
            remote.setInt(
                R.id.notificationlayout,
                "setBackgroundColor",
                rangshpref!!.getInt(colorWriteKey, 0)
            )

            val notificationIntent = Intent(activity, NotificationService::class.java)
            notificationIntent.putExtra("ufa", favoriteapps)
            notificationIntent.putExtra(
                "notifyuser",
                notify_user
            ) //doesn't have any utility for now.
            notificationIntent.putExtra("viewgroup", remote)
            notificationIntent.putExtra("numberofappbuttons", numberAppButtons)
            requireActivity().startService(notificationIntent)
        } catch (ignored: Exception) {
        }
    } //end of roudaki().

    private fun showlist(bc: Int) {
        mnoabListener!!.noabmethod(1, bc)
    }

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    private fun sadie() {
        try {
            val m = number_of_app_buttons!!.getInt(write_key, 0)
            //linear layout with the buttons inside of it.
            val buttonsRow: LinearLayout
            when (m) {
                1 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_1_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                }
                2 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_2_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                }
                3 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_3_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                }
                4 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_4_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                    ab4 = buttonsRow.findViewById(R.id.button4)
                    ab4?.setOnTouchListener(this)
                    ab4?.setOnClickListener {
                        bc = 4
                        showlist(bc)
                    }
                }
                5 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_5_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                    ab4 = buttonsRow.findViewById(R.id.button4)
                    ab4?.setOnTouchListener(this)
                    ab4?.setOnClickListener {
                        bc = 4
                        showlist(bc)
                    }
                    ab5 = buttonsRow.findViewById(R.id.button5)
                    ab5?.setOnTouchListener(this)
                    ab5?.setOnClickListener {
                        bc = 5
                        showlist(bc)
                    }
                }
                6 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_6_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                    ab4 = buttonsRow.findViewById(R.id.button4)
                    ab4?.setOnTouchListener(this)
                    ab4?.setOnClickListener {
                        bc = 4
                        showlist(bc)
                    }
                    ab5 = buttonsRow.findViewById(R.id.button5)
                    ab5?.setOnTouchListener(this)
                    ab5?.setOnClickListener {
                        bc = 5
                        showlist(bc)
                    }
                    ab6 = buttonsRow.findViewById(R.id.button6)
                    ab6?.setOnTouchListener(this)
                    ab6?.setOnClickListener {
                        bc = 6
                        showlist(bc)
                    }
                }
                7 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_7_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                    ab4 = buttonsRow.findViewById(R.id.button4)
                    ab4?.setOnTouchListener(this)
                    ab4?.setOnClickListener {
                        bc = 4
                        showlist(bc)
                    }
                    ab5 = buttonsRow.findViewById(R.id.button5)
                    ab5?.setOnTouchListener(this)
                    ab5?.setOnClickListener {
                        bc = 5
                        showlist(bc)
                    }
                    ab6 = buttonsRow.findViewById(R.id.button6)
                    ab6?.setOnTouchListener(this)
                    ab6?.setOnClickListener {
                        bc = 6
                        showlist(bc)
                    }
                    ab7 = buttonsRow.findViewById(R.id.button7)
                    ab7?.setOnTouchListener(this)
                    ab7?.setOnClickListener {
                        bc = 7
                        showlist(bc)
                    }
                }
                8 -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_8_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                    ab4 = buttonsRow.findViewById(R.id.button4)
                    ab4?.setOnTouchListener(this)
                    ab4?.setOnClickListener {
                        bc = 4
                        showlist(bc)
                    }
                    ab5 = buttonsRow.findViewById(R.id.button5)
                    ab5?.setOnTouchListener(this)
                    ab5?.setOnClickListener {
                        bc = 5
                        showlist(bc)
                    }
                    ab6 = buttonsRow.findViewById(R.id.button6)
                    ab6?.setOnTouchListener(this)
                    ab6?.setOnClickListener {
                        bc = 6
                        showlist(bc)
                    }
                    ab7 = buttonsRow.findViewById(R.id.button7)
                    ab7?.setOnTouchListener(this)
                    ab7?.setOnClickListener {
                        bc = 7
                        showlist(bc)
                    }
                    ab8 = buttonsRow.findViewById(R.id.button8)
                    ab8?.setOnTouchListener(this)
                    ab8?.setOnClickListener {
                        bc = 8
                        showlist(bc)
                    }
                }
                else -> {
                    buttonsRow =
                        buttonsInflater!!.inflate(R.layout.apps_8_button, null) as LinearLayout
                    ab1 = buttonsRow.findViewById(R.id.button1)
                    ab1?.setOnTouchListener(this)
                    ab1?.setOnClickListener {
                        bc = 1
                        showlist(bc)
                    }
                    ab2 = buttonsRow.findViewById(R.id.button2)
                    ab2?.setOnTouchListener(this)
                    ab2?.setOnClickListener {
                        bc = 2
                        showlist(bc)
                    }
                    ab3 = buttonsRow.findViewById(R.id.button3)
                    ab3?.setOnTouchListener(this)
                    ab3?.setOnClickListener {
                        bc = 3
                        showlist(bc)
                    }
                    ab4 = buttonsRow.findViewById(R.id.button4)
                    ab4?.setOnTouchListener(this)
                    ab4?.setOnClickListener {
                        bc = 4
                        showlist(bc)
                    }
                    ab5 = buttonsRow.findViewById(R.id.button5)
                    ab5?.setOnTouchListener(this)
                    ab5?.setOnClickListener {
                        bc = 5
                        showlist(bc)
                    }
                    ab6 = buttonsRow.findViewById(R.id.button6)
                    ab6?.setOnTouchListener(this)
                    ab6?.setOnClickListener {
                        bc = 6
                        showlist(bc)
                    }
                    ab7 = buttonsRow.findViewById(R.id.button7)
                    ab7?.setOnTouchListener(this)
                    ab7?.setOnClickListener {
                        bc = 7
                        showlist(bc)
                    }
                    ab8 = buttonsRow.findViewById(R.id.button8)
                    ab8?.setOnTouchListener(this)
                    ab8?.setOnClickListener {
                        bc = 8
                        showlist(bc)
                    }
                    val bavaleditor = baval!!.edit()
                    bavaleditor.putString(
                        firstWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bavaleditor.apply()
                    val bdovomeditor = bdovom!!.edit()
                    bdovomeditor.putString(
                        secondWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bdovomeditor.apply()
                    val bsevomeditor = bsevom!!.edit()
                    bsevomeditor.putString(
                        thirdWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bsevomeditor.apply()
                    val bcharomeditor = bcharom!!.edit()
                    bcharomeditor.putString(
                        fourthWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bcharomeditor.apply()
                    val bpanjomeditor = bpanjom!!.edit()
                    bpanjomeditor.putString(
                        fifthWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bpanjomeditor.apply()
                    val bshishomeditor = bshishom!!.edit()
                    bshishomeditor.putString(
                        sixthWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bshishomeditor.apply()
                    val bhaftomeditor = bhaftom!!.edit()
                    bhaftomeditor.putString(
                        seventhWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bhaftomeditor.apply()
                    val bhashtomeditor = bhashtom!!.edit()
                    bhashtomeditor.putString(
                        eighthWriteKey,
                        "kerman.ir.hojat72elect.ca.sudbury.hghasemi.notifyplus"
                    )
                    bhashtomeditor.apply()
                    val noabeditor = number_of_app_buttons!!.edit()
                    noabeditor.putInt(write_key, 8)
                    noabeditor.apply()
                    val rangeditor = rangshpref!!.edit()
                    rangeditor.putInt(colorWriteKey, Color.argb(255, 255, 255, 255))
                    rangeditor.apply()
                    //dar avalin bare ejraye app hamishe in default ast ke rokh midahad.
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "welcome!",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    // be activity begoim ke helpdialogfragment ra rah andazi konad.
                    mnoabListener!!.noabmethod(3, 0)
                }
            }
            buttonsholder!!.removeAllViews()
            buttonsholder!!.addView(buttonsRow)
            //in 2 khate bala ra be hich vajh taghir nadahid bayad hatman be hamin shekl bashand.
        } catch (ignored: Exception) {
        }
    } //end of sadie().


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            v.alpha = 0f
        }
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            v.alpha = 1f
        }

        //ma bedin vasile yek effecte click shodan ra be in kelidhaye toye safhe asli emal mikonim.
        return false
    }

    /**
     * This interface is for communication between "HomeFragment" and "MainActivity"
     */
     interface HomeFragmentInterface {
        fun noabmethod(
            dialognumber: Int,
            bc: Int
        ) //bc !=0 means we need to show a list of apps installed on device.
          //dialognumber=0  dialog for number of shortcuts.
          //dialognumber=1  dialog for installed apps on device.
          //dialognumber=2  dialog for background color.
    }

    companion object {

        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084


        @SuppressLint("StaticFieldLeak")
        private var tvofappclicked: TextView? = null
        private var bc = 0
        private var kelidsnumber = 0
        private var rangbackground = 0
        private var number_of_app_buttons: SharedPreferences? = null

        //shared preferences for saving the number of app buttons.
        private const val write_key = "noab"

        //the key for writing on the shared preferences that contains the number of app buttons.
        private const val write_key_notif = "noton"

        //the key for writing on the shared preferences that contains the state of notification.
        private const val write_key_floating = "floaton"


        @JvmStatic
        fun newInstance(tv: TextView?, mbc: Int, nb: Int, color: Int): HomeFragment {
            tvofappclicked = tv // in va balayi ash faghat agar az dialoge entekhabe app bargardim
            //null nakhahand bood.
            bc = mbc //faghat agar az dialoge entekhabe app bargardim
            //0 nakhahad bood.
            kelidsnumber = nb //faghat agar az dialoge entekhabe tedade kelid ha bargardim
            //-1 nakhahad bood.
            rangbackground = color //faghat agar az dialoge entekhabe range pas zamine bargardim
            //-100 nakhahad bood.

            //dar tamame mavared , in fragment ba estefade az in methode newInstance(ImageView,TextView)
            //sakhte mishavad . vaghti baraye avalin bar sakhte mishavad ba null por mishavad
            //va vaghti ke az dialog be an bar migardim ba etelaate haghighi porash mikonim.
            return HomeFragment()
        }
    }
}