package kerman.ir.hojat72elect.notifyplus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.widget.RemoteViews;


/**
 * Created by hojat_ghasemi on 13 March 2017 in kerman.
 */
public class NotificationService extends Service {

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        /////////////////////////////////////////////////////////////////////

        Context c = getApplicationContext();
        PackageManager mpm = c.getPackageManager();
        final int HELLO_ID = 10;

        String[] favorite_apps = (String[]) intent.getExtras().get("ufa");
        RemoteViews remote = (RemoteViews) intent.getExtras().get("viewgroup");
        int n = intent.getIntExtra("numberofappbuttons", 7);

        try {
            switch (n) {
                case 1:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    break;

                case 2:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }

                    break;

                case 3:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    break;

                case 4:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv4, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[3])));
                        remote.setOnClickPendingIntent(R.id.notifimv4, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[3]), 0));
                    } catch (Exception e) {
                    }
                    break;

                case 5:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv4, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[3])));
                        remote.setOnClickPendingIntent(R.id.notifimv4, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[3]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv5, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[4])));
                        remote.setOnClickPendingIntent(R.id.notifimv5, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[4]), 0));
                    } catch (Exception e) {
                    }
                    break;

                case 6:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv4, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[3])));
                        remote.setOnClickPendingIntent(R.id.notifimv4, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[3]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv5, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[4])));
                        remote.setOnClickPendingIntent(R.id.notifimv5, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[4]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv6, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[5])));
                        remote.setOnClickPendingIntent(R.id.notifimv6, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[5]), 0));
                    } catch (Exception e) {
                    }
                    break;

                case 7:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv4, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[3])));
                        remote.setOnClickPendingIntent(R.id.notifimv4, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[3]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv5, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[4])));
                        remote.setOnClickPendingIntent(R.id.notifimv5, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[4]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv6, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[5])));
                        remote.setOnClickPendingIntent(R.id.notifimv6, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[5]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv7, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[6])));
                        remote.setOnClickPendingIntent(R.id.notifimv7, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[6]), 0));
                    } catch (Exception e) {
                    }
                    break;

                case 8:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv4, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[3])));
                        remote.setOnClickPendingIntent(R.id.notifimv4, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[3]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv5, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[4])));
                        remote.setOnClickPendingIntent(R.id.notifimv5, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[4]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv6, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[5])));
                        remote.setOnClickPendingIntent(R.id.notifimv6, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[5]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv7, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[6])));
                        remote.setOnClickPendingIntent(R.id.notifimv7, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[6]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv8, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[7])));
                        remote.setOnClickPendingIntent(R.id.notifimv8, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[7]), 0));
                    } catch (Exception e) {
                    }
                    break;


                default:
                    try {
                        remote.setImageViewBitmap(R.id.notifimv1, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[0])));
                        remote.setOnClickPendingIntent(R.id.notifimv1, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[0]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv2, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[1])));
                        remote.setOnClickPendingIntent(R.id.notifimv2, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[1]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv3, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[2])));
                        remote.setOnClickPendingIntent(R.id.notifimv3, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[2]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv4, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[3])));
                        remote.setOnClickPendingIntent(R.id.notifimv4, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[3]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv5, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[4])));
                        remote.setOnClickPendingIntent(R.id.notifimv5, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[4]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv6, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[5])));
                        remote.setOnClickPendingIntent(R.id.notifimv6, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[5]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv7, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[6])));
                        remote.setOnClickPendingIntent(R.id.notifimv7, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[6]), 0));
                    } catch (Exception e) {
                    }
                    try {
                        remote.setImageViewBitmap(R.id.notifimv8, drawableToBitmap(mpm.getApplicationIcon(favorite_apps[7])));
                        remote.setOnClickPendingIntent(R.id.notifimv8, PendingIntent.getActivity(getApplicationContext(), 0, mpm.getLaunchIntentForPackage(favorite_apps[7]), 0));
                    } catch (Exception e) {
                    }
                    break;
            }

            /////////////////////////////////////////////////////////////////////


        } catch (Exception e) {
        }

        Notification note = new Notification(R.mipmap.ic_launcher, "", System.currentTimeMillis());

        note.flags |= Notification.FLAG_NO_CLEAR;

        note.contentView = remote;


        startForeground(HELLO_ID, note);


        return (START_NOT_STICKY);
    }//end of on start command

    @Override
    public void onDestroy() {
        stopForeground(true);
    }
}
