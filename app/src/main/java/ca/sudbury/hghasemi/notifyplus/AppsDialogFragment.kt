package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hojat_ghasemi on 16 March 2017 in kerman.
 */
class AppsDialogFragment : DialogFragment() {
    var mlistener: DialogClicked? = null
    private var apkListAdapter: ApkListAdapter? = null
    private var progressBar: ProgressBar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        String[] a = {"a", "s"};
//        infla = inflater;
        val v = inflater.inflate(R.layout.maindialog, container, false)
        val listView: RecyclerView = v.findViewById(android.R.id.list)
        apkListAdapter = (activity as MainActivity?)?.let { ApkListAdapter(it, this) }
        listView.layoutManager = LinearLayoutManager(activity)
        listView.adapter = apkListAdapter
        progressBar = v.findViewById(android.R.id.progress)
        progressBar?.visibility = View.VISIBLE
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = v.findViewById<View>(R.id.searchView1) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextFocusChangeListener { _, queryTextFocused ->
            if (!queryTextFocused) {
                searchView.query.length
            } //bayad bebandamesh
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
        return v
    }

    fun hideProgressBar() {
        progressBar!!.visibility = View.GONE
    }

    fun addItem(item: ApplicationInfo?) {
        if (item != null) {
            apkListAdapter!!.addItem(item)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mlistener = try {
            activity as DialogClicked
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnArticleSelectedListener")
        }
    }

    fun backToMainActivity(imv: ImageView?, tv: TextView?) {
        mlistener!!.ondialogclick(imv, tv)
        dialog?.cancel() //dismisses the dialog.
    }

    interface DialogClicked {
        fun ondialogclick(imv: ImageView?, tv: TextView?)
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class Loader(a: AppsDialogFragment) :
        AsyncTask<Void?, ApplicationInfo?, Void?>() {
        var dialog: ProgressDialog
        var adf: AppsDialogFragment
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

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            dialog.dismiss()
        }

        init {
            dialog = ProgressDialog.show(
                activity,
                getString(R.string.dlg_loading_title),
                getString(R.string.dlg_loading_body)
            )
            adf = a
        }
    }

    companion object {
        fun newInstance(): AppsDialogFragment {
            return AppsDialogFragment()
        }
    }
}