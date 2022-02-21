package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import java.util.*


/**
First created by Hojat Ghasemi on Sunday, 4th June 2017.
Contact the author at "https://github.com/hojat72elect"
 */
class FloatingViewService : Service()
//    , View.OnClickListener, OnSeekBarChangeListener
{


    var batteryStatus: TextView? = null // shows the battery percentage

    private var windowManager: WindowManager? = null
    private var mFloatingView: View? = null
    var imageClose: ImageView? = null
    private var width = 0f
    private var height = 0f
    private var isViewCollapsed: Boolean? =
        null // notifies whether the widget is collapsed or expanded


    /**
     * We're not doing anything in this function, we just have to
     * override it as an abstract method of Service class.
     */
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("InflateParams", "ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        // inflating the widget's layout
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)

        batteryStatus = mFloatingView?.findViewById(R.id.battery_tv)

        //Add the view to the window.
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // initial position
        layoutParams.gravity = Gravity.TOP or Gravity.END
        layoutParams.x = 0
        layoutParams.y = 100

        // layout params for close button
        val imageParams = WindowManager.LayoutParams(
            140,
            140,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        imageParams.gravity = Gravity.BOTTOM or Gravity.CENTER
        imageParams.y = 100

        // Add the view to the window
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        imageClose = ImageView(this).also {
            it.setImageResource(R.drawable.ic_close_white)
            it.visibility = View.INVISIBLE

        }

        windowManager?.addView(imageClose, imageParams)
        windowManager?.addView(mFloatingView, layoutParams)

        mFloatingView?.visibility = View.VISIBLE // why?
        height = windowManager!!.defaultDisplay.height.toFloat() // deprecated in API 30
        width = windowManager!!.defaultDisplay.width.toFloat()

        // Registering all the listeners for this tool
        mFloatingView?.setOnTouchListener(object : OnTouchListener {
            var initialX = 0
            var initialY = 0
            var initialTouchX = 0f
            var initialTouchY = 0f
            var startClickTime: Long = 0
            val MAX_CLICK_DURATION = 200
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startClickTime = Calendar.getInstance().timeInMillis
                        imageClose?.visibility = View.VISIBLE
                        initialX = layoutParams.x
                        initialY = layoutParams.y
                        // getting the touch position
                        initialTouchX = motionEvent.rawX
                        initialTouchY = motionEvent.rawY
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                        imageClose?.visibility = View.GONE
                        layoutParams.x = initialX + (initialTouchX - motionEvent.rawX).toInt()
                        layoutParams.y = initialY + (motionEvent.rawY - initialTouchY).toInt()
                        if (clickDuration < MAX_CLICK_DURATION) {
                            // user clicked on the floating widget
                            if (isViewCollapsed == true) {
                                // Show the main expanded view
                                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility =
                                    View.GONE
                                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility =
                                    View.VISIBLE
                            }
                        } else {
                            // remove widget
                            if (layoutParams.y > height * 0.6) {
                                stopSelf()
                            }
                        }
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        // calculate X & Y coordinates of view
                        layoutParams.x = initialX + (initialTouchX - motionEvent.rawX).toInt()
                        layoutParams.y = initialY + (motionEvent.rawY - initialTouchY).toInt()

                        // update layout with new coordinates
                        windowManager?.updateViewLayout(mFloatingView, layoutParams)
                        if (layoutParams.y > height * 0.6) {
                            // the pic when user's about to delete the floating widget
                            imageClose?.setImageResource(R.drawable.ic_close_red)
                        } else {
                            // the normal pic
                            imageClose?.setImageResource(R.drawable.ic_close_white)
                        }
                        return true

                    }
                }
                return false
            }
        })
        mFloatingView?.findViewById<View>(R.id.close_button1).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE


            }
        }
        mFloatingView?.findViewById<View>(R.id.control_center).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.GONE
                mFloatingView?.findViewById<View>(R.id.control_center_expanded_container)?.visibility =
                    View.VISIBLE
            }
        }
        mFloatingView?.findViewById<View>(R.id.volume).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.GONE
                mFloatingView?.findViewById<View>(R.id.volume_expanded_container)?.visibility =
                    View.VISIBLE
            }
        }
        mFloatingView?.findViewById<View>(R.id.back_button).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.volume_expanded_container)?.visibility =
                    View.GONE
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.VISIBLE
            }

        }
        mFloatingView?.findViewById<View>(R.id.close_button2).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.volume_expanded_container)?.visibility =
                    View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE
            }
        }
        mFloatingView?.findViewById<View>(R.id.back).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.control_center_expanded_container)?.visibility =
                    View.GONE
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.VISIBLE
            }
        }
        mFloatingView?.findViewById<View>(R.id.close_button3).let {
            it?.setOnClickListener {
                mFloatingView?.findViewById<View>(R.id.control_center_expanded_container)?.visibility =
                    View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE
            }

        }

        // registering receiver for battery status
        registerReceiver(batteryListener, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        // view should be collapsed at the start
        isViewCollapsed = true


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mFloatingView != null) {
            windowManager?.removeView(mFloatingView)
        }
        if (imageClose != null) {
            windowManager?.removeView(imageClose)
        }
    }

    /**
     * This BroadcastReceiver receives current percent of battery and loads it into a
     * TextView in our floating widget.
     */
    private val batteryListener: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            try {
                val chargedPercent =
                    (intent.getIntExtra(
                        BatteryManager.EXTRA_LEVEL,
                        -1
                    )) * 100 / (intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1))
                batteryStatus?.text = "$chargedPercent%"
            } catch (e: Exception) {
                Log.e("FloatViewService", "error receiving battery status: ${e.message}")
            }
        }

    }

}


//    var volume: ImageView? = null

//    var mail: ImageView? = null
//    var phone: ImageView? = null
//    var volume_down: ImageView? = null
//    var volume_up: ImageView? = null
//    var mute: ImageView? = null
//    var vibrate: ImageView? = null
//    var bluetooth: ImageView? = null
//    var wifi: ImageView? = null
//    var rotate: ImageView? = null
//    var airplane: ImageView? = null
//    var battery: ImageView? = null
//    var camera: ImageView? = null
//    var calculator: ImageView? = null


//    private var brighval: SeekBar? = null


//Variable to store brightness level
//    private var brightness = 0

//Content resolver used as a handle to the system's settings
//    private val cResolver: ContentResolver? = null

//Window object, that will store a reference to the current window
//    private val window: Window? = null
//    private var mWindowManager: WindowManager? = null
//    private var mFloatingView: View? = null
//

//    override fun onCreate() {
//


//        mail = mFloatingView?.findViewById(R.id.mail)
//        phone = mFloatingView?.findViewById(R.id.phone)
//        volume_down = mFloatingView?.findViewById(R.id.volume_down)
//        volume_up = mFloatingView?.findViewById(R.id.volume_up)
//        mute = mFloatingView?.findViewById(R.id.mute)
//        vibrate = mFloatingView?.findViewById(R.id.vibrate)
//        bluetooth = mFloatingView?.findViewById(R.id.bluetooth)
//        wifi = mFloatingView?.findViewById(R.id.wifi)
//        rotate = mFloatingView?.findViewById(R.id.rotate)
//        airplane = mFloatingView?.findViewById(R.id.airplane)
//        battery = mFloatingView?.findViewById(R.id.battery)
//        camera = mFloatingView?.findViewById(R.id.camera)
//        calculator = mFloatingView?.findViewById(R.id.calculator)
//
//        brighval = mFloatingView?.findViewById(R.id.seekBar1)
//        brighval?.max = 255
//        brighval?.setOnSeekBarChangeListener(this)
//        maryamheydarzadeh()
//
//

//        mail?.setOnClickListener(this)
//        phone?.setOnClickListener(this)
//        bluetooth?.setOnClickListener(this)
//        wifi?.setOnClickListener(this)
//        rotate?.setOnClickListener(this)
//        airplane?.setOnClickListener(this)
//        battery?.setOnClickListener(this)
//        camera?.setOnClickListener(this)
//        calculator?.setOnClickListener(this)
//        volume_down?.setOnClickListener(this)
//        volume_up?.setOnClickListener(this)
//        mute?.setOnClickListener(this)
//        vibrate?.setOnClickListener(this)



//                                    // The animations for when user opens the main view by clicking on the floating widget.
//                                    val floating_tool_appear = AnimationUtils.loadAnimation(
//                                        applicationContext, R.anim.floating_tool_animation_appear
//                                    )
//                                    mainexpandedView?.startAnimation(floating_tool_appear)
//


//    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//        if (fromUser) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                !Settings.System.canWrite(applicationContext)
//            ) {
//                //If the "write settings" permission is not available open the settings screen
//                //to grant the permission.
//                onsori(0)
//            } else {
//                brightness = brighval!!.progress
//                if (brightness < 0) {
//                    brightness = 0
//                } else if (brightness > 255) {
//                    brightness = 255
//                }
//                val cResolver = this.applicationContext.contentResolver
//                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
//
//
//                /*    Intent intent = new Intent(getBaseContext(), DummyBrightnessActivity.class);
//            //    Log.d("brightend", String.valueOf(brightness / (float)255));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //this is important
//            //in the next line 'brightness' should be a float number between 0.0 and 1.0
//            intent.putExtra("brightness value", brightness / (float) 255);
//            startActivity(intent);
//
//*/
//            }
//        }
//    }
//
//    override fun onStartTrackingTouch(seekBar: SeekBar) {}
//    override fun onStopTrackingTouch(seekBar: SeekBar) {}
//


//    override fun onClick(v: View) {


// Animation for collapsing the main expanded view into floating widget button
//            val floating_tool_disappear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.floating_tool_animation_disappear
//            )
//            mainexpandedView!!.startAnimation(floating_tool_disappear)


// Animation for going from main expanded view to volume control expanded view
//            val volume_tool_appear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.volume_control_animation_appear
//            )
//            val main_tool_disappear_in_volume = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_disappears_in_volume
//            )
//            mainexpandedView!!.startAnimation(main_tool_disappear_in_volume)
//            volumeexpandedView!!.startAnimation(volume_tool_appear)


// Animation for going from main expanded view to control center expanded view.
//            val main_tool_disappear_in_control_center = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_disappears_in_control_center
//            )
//            val control_center_appear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.control_center_animation_appear
//            )
//            mainexpandedView!!.startAnimation(main_tool_disappear_in_control_center)
//            controlcenterexpandedView!!.startAnimation(control_center_appear)


//              if (v === mail) {
//            // safhe ra khamosh konid.
//            //  Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//            pabloneroda("mail")
//        } else if (v === phone) {
//            pabloneroda("phone")
//            //  Toast.makeText(getApplicationContext(), "تلفن", Toast.LENGTH_SHORT).show();
//            //  molavi();
//            //: cheragh ghove ra roshan konid
//        } else if (v === volume_down) {
//            //khajokermani()
//            //seda ra kam mikonim.
//            val audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
//            audioManager.adjustSuggestedStreamVolume(
//                AudioManager.ADJUST_LOWER,
//                AudioManager.USE_DEFAULT_STREAM_TYPE,
//                AudioManager.FLAG_SHOW_UI
//            )
//        } else if (v === volume_up) {
//            //eghballahouri()
//            //seda ra ziad mikonim.
//            val audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
//            audioManager.adjustSuggestedStreamVolume(
//                AudioManager.ADJUST_RAISE,
//                AudioManager.USE_DEFAULT_STREAM_TYPE,
//                AudioManager.FLAG_SHOW_UI
//            )
//        } else if (v === mute) {
//            //fakhreldineraghi()
//            //mute the phone with no vibration
//            val audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
//            audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
//        } else if (v === vibrate) {
//            val audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
//            audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
//        }


//          Animation for collapsing the volume control view
//            val volume_close = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.floating_tool_animation_disappear
//            )
//            volumeexpandedView!!.startAnimation(volume_close)


//              Animation for going from volume control view to main expanded view
//            val volume_disappear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.volume_control_animation_disappear
//            )
//            val main_appear_in_volume = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_appears_in_volume
//            )
//            volumeexpandedView!!.startAnimation(volume_disappear)
//            mainexpandedView!!.startAnimation(main_appear_in_volume)


// Animation for collapsing the control center view to the floating widget.
//            val control_center_close = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.floating_tool_animation_disappear
//            )
//            controlcenterexpandedView!!.startAnimation(control_center_close)


// Animation for going from control center view to main expanded view.
//            val control_center_disappear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.control_center_animation_disappear
//            )
//            val main_appear_in_control_center = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_disappears_in_control_center
//            )
//            controlcenterexpandedView!!.startAnimation(control_center_disappear)
//            mainexpandedView!!.startAnimation(main_appear_in_control_center)


//        if (v === bluetooth) {
//            nimaushij()
//        } else if (v === wifi) {
//            dehkhoda()
//        } else if (v === rotate) {
//            // ejaze rotate kardan ra be karbar bedahid.
//            //  Toast.makeText(getApplicationContext(), "چرخش", Toast.LENGTH_SHORT).show();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                !Settings.System.canWrite(applicationContext)
//            ) {
//                //If the "write settings" permission is not available open the settings screen
//                //to grant the permission.
//                onsori(1)
//            } else {
//                sanaiyi()
//            }
//        } else if (v === airplane) {
//            // halat havapeyma ra roshan konid.
//            //  Toast.makeText(getApplicationContext(), "هواپیما", Toast.LENGTH_SHORT).show();
//            manochehridamghani()
//        } else if (v === camera) {
//            // doorbin ra farakhani konid.
//            //  Toast.makeText(getApplicationContext(), "دوربین", Toast.LENGTH_SHORT).show();
//            //  nezamiganjei();
//            pabloneroda("camera")
//        } else if (v === calculator) {
//            // mashin hesab ra farakhani konid.
//            //  Toast.makeText(getApplicationContext(), "ماشین حساب", Toast.LENGTH_SHORT).show();
//            pabloneroda("calcul")
//        }
//    }


//    private fun maryamheydarzadeh() {
// this method should be called whenever the control center expanded view is opened.
//        //we adopt the seekbar for the current brightness level of screen.
//        try {
//            brighval!!.progress = Settings.System.getInt(
//                applicationContext.contentResolver,
//                Settings.System.SCREEN_BRIGHTNESS,
//                0
//            ) //////////////////////////////////////
//        } catch (e: Exception) {
//        }
//    }

//    private fun manochehridamghani() {
//        //in 17+ android APIs , we use intent for leading the user to airplane section in settings.
//        // : do the toggling of airplane mode.
//        try {
//            val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            try {
//                val intent = Intent("android.settings.WIRELESS_SETTINGS")
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//            } catch (ex: ActivityNotFoundException) {
//                Toast.makeText(
//                    applicationContext,
//                    "دستگاه شما از این قابلیت پشتیبانی نمی کند.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun onsori(toast: Int) {
//        //this method asks for user's permission for write on settings in android 23(6.0 marshmallow) and above.
//        val intent = Intent(
//            Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse(
//                "package:$packageName"
//            )
//        )
//        startActivity(intent)
//        if (toast == 1) {
//            Toast.makeText(
//                applicationContext,
//                "please accept the permissions to the app first",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    private fun sanaiyi() {
//        //this method toggles the state of auto rotation in device.
//        if (Settings.System.getInt(
//                applicationContext.contentResolver,
//                Settings.System.ACCELEROMETER_ROTATION,
//                1
//            ) == 1
//        ) {
//            Settings.System.putInt(
//                applicationContext.contentResolver,
//                Settings.System.ACCELEROMETER_ROTATION,
//                0
//            )
//        } else {
//            Settings.System.putInt(
//                applicationContext.contentResolver,
//                Settings.System.ACCELEROMETER_ROTATION,
//                1
//            )
//        }
//    }
//
//    private fun dehkhoda() {
//        // wifi ra roshan konid.
//        //  Toast.makeText(getApplicationContext(), "wifi", Toast.LENGTH_SHORT).show();
//        val wifi = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//        wifi.isWifiEnabled = !wifi.isWifiEnabled
//    }
//
//    private fun nimaushij() {
//        // bluetooth ra roshan konid.
//        //  Toast.makeText(getApplicationContext(), "بلوتوث", Toast.LENGTH_SHORT).show();
//        val btAdapter = BluetoothAdapter.getDefaultAdapter()
//        if (btAdapter == null) {
//            Toast.makeText(applicationContext, "بلوتوث ندارید!", Toast.LENGTH_SHORT).show()
//        } else {
//            // bt available
//            if (btAdapter.isEnabled) {
////                btAdapter.disable();
//            } else {
////                btAdapter.enable();
//            }
//        }
//    }
//
//    @SuppressLint("QueryPermissionsNeeded")
//    private fun pabloneroda(search: String) {
//        //calls the calculator
//        val items = ArrayList<HashMap<String, Any>>()
//        val pm = packageManager
//        val packs = pm.getInstalledPackages(0)
//        for (pi in packs) {
//            if (pi.packageName.lowercase(Locale.getDefault())
//                    .contains(search) || pi.applicationInfo.loadLabel(pm)
//                    .toString().lowercase(Locale.getDefault()).contains(search)
//            ) {
//                val map = HashMap<String, Any>()
//                map["appName"] = pi.applicationInfo.loadLabel(pm)
//                map["packageName"] = pi.packageName
//                items.add(map)
//            }
//        }
//
//        if (items.size >= 1) {
//
//            for (c in 0..items.size) {
//                val myintent = pm.getLaunchIntentForPackage((items[c]["packageName"] as String?)!!)
//                if (myintent != null) {
//                    startActivity(myintent)
//                    break
//                }
//            }
//        } else {
//            // Application not found
//            Toast.makeText(applicationContext, "برنامه ی مناسبی پیدا نشد.", Toast.LENGTH_SHORT)
//                .show()
//        }
//    }
//
//    private fun nezamiganjei() {
//        //dorbin ra farakhani mikonad.
//        //: 1-I should add the current time of system to the name of stored picture and save it to app's exclusive folder.
//        //: 2-in API 23+ systems , i should ask for user's permission , manually.
//        var output: File? = null
//        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
//        output = File(dir, "CameraContentDemo.jpeg")
//        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output))
//        startActivity(Intent.createChooser(i, "یک برنامه عکاسی را انتخاب کنید."))
//    }
