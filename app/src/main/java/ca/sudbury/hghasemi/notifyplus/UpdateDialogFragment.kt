package ca.sudbury.hghasemi.notifyplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 *
 *Created by Hojat Ghasemi at 2022-02-05
 *contact the author at "https://github.com/hojat72elect"
 *
 * This class shows up a dialog to the user, prompting them to update the app
 * by going to the market. It's not fully implemented yet bcoz the app isn't
 * published anywhere.
 */
class UpdateDialogFragment() : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = UpdateDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.update_dialog_fragment, container, false)
        inflatedView.findViewById<View>(R.id.bupdate).let { it.setOnClickListener { TODO() } }
        inflatedView.findViewById<View>(R.id.bcancel).let { it.setOnClickListener { TODO() } }
        inflatedView.findViewById<View>(R.id.backlayout).let { it.setOnClickListener { TODO() } }
        return inflatedView
    }
}