package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.IBinder
import android.widget.RemoteViews

/**
 * Created by Hojat_Ghasemi on 13 March 2017 in Kerman.
 * Contact the author at "https://github.com/hojat72elect"
 */
class NotificationService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val c = applicationContext
        val mpm = c.packageManager
        val HELLO_ID = 10
        val favoriteApps = intent.getStringArrayExtra("ufa")
        val remote = intent.extras!!["viewgroup"] as RemoteViews


        try {
            assert(favoriteApps != null)
            remote.setImageViewBitmap(
                R.id.notifimv1, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![0]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv1, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[0]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv2, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![1]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv2, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[1]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv3, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![2]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv3, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[2]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv4, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![3]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv4, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[3]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv5, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![4]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv5, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[4]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv6, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![5]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv6, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[5]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv7, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![6]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv7, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[6]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv8, drawableToBitmap(
                    mpm.getApplicationIcon(
                        favoriteApps!![7]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv8, PendingIntent.getActivity(
                    applicationContext, 0, mpm.getLaunchIntentForPackage(
                        favoriteApps[7]
                    ), 0
                )
            )
        } catch (ignored: Exception) {
        }


        val note = Notification(R.mipmap.ic_launcher, "", System.currentTimeMillis())
        note.flags = note.flags or Notification.FLAG_NO_CLEAR
        note.contentView = remote
        startForeground(HELLO_ID, note)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
    }

    companion object {
        fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                if (drawable.bitmap != null) {
                    return drawable.bitmap
                }
            }
            val bitmap =
                if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                    Bitmap.createBitmap(
                        1,
                        1,
                        Bitmap.Config.ARGB_8888
                    ) // Single color bitmap will be created of 1x1 pixel
                } else {
                    Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}