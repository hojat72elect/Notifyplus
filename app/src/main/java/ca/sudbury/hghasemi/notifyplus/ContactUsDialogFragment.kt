package ca.sudbury.hghasemi.notifyplus

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 *Created by Hojat Ghasemi at 2022-02-06
 *contact the author at "https://github.com/hojat72elect"
 */
class ContactUsDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Developed by \"Hojat Ghasemi\"\n\nFollow me on social media")
                .setPositiveButton("Twitter") { _, _ ->
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/hojat_93")
                            ).setPackage("com.twitter.android")
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/hojat_93")
                            )
                        )
                    }

                }
                .setNegativeButton("GitHub") { _, _ ->
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/hojat72elect")
                            ).setPackage("com.github.android")
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/hojat72elect")
                            )
                        )
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

