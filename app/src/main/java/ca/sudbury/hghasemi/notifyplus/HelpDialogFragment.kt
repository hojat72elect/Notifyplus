package ca.sudbury.hghasemi.notifyplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 *Created by Hojat Ghasemi at 2022-02-06
 *contact the author at "https://github.com/hojat72elect"
 */
class HelpDialogFragment() : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = HelpDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflatedView = inflater.inflate(R.layout.help_dialog_fragment, container, false)
        setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar
        )

        inflatedView.findViewById<View>(R.id.backlayout).let { it.setOnClickListener { dialog?.cancel() } }

        return inflatedView
    }
}