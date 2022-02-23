package ca.sudbury.hghasemi.notifyplus.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ca.sudbury.hghasemi.notifyplus.R

/**
 *Created by Hojat Ghasemi at 2022-02-06
 *contact the author at "https://github.com/hojat72elect"
 */
class ContactUsDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.dev_intro))
                .setPositiveButton("Twitter") { _, _ ->
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.twitter_handle))
                            ).setPackage("com.twitter.android")
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.twitter_handle))
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
                                Uri.parse(getString(R.string.github_handle))
                            )
                        )
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

