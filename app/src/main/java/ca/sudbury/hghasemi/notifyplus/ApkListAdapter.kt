package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by Hojat Ghasemi on Saturday , 27 May 2017.
 * Contact the author at "https://github.com/hojat72elect"
 */
class ApkListAdapter(activity: MainActivity, appsDialog: AppsDialog) :
    RecyclerView.Adapter<ApkListAdapter.ViewHolder>() {

    val packageManager: PackageManager = activity.packageManager
    var namesToLoad = 0
    private val list = ArrayList<ApplicationInfo>()
    private val listOriginal = ArrayList<ApplicationInfo>()
    private val executorServiceNames = Executors.newFixedThreadPool(3)
    private val executorServiceIcons = Executors.newFixedThreadPool(3)
    private val handler = Handler()
    private val cacheAppName =
        Collections.synchronizedMap(LinkedHashMap<String, String?>(10, 1.5f, true))
    private val cacheAppIcon =
        Collections.synchronizedMap(LinkedHashMap<String, Drawable>(10, 1.5f, true))
    private var searchPattern: String? = null

    // Returns the ViewHolder which contains the UI of each row of RecyclerView
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.appnameandicon, viewGroup, false)
        )
    }

    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.setPackageName(item.packageName, searchPattern)
        if (cacheAppIcon.containsKey(item.packageName) && cacheAppName.containsKey(item.packageName)) {
            holder.setAppName(cacheAppName[item.packageName], searchPattern)
            holder.imgIcon.setImageDrawable(cacheAppIcon[item.packageName])
        } else {
            holder.setAppName(item.packageName, searchPattern)
            holder.imgIcon.setImageDrawable(null)
            executorServiceIcons.submit(GuiLoader(holder, item))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: ApplicationInfo) {
        namesToLoad++
        executorServiceNames.submit(AppNameLoader(item))
        listOriginal.add(item)
        filterListByPattern()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchPattern(pattern: String) {
        searchPattern = pattern.lowercase(Locale.getDefault())
        filterListByPattern()
        notifyDataSetChanged()
    }

    private fun filterListByPattern() {
        list.clear()
        for (info in listOriginal) {
            var add = true
            do {
                if (searchPattern == null || searchPattern!!.isEmpty()) {
                    break // empty search pattern: add everything
                }
                if (cacheAppName.containsKey(info.packageName) && cacheAppName[info.packageName]
                        ?.lowercase(Locale.getDefault())?.contains(
                            searchPattern!!
                        ) == true
                ) {
                    break // search in application name
                }
                add = false
            } while (false)
            if (add) list.add(info)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {
        var imgIcon: ImageView = v.findViewById(R.id.appicon)
        private val txtPackageName: TextView = v.findViewById(R.id.apppackagename)
        private val txtAppName: TextView = v.findViewById(R.id.appname)

        override fun onClick(v: View) {
            mAppsDialog.backToMainActivity(imgIcon, txtPackageName)
        }

        fun setAppName(name: String?, highlight: String?) {
            setAndHighlight(txtAppName, name, highlight)
        }

        fun setPackageName(name: String?, highlight: String?) {
            setAndHighlight(txtPackageName, name, highlight)
        }

        private fun setAndHighlight(view: TextView, value: String?, pattern: String?) {

            view.text = value
            if (pattern == null || pattern.isEmpty()) return  // nothing to highlight

            val lowerCaseValue = value!!.lowercase(Locale.getDefault())
            var offset = 0
            var index = lowerCaseValue.indexOf(pattern, offset)
            while (index >= 0 && offset < lowerCaseValue.length) {
                val span: Spannable = SpannableString(view.text)
                span.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    index,
                    index + pattern.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                view.text = span
                offset += index + pattern.length
                index = lowerCaseValue.indexOf(pattern, offset)
            }
        }

        init {
            v.setOnClickListener(this)
        }
    }

    internal inner class AppNameLoader(private val applicationInfo: ApplicationInfo) : Runnable {
        override fun run() {
            cacheAppName[applicationInfo.packageName] =
                applicationInfo.loadLabel(packageManager) as String
            handler.post {
                namesToLoad--
                if (namesToLoad == 0) mAppsDialog.hideProgressBar()
            }
        }
    }

    internal inner class GuiLoader(
        private val viewHolder: ViewHolder,
        private val applicationInfo: ApplicationInfo
    ) : Runnable {
        override fun run() {
            var first = true
            do {
                try {
                    val appName =
                        if (cacheAppName.containsKey(applicationInfo.packageName)) cacheAppName[applicationInfo.packageName] else applicationInfo.loadLabel(
                            packageManager
                        ) as String
                    val icon = applicationInfo.loadIcon(packageManager)
                    cacheAppName[applicationInfo.packageName] = appName
                    cacheAppIcon[applicationInfo.packageName] = icon
                    handler.post {
                        viewHolder.setAppName(appName, searchPattern)
                        viewHolder.imgIcon.setImageDrawable(icon)
                    }
                } catch (ex: OutOfMemoryError) {
                    cacheAppIcon.clear()
                    cacheAppName.clear()
                    if (first) {
                        first = false
                        continue
                    }
                }
                break
            } while (true)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mAppsDialog: AppsDialog
    }

    init {
        mAppsDialog = appsDialog
    }
}