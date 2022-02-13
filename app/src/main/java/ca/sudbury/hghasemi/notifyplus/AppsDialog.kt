package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * First created by Hojat Ghasemi on 16th March 2017.
 * Contact the author at "https://github.com/hojat72elect"
 */
class AppsDialog : DialogFragment() {

    private lateinit var listener: AppsDialogListener
    private var apkListAdapter: ApkListAdapter? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.maindialog, null)

        val listView = dialogView.findViewById<RecyclerView>(android.R.id.list)
        listView.layoutManager = LinearLayoutManager(activity)
        apkListAdapter = (activity as MainActivity?)?.let { ApkListAdapter(it, this) }

        listView.adapter = apkListAdapter
        progressBar = dialogView.findViewById(android.R.id.progress)
        progressBar?.visibility = View.VISIBLE

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = dialogView.findViewById<View>(R.id.searchView1) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextFocusChangeListener { _, queryTextFocused ->
            if (!queryTextFocused) {
                searchView.query.length
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                apkListAdapter!!.setSearchPattern(s)
                return true
            }
        })

        Loader(this).execute()

        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun hideProgressBar() {
        progressBar!!.visibility = View.GONE
    }

    fun addItem(item: ApplicationInfo?) {
        if (item != null) {
            apkListAdapter!!.addItem(item)
        }
    }

    // Override the DialogFragment.onAttach() method to instantiate the AppsDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AppsDialogListener so we can send events to MainActivity
            listener = context as AppsDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw an exception
            throw ClassCastException((context.toString() + "must implement AppsDialogListener"))
        }
    }

    fun backToMainActivity(imv: ImageView?, tv: TextView?) {
        listener.onAppChanged(imv, tv)
        dialog?.cancel()
    }

    interface AppsDialogListener {
        fun onAppChanged(imv: ImageView?, tv: TextView?)
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class Loader(a: AppsDialog) :
        AsyncTask<Void?, ApplicationInfo?, Void?>() {
        private var adf: AppsDialog = a

        @SuppressLint("QueryPermissionsNeeded")
        override fun doInBackground(vararg p0: Void?): Void? {
            val packages = activity!!.packageManager.getInstalledApplications(0)
            for (packageInfo in packages) {
                if (activity!!.packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null) {
                    publishProgress(packageInfo)
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: ApplicationInfo?) {
            super.onProgressUpdate(*values)
            adf.addItem(values[0])
        }

    }
}