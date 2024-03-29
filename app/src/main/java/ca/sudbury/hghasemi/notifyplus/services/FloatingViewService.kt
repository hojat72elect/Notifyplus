package ca.sudbury.hghasemi.notifyplus.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothManager
import android.content.*
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.media.AudioManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import ca.sudbury.hghasemi.notifyplus.R
import java.util.*


/**
First created by Hojat Ghasemi on Sunday, 4th June 2017.
Contact the author at "https://github.com/hojat72elect"
 */
class FloatingViewService : Service() {

    var batteryStatus: TextView? = null // shows the battery percentage
    private var windowManager: WindowManager? = null
    private var mFloatingView: View? = null
    var imageClose: ImageView? = null
    private var width = 0f
    private var height = 0f
    private var isViewCollapsed: Boolean? =
        null // notifies whether the widget is collapsed or expanded
    private var brightnessSeekBar: SeekBar? = null // the seekbar for controlling screen brightness

    /**
     * it's used for when an activity binds to this service
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

        // layout params for the close button
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
                                // the widget view is not collapsed any more
                                isViewCollapsed = false
                            }
                        } else {
                            // remove widget
                            if (layoutParams.y > height * 0.6) {
                                stopSelf()
                                //todo: tell the MainActivity about it.
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
                isViewCollapsed = true
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
                isViewCollapsed = true
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
                isViewCollapsed = true
            }
        }
        mFloatingView?.findViewById<View>(R.id.camera).let {
            it?.setOnClickListener {
                // fire up an intent for opening device's camera
                fireIntent("camera", this.packageManager, this)
                // collapse the widget
                mFloatingView?.findViewById<View>(R.id.control_center_expanded_container)?.visibility =
                    View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE
                isViewCollapsed = true
            }
        }
        mFloatingView?.findViewById<View>(R.id.mail).let {
            it?.setOnClickListener {
                // Fire up an intent for opening email app
                fireIntent("mail", this.packageManager, this)
                // Collapse the widget
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE
                isViewCollapsed = true
            }
        }
        mFloatingView?.findViewById<View>(R.id.phone).let {
            it?.setOnClickListener {
                // Fire up an intent for opening telephone app
                fireIntent("phone", this.packageManager, this)
                // Collapse the widget
                mFloatingView?.findViewById<View>(R.id.expanded_view)?.visibility = View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE
                isViewCollapsed = true
            }
        }
        mFloatingView?.findViewById<View>(R.id.calculator).let {
            it?.setOnClickListener {
                // Fire up an intent for opening calculator app
                fireIntent("calculator", this.packageManager, this)
                // Collapse the widget
                mFloatingView?.findViewById<View>(R.id.control_center_expanded_container)?.visibility =
                    View.GONE
                mFloatingView?.findViewById<View>(R.id.collapsed_view)?.visibility = View.VISIBLE
                isViewCollapsed = true
            }
        }
        mFloatingView?.findViewById<View>(R.id.bluetooth).let {
            it?.setOnClickListener {
                // Turn on/off the bluetooth
                val btAdapter = (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
                if (btAdapter == null) {
                    Toast.makeText(
                        this.applicationContext,
                        "Bluetooth not available!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Bluetooth hardware available
                    // The conditions in "if" clause below are nothing special, I'm just
                    // checking if 3 bluetooth permissions are granted or not.
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.BLUETOOTH
                        ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.BLUETOOTH_ADMIN
                        ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // User has already granted the bluetooth permissions
                        if (btAdapter.isEnabled) {
                            btAdapter.disable()
                            // clean the highlighted key
                            mFloatingView?.findViewById<View>(R.id.bluetooth)
                                ?.setBackgroundColor(resources.getColor(android.R.color.white))
                        } else {
                            btAdapter.enable()
                            // highlight the bluetooth key
                            mFloatingView?.findViewById<View>(R.id.bluetooth)
                                ?.setBackgroundColor(resources.getColor(android.R.color.holo_blue_bright))
                        }
                    } else {
                        // You can't normally ask for permissions from a service; just ask user to
                        // accept them in the app's settings
                        Toast.makeText(
                            applicationContext,
                            "Please go to app's settings page and accept the required permissions first!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        mFloatingView?.findViewById<View>(R.id.rotate).let {
            it?.setOnClickListener {
                // first check if you have the permission for writing to system settings
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    !Settings.System.canWrite(applicationContext)
                ) {
                    // Just ask the user to grant permissions
                    askForWriteSystemSettingsPermission()
                } else {
                    // Read the current state of auto rotation and toggle it.
                    if (Settings.System.getInt(
                            applicationContext.contentResolver,
                            Settings.System.ACCELEROMETER_ROTATION,
                            1
                        ) == 1
                    ) {
                        Settings.System.putInt(
                            applicationContext.contentResolver,
                            Settings.System.ACCELEROMETER_ROTATION, 0
                        )
                        mFloatingView?.findViewById<View>(R.id.rotate)
                            ?.setBackgroundColor(resources.getColor(android.R.color.white))
                    } else {
                        Settings.System.putInt(
                            applicationContext.contentResolver,
                            Settings.System.ACCELEROMETER_ROTATION,
                            1
                        )
                        mFloatingView?.findViewById<View>(R.id.rotate)
                            ?.setBackgroundColor(resources.getColor(android.R.color.holo_blue_bright))
                    }
                }
            }
        }
        mFloatingView?.findViewById<View>(R.id.airplane).let {
            it?.setOnClickListener {
                try {
                    startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                } catch (e: ActivityNotFoundException) {
                    try {
                        startActivity(Intent("android.settings.WIRELESS_SETTINGS").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            applicationContext,
                            "Your device doesn't support this feature",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        mFloatingView?.findViewById<View>(R.id.vibrate).let {
            it?.setOnClickListener {
                val audioManager =
                    applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
                if (audioManager.ringerMode != AudioManager.RINGER_MODE_VIBRATE) {
                    // Device isn't in vibration mode
                    audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                    mFloatingView?.findViewById<View>(R.id.vibrate)
                        ?.setBackgroundColor(resources.getColor(android.R.color.holo_blue_bright))
                } else {
                    // Device is already in vibration mode
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    mFloatingView?.findViewById<View>(R.id.vibrate)
                        ?.setBackgroundColor(resources.getColor(android.R.color.white))
                }
            }
        }
        mFloatingView?.findViewById<View>(R.id.mute).let {
            it?.setOnClickListener {
                try {
                    // first of all, check if you have permission for writing
                    // to system settings (you need to do it for API 23+)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        !Settings.System.canWrite(applicationContext)
                    ) {
                        // Just ask the user to grant permissions
                        askForWriteSystemSettingsPermission()
                    } else {
                        //user has already granted the permission to write into settings
                        val audioManager =
                            applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
                        if (audioManager.ringerMode != AudioManager.RINGER_MODE_SILENT) {
                            // Device isn't in silent mode
                            audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                            mFloatingView?.findViewById<View>(R.id.mute)
                                ?.setBackgroundColor(resources.getColor(android.R.color.holo_blue_bright))
                        } else {
                            // Device is already in silent mode
                            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                            mFloatingView?.findViewById<View>(R.id.mute)
                                ?.setBackgroundColor(resources.getColor(android.R.color.white))
                        }


                    }
                } catch (e: SecurityException) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val audioManager =
                            applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
                        (audioManager).adjustSuggestedStreamVolume(
                            AudioManager.ADJUST_TOGGLE_MUTE,
                            AudioManager.USE_DEFAULT_STREAM_TYPE,
                            AudioManager.FLAG_SHOW_UI
                        )
                        if (audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {
                            mFloatingView?.findViewById<View>(R.id.mute)
                                ?.setBackgroundColor(resources.getColor(android.R.color.holo_blue_bright))
                        } else {
                            mFloatingView?.findViewById<View>(R.id.mute)
                                ?.setBackgroundColor(resources.getColor(android.R.color.white))
                        }
                    }
                }
            }
        }
        mFloatingView?.findViewById<View>(R.id.volume_up).let {
            it?.setOnClickListener {
                val audioManager =
                    applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.adjustSuggestedStreamVolume(
                    AudioManager.ADJUST_RAISE,
                    AudioManager.USE_DEFAULT_STREAM_TYPE,
                    AudioManager.FLAG_SHOW_UI
                )

            }
        }
        mFloatingView?.findViewById<View>(R.id.volume_down).let {
            it?.setOnClickListener {

                val audioManager =
                    applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.adjustSuggestedStreamVolume(
                    AudioManager.ADJUST_LOWER,
                    AudioManager.USE_DEFAULT_STREAM_TYPE,
                    AudioManager.FLAG_SHOW_UI
                )

            }
        }

        // Registering the seekbar for brightness control
        brightnessSeekBar = mFloatingView?.findViewById(R.id.seekBar1)
        brightnessSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // user has changed the seekbar
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !Settings.System.canWrite(applicationContext)
                    ) {
                        // Just ask the user to grant permissions
                        askForWriteSystemSettingsPermission()
                    } else {
                        // Change the brightness according to seekbar
                        Settings.System.putInt(
                            applicationContext.contentResolver,
                            Settings.System.SCREEN_BRIGHTNESS,
                            progress
                        )


                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        // registering receiver for battery status
        registerReceiver(batteryListener, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        // view should be collapsed at the start
        isViewCollapsed = true

        // Update the seekbar with the current brightness level
        // of the screen
        brightnessSeekBar?.progress = Settings.System.getInt(
            applicationContext.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            0
        )

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(batteryListener)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
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

    /**
     * Just give it the name of the app you wanna run, it fires an intent for calling that app.
     */
    @SuppressLint("QueryPermissionsNeeded")
    private fun fireIntent(searchQuery: String, pm: PackageManager, service: Service) {
        for (packageInfo in pm.getInstalledPackages(0)) {
            // going through all the installed apps on device and check their "PackageInfo"
            if ((packageInfo.packageName.lowercase(Locale.getDefault())).contains(searchQuery) ||
                packageInfo.applicationInfo.loadLabel(pm).toString()
                    .lowercase(Locale.getDefault())
                    .contains(searchQuery)
            ) {
                // Either the package or app name contain the search query
                val intent = pm.getLaunchIntentForPackage(packageInfo.packageName.toString())
                if (intent != null) {
                    service.startActivity(intent) // just open up the first match you find and get out of the function
                    break
                }
            }
        }
    }

    /**
     *  Calling this function will simply prompt the user to give us the
     *  permission to overwrite system settings.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun askForWriteSystemSettingsPermission() {
        // Just ask the user to grant permissions
        val writeSystemSettingsIntent = Intent(
            Settings.ACTION_MANAGE_WRITE_SETTINGS,
            Uri.parse("package:$packageName")
        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(writeSystemSettingsIntent)
        Toast.makeText(
            applicationContext,
            "please accept the permissions to the app first",
            Toast.LENGTH_SHORT
        ).show()
    }
}