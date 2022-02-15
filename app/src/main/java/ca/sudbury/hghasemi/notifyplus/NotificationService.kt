package ca.sudbury.hghasemi.notifyplus

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

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

        val DEFAULT_CHANNEL_ID = "Notify_Plus_Channel_ID"
        val NOTIFICATION_ID = 102
        val favoriteApps = intent.getStringArrayExtra("ufa")
        val remote = intent.extras!!["viewgroup"] as RemoteViews


        try {
            assert(favoriteApps != null)
            remote.setImageViewBitmap(
                R.id.notifimv1, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![0]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv1, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[0]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv2, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![1]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv2, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[1]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv3, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![2]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv3, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[2]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv4, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![3]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv4, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[3]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv5, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![4]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv5, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[4]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv6, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![5]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv6, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[5]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv7, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![6]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv7, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[6]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }
        try {
            remote.setImageViewBitmap(
                R.id.notifimv8, drawableToBitmap(
                    applicationContext.packageManager.getApplicationIcon(
                        favoriteApps!![7]
                    )
                )
            )
            remote.setOnClickPendingIntent(
                R.id.notifimv8, PendingIntent.getActivity(
                    applicationContext,
                    0,
                    applicationContext.packageManager.getLaunchIntentForPackage(
                        favoriteApps[7]
                    ),
                    0
                )
            )
        } catch (ignored: Exception) {
        }


        // with this one we have this error:
        // android.app.RemoteServiceException: Bad notification for startForeground


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = Notification.Builder(
                applicationContext,
                createNotificationChannel(DEFAULT_CHANNEL_ID, "Notify Plus")
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(remote) // this line is available only for SDK 24+
                .build()
            notification.flags = Notification.FLAG_NO_CLEAR or notification.flags
            startForeground(NOTIFICATION_ID, notification)
        } else {
            val notification = Notification(R.mipmap.ic_launcher, "", System.currentTimeMillis())
            notification.flags = Notification.FLAG_NO_CLEAR or notification.flags
            notification.contentView = remote
            startForeground(NOTIFICATION_ID, notification)
        }



        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
    }


    private fun drawableToBitmap(drawable: Drawable): Bitmap {
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelID: String, channelName: String): String {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelID
    }
}