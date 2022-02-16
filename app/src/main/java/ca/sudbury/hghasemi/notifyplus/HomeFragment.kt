//package ca.sudbury.hghasemi.notifyplus
//
//import android.content.Intent
//import android.content.SharedPreferences
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.appcompat.widget.SwitchCompat
//import java.util.*

//class HomeFragment {
//    private var isfloatingon: SharedPreferences? = null
//    private var bftool: SwitchCompat? = null

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {

//        val result = inflater.inflate(R.layout.home_layout_jadid, container, false)
//        sadie()
//        hafez()
//        // these are all about the floating control center
//        result.findViewById<View>(R.id.floatingtool).let {
//            it.setOnClickListener {
//                // toggle the related switch
//            }
//        }
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
//        val isFloatOn = isfloatingon?.getBoolean(write_key_floating, false)
//        bftool?.isChecked = isFloatOn ?: false
//        result.findViewById<View>(R.id.floatingtool).let {
//            it.setOnClickListener {
//                bftool?.toggle()
//            }
//        }
//        return result
//    }
//
//    //calls the FloatingViewService to show the floating control center.
//    private fun parvinetesami() {
//
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
//    companion object {
//        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
//
//        //the key for writing on the shared preferences that contains the state of floating control center.
//        private const val write_key_floating = "floaton"
//    }
//}