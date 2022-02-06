package ca.sudbury.hghasemi.notifyplus

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ca.sudbury.hghasemi.notifyplus.utils.ColorPalette
import uz.shift.colorpicker.LineColorPicker

/**
 *Created by Hojat Ghasemi at 2022-02-06
 *contact the author at "https://github.com/hojat72elect"
 */
class ColorDialogFragment() : DialogFragment() {

    private val TAG = "ColorDF_Log"
    lateinit var colorPicker: LineColorPicker
    lateinit var colorPicker2: LineColorPicker
    lateinit var rangpallete: View
    lateinit var mbuttonlistener: buttonclicked


    companion object {
        @JvmStatic
        fun newInstance() = ColorDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.background_color_dialog_jadid, container, false)

        colorPicker = v.findViewById(R.id.pickerPrimary)
        colorPicker2 = v.findViewById(R.id.pickerPrimary2)
        rangpallete = v.findViewById(R.id.colorView)

        v.findViewById<View>(R.id.backlayout).let { it.setOnClickListener { dialog?.cancel() } }

        colorPicker.colors = ColorPalette.getBaseColors(requireActivity().applicationContext)
        colorPicker2.colors =
            ColorPalette.getSecondaryColors(requireActivity().applicationContext, colorPicker.color)
        rangpallete.setBackgroundColor(colorPicker2.color)

        colorPicker.setOnColorChangedListener {
            rangpallete.setBackgroundColor(colorPicker.color)
            colorPicker2.colors = ColorPalette.getSecondaryColors(
                requireActivity().applicationContext,
                colorPicker.color
            )
            colorPicker2.setSelectedColor(colorPicker.color)
        }

        colorPicker2.setOnColorChangedListener {
            rangpallete.setBackgroundColor(colorPicker2.color)
        }

        v.findViewById<View>(R.id.setColorButton).let {
            it.setOnClickListener {
                mbuttonlistener.rangdialogclicked(colorPicker2.color)
                dialog?.cancel()
            }
        }
        return v
    }


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
        //ma ba in interface be HomeFragmentJadid bar khahim gasht.
    }


}