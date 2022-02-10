package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ca.sudbury.hghasemi.notifyplus.utils.ColorPalette
import uz.shift.colorpicker.LineColorPicker

/**
 *Created by Hojat Ghasemi at 2022-02-06
 *contact the author at "https://github.com/hojat72elect"
 */
class ColorDialogFragment : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.background_color_dialog_jadid, null)

        val primaryColorPicker = dialogView.findViewById<LineColorPicker>(R.id.pickerPrimary)
        val secondaryColorPicker = dialogView.findViewById<LineColorPicker>(R.id.pickerPrimary2)
        val colorView = dialogView.findViewById<View>(R.id.colorView)


        primaryColorPicker.colors = ColorPalette.getBaseColors(requireActivity().applicationContext)
        secondaryColorPicker.colors =
            ColorPalette.getSecondaryColors(
                requireActivity().applicationContext,
                primaryColorPicker.color
            )
        colorView.setBackgroundColor(secondaryColorPicker.color)

        primaryColorPicker.setOnColorChangedListener {
            colorView.setBackgroundColor(primaryColorPicker.color)
            secondaryColorPicker.colors = ColorPalette.getSecondaryColors(
                requireActivity().applicationContext,
                primaryColorPicker.color
            )
            secondaryColorPicker.setSelectedColor(primaryColorPicker.color)
        }

        secondaryColorPicker.setOnColorChangedListener {
            colorView.setBackgroundColor(secondaryColorPicker.color)
        }

        dialogView.findViewById<View>(R.id.setColorButton).let {
            it.setOnClickListener {
                //  user wants the color to be applied, do something about it.
                //  mbuttonlistener.rangdialogclicked(colorPicker2.color)
                dialog?.cancel()
            }
        }

        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}


/*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mbuttonlistener = context as buttonclicked
        } catch (e: ClassCastException) {
            Log.e(TAG, "class cast exception in ColorDialogFragment.kt @onAttach ${e.message}")
        }
    }

    interface buttonclicked {
        fun rangdialogclicked(color: Int)
        //ma ba in interface be HomeFragmentJadidJAVA bar khahim gasht.
    }
* */