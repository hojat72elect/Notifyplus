package ca.sudbury.hghasemi.notifyplus;


import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Hojat Ghasemi on Saturday , 27 May 2017 in kerman.
 */
public class ApkListAdapter extends RecyclerView.Adapter<ApkListAdapter.ViewHolder> {
    @SuppressLint("StaticFieldLeak")
    public static AppsDialogFragment mAdf;
    public final PackageManager packageManager;
    public MainActivity mActivity;
    int names_to_load = 0;
    private final ArrayList<ApplicationInfo> list = new ArrayList<>();
    private final ArrayList<ApplicationInfo> list_original = new ArrayList<>();
    private final ExecutorService executorServiceNames = Executors.newFixedThreadPool(3);
    private final ExecutorService executorServiceIcons = Executors.newFixedThreadPool(3);
    private final Handler handler = new Handler();
    private final Map<String, String> cache_appName = Collections.synchronizedMap(new LinkedHashMap<>(10, 1.5f, true));
    private final Map<String, Drawable> cache_appIcon = Collections.synchronizedMap(new LinkedHashMap<>(10, 1.5f, true));

    private String search_pattern;

    public ApkListAdapter(MainActivity activity, AppsDialogFragment adf) {
        this.packageManager = activity.getPackageManager();
        mActivity = activity;
        mAdf = adf;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appnameandicon, viewGroup, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        ApplicationInfo item = list.get(i);
        holder.setPackageName(item.packageName, search_pattern);
        if (cache_appIcon.containsKey(item.packageName) && cache_appName.containsKey(item.packageName)) {
            holder.setAppName(cache_appName.get(item.packageName), search_pattern);
            holder.imgIcon.setImageDrawable(cache_appIcon.get(item.packageName));
        } else {
            holder.setAppName(item.packageName, search_pattern);
            holder.imgIcon.setImageDrawable(null);
            executorServiceIcons.submit(new GuiLoader(holder, item));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(ApplicationInfo item) {
        names_to_load++;
        executorServiceNames.submit(new AppNameLoader(item));
        list_original.add(item);
        filterListByPattern();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSearchPattern(String pattern) {
        search_pattern = pattern.toLowerCase();
        filterListByPattern();
        this.notifyDataSetChanged();
    }

    private void filterListByPattern() {
        list.clear();
        for (ApplicationInfo info : list_original) {
            boolean add = true;
            do {
                if (search_pattern == null || search_pattern.isEmpty()) {
                    break;// empty search pattern: add everything
                }

                if (cache_appName.containsKey(info.packageName) && Objects.requireNonNull(cache_appName.get(info.packageName)).toLowerCase().contains(search_pattern)) {
                    break;// search in application name
                }
                add = false;
            } while (false);

            if (add) list.add(info);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public ImageView imgIcon;
        private final TextView txtPackageName;
        private final TextView txtAppName;

        public ViewHolder(View v, ApkListAdapter adapter) {
            super(v);
            txtPackageName = v.findViewById(R.id.apppackagename);
            imgIcon = v.findViewById(R.id.appicon);
            txtAppName = v.findViewById(R.id.appname);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdf.backtomainactivity(imgIcon, txtPackageName);

        }

        public void setAppName(String name, String highlight) {
            setAndHighlight(txtAppName, name, highlight);
        }

        public void setPackageName(String name, String highlight) {
            setAndHighlight(txtPackageName, name, highlight);
        }

        private void setAndHighlight(TextView view, String value, String pattern) {
            view.setText(value);
            if (pattern == null || pattern.isEmpty()) return;// nothing to highlight

            value = value.toLowerCase();
            for (int offset = 0, index = value.indexOf(pattern, offset); index >= 0 && offset < value.length(); index = value.indexOf(pattern, offset)) {
                Spannable span = new SpannableString(view.getText());
                span.setSpan(new ForegroundColorSpan(Color.BLUE), index, index + pattern.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                view.setText(span);
                offset += index + pattern.length();
            }
        }
    }

    class AppNameLoader implements Runnable {
        private final ApplicationInfo applicationInfo;

        public AppNameLoader(ApplicationInfo info) {
            applicationInfo = info;
        }

        @Override
        public void run() {
            cache_appName.put(applicationInfo.packageName, (String) applicationInfo.loadLabel(packageManager));
            handler.post(() -> {
                names_to_load--;
                if (names_to_load == 0) mAdf.hideProgressBar();
            });
        }
    }

    class GuiLoader implements Runnable {
        private final ViewHolder viewHolder;
        private final ApplicationInfo applicationInfo;

        public GuiLoader(ViewHolder h, ApplicationInfo info) {
            viewHolder = h;
            applicationInfo = info;
        }

        @Override
        public void run() {
            boolean first = true;
            do {
                try {
                    final String appName = cache_appName.containsKey(applicationInfo.packageName)
                            ? cache_appName.get(applicationInfo.packageName)
                            : (String) applicationInfo.loadLabel(packageManager);
                    final Drawable icon = applicationInfo.loadIcon(packageManager);
                    cache_appName.put(applicationInfo.packageName, appName);
                    cache_appIcon.put(applicationInfo.packageName, icon);
                    handler.post(() -> {
                        viewHolder.setAppName(appName, search_pattern);
                        viewHolder.imgIcon.setImageDrawable(icon);
                    });


                } catch (OutOfMemoryError ex) {
                    cache_appIcon.clear();
                    cache_appName.clear();
                    if (first) {
                        first = false;
                        continue;
                    }
                }
                break;
            } while (true);
        }
    }
}
