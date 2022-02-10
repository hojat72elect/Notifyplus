//package ca.sudbury.hghasemi.notifyplus
//
//import android.app.Dialog
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.DialogFragment
//
///**
// * Created by Hojat Ghasemi at 2022-02-05
// * Contact the author at "https://github.com/hojat72elect"
// */
//class ButtonCountDialogFragment() : DialogFragment() {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//
//
//    }
//}
//
//
//
//
///*
//*
//*
//*
//*
//    private val TAG = "BCDF_Log"
//    lateinit var mButtonCountChangeListener: ButtonCountChangedListener
//
//    companion object {
//        @JvmStatic
//        fun newInstance() = ButtonCountDialogFragment()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val inflatedView = inflater.inflate(R.layout.dialogbuttons, container, false)
//
//        inflatedView.findViewById<View>(R.id.backlayout)
//            .let { it.setOnClickListener { dialog?.cancel() } }
//        inflatedView.findViewById<View>(R.id.mb1)
//            .let { it.setOnClickListener { returnToMainActivity(1) } }
//        inflatedView.findViewById<View>(R.id.mb2)
//            .let { it.setOnClickListener { returnToMainActivity(2) } }
//        inflatedView.findViewById<View>(R.id.mb3)
//            .let { it.setOnClickListener { returnToMainActivity(3) } }
//        inflatedView.findViewById<View>(R.id.mb4)
//            .let { it.setOnClickListener { returnToMainActivity(4) } }
//        inflatedView.findViewById<View>(R.id.mb5)
//            .let { it.setOnClickListener { returnToMainActivity(5) } }
//        inflatedView.findViewById<View>(R.id.mb6)
//            .let { it.setOnClickListener { returnToMainActivity(6) } }
//        inflatedView.findViewById<View>(R.id.mb7)
//            .let { it.setOnClickListener { returnToMainActivity(7) } }
//        inflatedView.findViewById<View>(R.id.mb8)
//            .let { it.setOnClickListener { returnToMainActivity(8) } }
//
//        return inflatedView
//
//    }
//
//    private fun returnToMainActivity(numberAppButtons: Int) {
//        mButtonCountChangeListener.buttonCountChanged(numberAppButtons)
//        dialog?.cancel()// This dialog is dismissed.
//    }
//
//    /**
//     * this interface takes the number of app buttons
//     * from this fragment back to MainActivity.
//     */
//    interface ButtonCountChangedListener {
//        fun buttonCountChanged(numButtons: Int)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            mButtonCountChangeListener = context as ButtonCountChangedListener
//        } catch (e: ClassCastException) {
//            Log.e(
//                TAG,
//                "class cast exception in ButtonCountDialogFragment.kt @onAttach ${e.message}"
//            )
//        }
//    }
//
//* */