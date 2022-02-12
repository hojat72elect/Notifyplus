package ca.sudbury.hghasemi.notifyplus


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ca.sudbury.hghasemi.notifyplus.utils.ColorPalette
import uz.shift.colorpicker.LineColorPicker

/**
 * Created by Hojat Ghasemi at 2022-02-06
 * Contact the author at "https://github.com/hojat72elect"
 */
class ColorDialog : DialogFragment() {

    private lateinit var listener: ColorDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Loading all the UI elements
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.background_color_dialog_jadid, null)
        val primaryColorPicker = dialogView.findViewById<LineColorPicker>(R.id.pickerPrimary)
        val secondaryColorPicker = dialogView.findViewById<LineColorPicker>(R.id.pickerPrimary2)
        val colorView = dialogView.findViewById<View>(R.id.colorView)

        // Filling up color pickers and colorView with suitable colors
        primaryColorPicker.colors = ColorPalette.getBaseColors(requireActivity().applicationContext)
        secondaryColorPicker.colors =
            ColorPalette.getSecondaryColors(
                requireActivity().applicationContext,
                primaryColorPicker.color
            )
        colorView.setBackgroundColor(secondaryColorPicker.color)

        // Registering the listeners
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
                // The user has chosen and submitted the new color, we send it
                // to MainActivity and close this dialog.
                listener.onColorChanged(this, secondaryColorPicker.color)
                dialog?.cancel()
            }
        }

        // Dispatching the dialog
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*
    * MainActivity must implement this interface to receive event callbacks.
    **/
    interface ColorDialogListener {
        fun onColorChanged(dialog: DialogFragment, newColor: Int)
    }

    // Override the DialogFragment.onAttach() method to make sure the class that has started this
    // Dialog, has implemented ColorDialogListener interface.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ColorDialogListener so we can send events to MainActivity
            listener = context as ColorDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() + "must implement ColorDialogListener"))
        }
    }
}