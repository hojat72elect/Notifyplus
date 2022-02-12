//package ca.sudbury.hghasemi.notifyplus
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Intent
//import android.content.SharedPreferences
//import android.content.pm.PackageManager
//import android.graphics.Color
//import android.graphics.Typeface
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.View.OnTouchListener
//import android.view.ViewGroup
//import android.widget.*
//import androidx.appcompat.widget.SwitchCompat
//import androidx.fragment.app.Fragment
//import java.util.*
//
///**
// * Created by Hojat_Ghasemi on Thursday , 30 March 2017 in kerman.
// */
//class HomeFragment : Fragment(), OnTouchListener {
//    var buttonsInflater: LayoutInflater? = null
//    private var mnoabListener: HomeFragmentInterface? = null
//    private var buttonsholder //linear layout that contains the buttons.
//            : LinearLayout? = null
//    private var mPm: PackageManager? = null
//    private var isnotifon: SharedPreferences? = null
//    private var baval: SharedPreferences? = null
//    private var bdovom: SharedPreferences? = null
//    private var bsevom: SharedPreferences? = null
//    private var bcharom: SharedPreferences? = null
//    private var bpanjom: SharedPreferences? = null
//    private var bshishom: SharedPreferences? = null
//    private var bhaftom: SharedPreferences? = null
//    private var bhashtom: SharedPreferences? = null
//    private var rangshpref: SharedPreferences? = null
//    private var isfloatingon: SharedPreferences? = null
//    private var ab1: Button? = null
//    private var ab2: Button? = null
//    private var ab3: Button? = null
//    private var ab4: Button? = null
//    private var ab5: Button? = null
//    private var ab6: Button? = null
//    private var ab7: Button? = null
//    private var ab8: Button? = null
//    private val firstWriteKey = "bavalsharedpref"
//    private val secondWriteKey = "bdovomsharedpref"
//    private val thirdWriteKey = "bsevomsharedpref"
//    private val fourthWriteKey = "bcharomsharedpref"
//    private val fifthWriteKey = "bpanjomsharedpref"
//    private val sixthWriteKey = "bshishomsharedpref"
//    private val seventhWriteKey = "bhaftomsharedpref"
//    private val eighthWriteKey = "bhashtomsharedpref"
//    private val colorWriteKey = "rangsharedpref"
//    private var bftool: SwitchCompat? = null
//
//
//    @SuppressLint("UseSwitchCompatOrMaterialCode")
//    private var bnot: SwitchCompat? = null
//    private var notificationState = false
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val result = inflater.inflate(R.layout.home_layout_jadid, container, false)
//
//        bnot = result.findViewById(R.id.notifyswitch)
//        result.findViewById<View>(R.id.togglenot).let {
//            it.setOnClickListener {
//                bnot?.toggle()
//            }
//        }
//
//
//        bnot!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            val notifeditor = isnotifon!!.edit()
//            if (isChecked) {
//                // The toggle is enabled
//                notificationState = true
//                notifeditor.putBoolean(write_key_notif, true)
//                roudaki(notificationState, number_of_app_buttons)
//                //starting the notification with the shared preferences we have
//            } else {
//                // The toggle is disabled
//                notificationState = false
//                notifeditor.putBoolean(write_key_notif, false)
//                requireActivity().stopService(Intent(activity, NotificationService::class.java))
//                //stoping the  whole notification thing.
//            }
//            notifeditor.apply()
//        }
//
//
//        val c = requireActivity().applicationContext
//        mPm = c.packageManager
//        isnotifon = requireActivity().getSharedPreferences("notify_on_orefs", 0)

//        ///////////////////////////////////////////////////////
//        //inja switch marboot be notification ra meghdar dehi mikonim.
//        val isnotifyon = isnotifon?.getBoolean(write_key_notif, false)
//        if (isnotifyon != null) {
//            bnot?.isChecked = isnotifyon
//        }
//
//        sadie()
//
//
//        hafez()
//        if (isnotifyon == true) {
//            roudaki(notificationState, number_of_app_buttons)
//            //agar notification ghablan neshan dade shode bashad ,
//            // an ra update mikonim.
//        }
//
//
//
//        result.findViewById<View>(R.id.floatingtool).let {
//            it.setOnClickListener {
//
//            }
//        }
//
//        bftool = result.findViewById(R.id.toolswitch)
//        bftool?.setOnCheckedChangeListener { _, isChecked ->
//            val floatEditor = isfloatingon?.edit()
//            if (isChecked) {
//                floatEditor?.putBoolean(write_key_floating, true)
//                // The toggle is enabled
//                //  :show the floating view.
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                    !Settings.canDrawOverlays(activity?.applicationContext)
//                ) {
//                    //If the "draw over" permission is not available open the settings screen
//                    //to grant the permission.
//
//                    val intent = Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + activity?.packageName)
//                    )
//                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
//                    Toast.makeText(
//                        activity?.applicationContext,
//                        "You need to first accept the permission requests of this app.",
//                        Toast.LENGTH_LONG
//                    ).show()
//                } else {
//                    parvinetesami()
//                }
//            } else {
//                // The toggle is disabled
//                // clear the floating view.
//                floatEditor?.putBoolean(write_key_floating, false)
//                requireActivity().stopService(
//                    Intent(
//                        requireContext(),
//                        FloatingViewService::class.java
//                    )
//                )
//            }
//            floatEditor?.commit()
//        }
//
//        val isFloatOn = isfloatingon?.getBoolean(write_key_floating, false)
//
//        bftool?.isChecked = isFloatOn ?: false
//
//        result.findViewById<View>(R.id.floatingtool).let {
//            it.setOnClickListener {
//                bftool?.toggle()
//            }
//        }
//
//
//
//        return result
//    }
//
//    private fun parvinetesami() {
//        //cals the FloatingViewService.
//        try {
//            val fli = Intent(activity, FloatingViewService::class.java)
//            activity?.startService(fli)
//        } catch (e: Exception) {
//            Toast.makeText(
//                requireActivity().applicationContext,
//                "Error in starting the floating control center",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//
//    }


//
//    private fun roudaki(notify_user: Boolean, number_of_app_buttons: SharedPreferences?) {
//        try {
//            val favoriteapps: Array<String?>
//            val numberAppButtons = number_of_app_buttons!!.getInt(write_key, 0)
//            //////////////////////////////////////////////////////////////////////////////////////////////
//            val remote: RemoteViews
//            when (numberAppButtons) {
//                1 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify1)
//                    favoriteapps = arrayOf(baval!!.getString(firstWriteKey, null))
//                }
//                2 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify2)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null)
//                    )
//                }
//                3 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify3)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null),
//                        bsevom!!.getString(thirdWriteKey, null)
//                    )
//                }
//                4 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify4)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null),
//                        bsevom!!.getString(thirdWriteKey, null),
//                        bcharom!!.getString(fourthWriteKey, null)
//                    )
//                }
//                5 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify5)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null),
//                        bsevom!!.getString(thirdWriteKey, null),
//                        bcharom!!.getString(fourthWriteKey, null),
//                        bpanjom!!.getString(fifthWriteKey, null)
//                    )
//                }
//                6 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify6)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null),
//                        bsevom!!.getString(thirdWriteKey, null),
//                        bcharom!!.getString(fourthWriteKey, null),
//                        bpanjom!!.getString(fifthWriteKey, null),
//                        bshishom!!.getString(sixthWriteKey, null)
//                    )
//                }
//                8 -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify8)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null),
//                        bsevom!!.getString(thirdWriteKey, null),
//                        bcharom!!.getString(fourthWriteKey, null),
//                        bpanjom!!.getString(fifthWriteKey, null),
//                        bshishom!!.getString(sixthWriteKey, null),
//                        bhaftom!!.getString(seventhWriteKey, null),
//                        bhashtom!!.getString(eighthWriteKey, null)
//                    )
//                }
//                else -> {
//                    remote = RemoteViews(requireActivity().packageName, R.layout.notify7)
//                    favoriteapps = arrayOf(
//                        baval!!.getString(firstWriteKey, null),
//                        bdovom!!.getString(secondWriteKey, null),
//                        bsevom!!.getString(thirdWriteKey, null),
//                        bcharom!!.getString(fourthWriteKey, null),
//                        bpanjom!!.getString(fifthWriteKey, null),
//                        bshishom!!.getString(sixthWriteKey, null),
//                        bhaftom!!.getString(seventhWriteKey, null)
//                    )
//                }
//            }
//            remote.setInt(
//                R.id.notificationlayout,
//                "setBackgroundColor",
//                rangshpref!!.getInt(colorWriteKey, 0)
//            )
//
//            val notificationIntent = Intent(activity, NotificationService::class.java)
//            notificationIntent.putExtra("ufa", favoriteapps)
//            notificationIntent.putExtra(
//                "notifyuser",
//                notify_user
//            ) //doesn't have any utility for now.
//            notificationIntent.putExtra("viewgroup", remote)
//            notificationIntent.putExtra("numberofappbuttons", numberAppButtons)
//            requireActivity().startService(notificationIntent)
//        } catch (ignored: Exception) {
//        }
//    } //end of roudaki().
//

//
//    @SuppressLint("ClickableViewAccessibility", "InflateParams")
//    private fun sadie() {

//            buttonsholder!!.removeAllViews()
//            buttonsholder!!.addView(buttonsRow)
//            //in 2 khate bala ra be hich vajh taghir nadahid bayad hatman be hamin shekl bashand.
//        } catch (ignored: Exception) {
//        }
//    } //end of sadie().
//
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouch(v: View, event: MotionEvent): Boolean {
//        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
//            v.alpha = 0f
//        }
//        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
//            v.alpha = 1f
//        }
//
//        //ma bedin vasile yek effecte click shodan ra be in kelidhaye toye safhe asli emal mikonim.
//        return false
//    }
//
//    /**
//     * This interface is for communication between "HomeFragment" and "MainActivity"
//     */
//     interface HomeFragmentInterface {
//        fun noabmethod(
//            dialognumber: Int,
//            bc: Int
//        ) //bc !=0 means we need to show a list of apps installed on device.
//          //dialognumber=0  dialog for number of shortcuts.
//          //dialognumber=1  dialog for installed apps on device.
//          //dialognumber=2  dialog for background color.
//    }
//
//    companion object {
//
//        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
//
//
//        @SuppressLint("StaticFieldLeak")
//        private var tvofappclicked: TextView? = null
//        private var bc = 0
//        private var kelidsnumber = 0
//        private var rangbackground = 0
//        private var number_of_app_buttons: SharedPreferences? = null
//
//        //shared preferences for saving the number of app buttons.
//        private const val write_key = "noab"
//
//        //the key for writing on the shared preferences that contains the number of app buttons.
//        private const val write_key_notif = "noton"
//
//        //the key for writing on the shared preferences that contains the state of notification.
//        private const val write_key_floating = "floaton"
//
//
//        @JvmStatic
//        fun newInstance(tv: TextView?, mbc: Int, nb: Int, color: Int): HomeFragment {
//            tvofappclicked = tv // in va balayi ash faghat agar az dialoge entekhabe app bargardim
//            //null nakhahand bood.
//            bc = mbc //faghat agar az dialoge entekhabe app bargardim
//            //0 nakhahad bood.
//            kelidsnumber = nb //faghat agar az dialoge entekhabe tedade kelid ha bargardim
//            //-1 nakhahad bood.
//            rangbackground = color //faghat agar az dialoge entekhabe range pas zamine bargardim
//            //-100 nakhahad bood.
//
//            //dar tamame mavared , in fragment ba estefade az in methode newInstance(ImageView,TextView)
//            //sakhte mishavad . vaghti baraye avalin bar sakhte mishavad ba null por mishavad
//            //va vaghti ke az dialog be an bar migardim ba etelaate haghighi porash mikonim.
//            return HomeFragment()
//        }
//    }
//}