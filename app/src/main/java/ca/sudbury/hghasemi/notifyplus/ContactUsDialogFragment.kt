package ca.sudbury.hghasemi.notifyplus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.DialogFragment

/**
 *Created by Hojat Ghasemi at 2022-02-06
 *contact the author at "https://github.com/hojat72elect"
 */
class ContactUsDialogFragment : DialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ContactUsDialogFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(
            R.layout.about_app_fragment,
            container,
            false
        )

        inflatedView.findViewById<View>(R.id.btelegram).let {
            it.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/hojat72elect")
                        ).setPackage("org.telegram.messenger")
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "There was a problem in connection to telegram server!!!\nplease try again later.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        inflatedView.findViewById<View>(R.id.backlayout)
            .let { it.setOnClickListener { dialog?.cancel() } }
        inflatedView.findViewById<View>(R.id.binstagram).let {
            it.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/hojat72elect")).setPackage("com.instagram.android"))
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/hojat72elect")
                        )
                    )
                }
            }
        }
        inflatedView.findViewById<View>(R.id.t1).let {
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    requireActivity().applicationContext,
                    R.anim.dialoganim
                )
            )
        }
        inflatedView.findViewById<View>(R.id.t2).let {
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    requireActivity().applicationContext,
                    R.anim.dialoganimtwo
                )
            )
        }
        inflatedView.findViewById<View>(R.id.bp).let {
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    requireActivity().applicationContext,
                    R.anim.dialoganimthree
                )
            )
        }

        return inflatedView
    }
}