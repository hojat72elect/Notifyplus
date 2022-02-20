package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

/**
First created by Hojat Ghasemi on Sunday, 4th June 2017.
Contact the author at "https://github.com/hojat72elect"
 */
class FloatingViewService : Service()
//    , View.OnClickListener, OnSeekBarChangeListener
{

    private var windowManager: WindowManager? = null
    private var mFloatingView: View? = null


    /**
     * We're not doing anything in this function, we just have to
     * override it as an abstract method of Service class.
     */
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("InflateParams")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)

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


        // Add the view to the window
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager?.addView(mFloatingView, layoutParams)


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mFloatingView != null) {
            windowManager?.removeView(mFloatingView)
        }
    }
//
//    var volume: ImageView? = null
//    var control_center: ImageView? = null
//    var close_button1: ImageView? = null
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
//    var close: ImageView? = null
//    var back: ImageView? = null
//    var closeButton: ImageView? = null
//    var closeButtonCollapsed: ImageView? = null
//    var back_button: ImageView? = null
//    var batterystate: TextView? = null
//
//    private val batteryRcv: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            try {
//                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
//                val maxValue = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//                val chargedPct = level * 100 / maxValue
//                val batteryInfo = "$chargedPct%"
//                batterystate!!.text = batteryInfo
//            } catch (e: Exception) {
//                Log.e("khata dar battery", "Battery receiver failed: ", e)
//            }
//        }
//    }
//    private var brighval: SeekBar? = null
//    private var collapsedView: View? = null
//    private var mainexpandedView: View? = null
//    private var volumeexpandedView: View? = null
//    private var controlcenterexpandedView: View? = null

    //Variable to store brightness value
//    private var brightness = 0

    //Content resolver used as a handle to the system's settings
//    private val cResolver: ContentResolver? = null

    //Window object, that will store a reference to the current window
//    private val window: Window? = null
//    private var mWindowManager: WindowManager? = null
//    private var mFloatingView: View? = null
//


    //
//    @SuppressLint("InflateParams")
//    override fun onCreate() {
//        super.onCreate()
//        //Inflate the floating view layout we created
//        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)
//
//        //Add the view to the window.
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_PHONE,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
//
//        //Specify the view position
//        params.gravity = Gravity.TOP or Gravity.LEFT
//        //Initially view will be added to top-left corner
//        //TODO: jaye in ro bayad toye safhe avazesh konam.
//        params.x = 0
//        params.y = 100
//
//        //Add the view to the window
//        mWindowManager = getSystemService(WINDOW_SERVICE) as WindowManager
//        mWindowManager!!.addView(mFloatingView, params)
//
//        collapsedView = mFloatingView?.findViewById(R.id.collapse_view)
//        mainexpandedView = mFloatingView?.findViewById(R.id.main_expanded_container)
//        volumeexpandedView = mFloatingView?.findViewById(R.id.volume_expanded_container)
//        controlcenterexpandedView =
//            mFloatingView?.findViewById(R.id.control_center_expanded_container)
//
//        volume = mFloatingView?.findViewById(R.id.volume)
//        control_center = mFloatingView?.findViewById(R.id.control_center)
//        close_button1 = mFloatingView?.findViewById(R.id.close_button1)
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
//        close = mFloatingView?.findViewById(R.id.close)
//        back = mFloatingView?.findViewById(R.id.back)
//        closeButton = mFloatingView?.findViewById(R.id.close_button)
//        closeButtonCollapsed = mFloatingView?.findViewById(R.id.close_btn)
//        back_button = mFloatingView?.findViewById(R.id.back_button)
//        batterystate = mFloatingView?.findViewById(R.id.battery_tv)
//
//        brighval = mFloatingView?.findViewById(R.id.seekBar1)
//        brighval?.max = 255
//        brighval?.setOnSeekBarChangeListener(this)
//        maryamheydarzadeh()
//
//
//        volume?.setOnClickListener(this)
//        control_center?.setOnClickListener(this)
//        close_button1?.setOnClickListener(this)
//        mail?.setOnClickListener(this)
//        phone?.setOnClickListener(this)
//        bluetooth?.setOnClickListener(this)
//        wifi?.setOnClickListener(this)
//        rotate?.setOnClickListener(this)
//        airplane?.setOnClickListener(this)
//        battery?.setOnClickListener(this)
//        camera?.setOnClickListener(this)
//        calculator?.setOnClickListener(this)
//        close?.setOnClickListener(this)
//        back?.setOnClickListener(this)
//        closeButton?.setOnClickListener(this)
//        closeButtonCollapsed?.setOnClickListener(this)
//        volume_down?.setOnClickListener(this)
//        volume_up?.setOnClickListener(this)
//        mute?.setOnClickListener(this)
//        vibrate?.setOnClickListener(this)
//        back_button?.setOnClickListener(this)
//        try {
//            registerReceiver(batteryRcv, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
//
//        } catch (e: Exception) {
//            Log.e("khata dar battery!", "Failed to register receiver")
//        }
//
//
//        //Drag and move floating view using user's touch action.
//        mFloatingView?.findViewById<View>(R.id.root_container)
//            ?.setOnTouchListener(object : OnTouchListener {
//                private var initialX = 0
//                private var initialY = 0
//                private var initialTouchX = 0f
//                private var initialTouchY = 0f
//
//                @SuppressLint("ClickableViewAccessibility")
//                override fun onTouch(v: View, event: MotionEvent): Boolean {
//                    when (event.action) {
//                        MotionEvent.ACTION_DOWN -> {
//
//                            //remember the initial position.
//                            initialX = params.x
//                            initialY = params.y
//
//                            //get the touch location
//                            initialTouchX = event.rawX
//                            initialTouchY = event.rawY
//                            return true
//                        }
//                        MotionEvent.ACTION_UP -> {
//                            val Xdiff = (event.rawX - initialTouchX).toInt()
//                            val Ydiff = (event.rawY - initialTouchY).toInt()
//
//                            //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
//                            //So that is click event.
//                            if (Xdiff < 10 && Ydiff < 10) {
//                                if (isViewCollapsed) {
//                                    //When user clicks on the image view of the collapsed layout,
//                                    //visibility of the collapsed layout will be changed to "View.GONE"
//                                    //and expanded view will become visible.
//                                    val floating_tool_appear = AnimationUtils.loadAnimation(
//                                        applicationContext, R.anim.floating_tool_animation_appear
//                                    )
//                                    collapsedView?.visibility = View.GONE
//                                    mainexpandedView?.visibility = View.VISIBLE
//                                    mainexpandedView?.startAnimation(floating_tool_appear)
//                                }
//                            }
//                            return true
//                        }
//                        MotionEvent.ACTION_MOVE -> {
//                            //Calculate the X and Y coordinates of the view.
//                            params.x = initialX + (event.rawX - initialTouchX).toInt()
//                            params.y = initialY + (event.rawY - initialTouchY).toInt()
//
//                            //Update the layout with new X & Y coordinate
//                            mWindowManager!!.updateViewLayout(mFloatingView, params)
//                            return true
//                        }
//                    }
//                    return false
//                }
//            })
//    }
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
//        if (v === closeButtonCollapsed) {
//            stopSelf()
//        } else if (v === close_button1) {
//            val floating_tool_disappear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.floating_tool_animation_disappear
//            )
//            mainexpandedView!!.startAnimation(floating_tool_disappear)
//            mainexpandedView!!.visibility = View.GONE
//            collapsedView!!.visibility = View.VISIBLE
//        } else if (v === volume) {
//            val volume_tool_appear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.volume_control_animation_appear
//            )
//            val main_tool_disappear_in_volume = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_disappears_in_volume
//            )
//            mainexpandedView!!.startAnimation(main_tool_disappear_in_volume)
//            mainexpandedView!!.visibility = View.GONE
//            volumeexpandedView!!.visibility = View.VISIBLE
//            volumeexpandedView!!.startAnimation(volume_tool_appear)
//        } else if (v === control_center) {
//            val main_tool_disappear_in_control_center = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_disappears_in_control_center
//            )
//            val control_center_appear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.control_center_animation_appear
//            )
//            mainexpandedView!!.startAnimation(main_tool_disappear_in_control_center)
//            mainexpandedView!!.visibility = View.GONE
//            controlcenterexpandedView!!.visibility = View.VISIBLE
//            controlcenterexpandedView!!.startAnimation(control_center_appear)
//            maryamheydarzadeh()
//        } else if (v === mail) {
//            //todo safhe ra khamosh konid.
//            //  Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//            pabloneroda("mail")
//        } else if (v === phone) {
//            pabloneroda("phone")
//            //  Toast.makeText(getApplicationContext(), "تلفن", Toast.LENGTH_SHORT).show();
//            //  molavi();
//            //todo: cheragh ghove ra roshan konid
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
//        } else if (v === closeButton) {
//            val volume_close = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.floating_tool_animation_disappear
//            )
//            volumeexpandedView!!.startAnimation(volume_close)
//            volumeexpandedView!!.visibility = View.GONE
//            collapsedView!!.visibility = View.VISIBLE
//        } else if (v === back_button) {
//            val volume_disappear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.volume_control_animation_disappear
//            )
//            val main_appear_in_volume = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_appears_in_volume
//            )
//            volumeexpandedView!!.startAnimation(volume_disappear)
//            volumeexpandedView!!.visibility = View.GONE
//            mainexpandedView!!.visibility = View.VISIBLE
//            mainexpandedView!!.startAnimation(main_appear_in_volume)
//        } else if (v === close) {
//            val control_center_close = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.floating_tool_animation_disappear
//            )
//            controlcenterexpandedView!!.startAnimation(control_center_close)
//            controlcenterexpandedView!!.visibility = View.GONE
//            collapsedView!!.visibility = View.VISIBLE
//        } else if (v === back) {
//            val control_center_disappear = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.control_center_animation_disappear
//            )
//            val main_appear_in_control_center = AnimationUtils.loadAnimation(
//                applicationContext, R.anim.main_disappears_in_control_center
//            )
//            controlcenterexpandedView!!.startAnimation(control_center_disappear)
//            controlcenterexpandedView!!.visibility = View.GONE
//            mainexpandedView!!.visibility = View.VISIBLE
//            mainexpandedView!!.startAnimation(main_appear_in_control_center)
//        } else if (v === bluetooth) {
//            nimaushij()
//        } else if (v === wifi) {
//            dehkhoda()
//        } else if (v === rotate) {
//            //todo ejaze rotate kardan ra be karbar bedahid.
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
//            //todo halat havapeyma ra roshan konid.
//            //  Toast.makeText(getApplicationContext(), "هواپیما", Toast.LENGTH_SHORT).show();
//            manochehridamghani()
//        } else if (v === battery) {
//            //todo tanzimate batri ra emal konid.
//            //     Toast.makeText(getApplicationContext(), "باتری", Toast.LENGTH_SHORT).show();
//        } else if (v === camera) {
//            //todo doorbin ra farakhani konid.
//            //  Toast.makeText(getApplicationContext(), "دوربین", Toast.LENGTH_SHORT).show();
//            //  nezamiganjei();
//            pabloneroda("camera")
//        } else if (v === calculator) {
//            //todo mashin hesab ra farakhani konid.
//            //  Toast.makeText(getApplicationContext(), "ماشین حساب", Toast.LENGTH_SHORT).show();
//            pabloneroda("calcul")
//        }
//    }

//
//    private fun maryamheydarzadeh() {
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
//
//    /**
//     * Detect if the floating view is collapsed or expanded.
//     *
//     * @return true if the floating view is collapsed.
//     */
//    private val isViewCollapsed: Boolean
//        get() = mFloatingView == null || mFloatingView!!.findViewById<View>(R.id.collapse_view).visibility == View.VISIBLE
//
//    private fun manochehridamghani() {
//        //in 17+ android APIs , we use intent for leading the user to airplane section in settings.
//        //todo : do the toggling of airplane mode.
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
//        //todo wifi ra roshan konid.
//        //  Toast.makeText(getApplicationContext(), "wifi", Toast.LENGTH_SHORT).show();
//        val wifi = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//        wifi.isWifiEnabled = !wifi.isWifiEnabled
//    }
//
//    private fun nimaushij() {
//        //todo bluetooth ra roshan konid.
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
//        //todo: 1-I should add the current time of system to the name of stored picture and save it to app's exclusive folder.
//        //todo: 2-in API 23+ systems , i should ask for user's permission , manually.
//        var output: File? = null
//        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
//        output = File(dir, "CameraContentDemo.jpeg")
//        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output))
//        startActivity(Intent.createChooser(i, "یک برنامه عکاسی را انتخاب کنید."))
//    }
}