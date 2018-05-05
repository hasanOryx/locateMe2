package oryx.kortex.locateme.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import oryx.kortex.locateme.R
import android.provider.Telephony



class NotificationService : Service() {

    lateinit var context: Context

    override fun onBind(intent: Intent?): IBinder? {
      // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun onCreate() {
        context = this
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // This is a good place to set spokenText
      //  val intent = Intent(Intent.ACTION_VIEW,
        //        Uri.parse("https://www.facebook.com/AppLocateMe"))  // fb.me/AppLocateMe
        //      Intent intent = new Intent(getApplicationContext(), AdActivity.class);


        val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this) // Need to change the build to API 19
        val nintent = Intent(Intent.ACTION_MAIN)
      //  intent.type = "text/plain"
        nintent.`package` = defaultSmsPackageName


        val pendingIntent = PendingIntent.getActivity(context, 0, nintent, 0)

       // val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mNotificationId: Int = 1000

        val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mNotification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Notification.Builder(context, CHANNEL_ID)
                                } else {
                                   Notification.Builder(context)
                                }.apply {
                                    setContentIntent(pendingIntent)
                                    setSmallIcon(R.drawable.ic_error_black_24dp)
                                    setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                                    setAutoCancel(true)
                                    setContentTitle(resources.getString(R.string.urgncy_recieved_title))
                                    setStyle(Notification.BigTextStyle()
                                            .bigText(resources.getString(R.string.urgncy_recieved_body)))
                                    setContentText(resources.getString(R.string.urgncy_recieved_body))
                                }.build()

                nManager.notify(mNotificationId, mNotification)
        return Service.START_STICKY
    }

    companion object {
        const val CHANNEL_ID = "oryx.kortex.locateme.CHANNEL_ID"
    }
}

/*
myNotify

 */

/*
class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000

    @SuppressLint("NewApi")
    private fun createChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
            notificationChannel.description = "test chanel" //getString(R.string.notification_channel_description)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {

        const val CHANNEL_ID = "samples.notification.devdeeds.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
    }


    override fun onHandleIntent(intent: Intent?) {

        //Create Channel
        createChannel()


        var timestamp: Long = 0
        if (intent != null && intent.extras != null) {
            timestamp = intent.extras!!.getLong("timestamp")
        }




        if (timestamp > 0) {


            val context = this.applicationContext
            var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this, ResultActivity::class.java)

            val title = "Sample Notification"
            val message = "You have received a sample notification. This notification will take you to the details page."

            notifyIntent.putExtra("title", title)
            notifyIntent.putExtra("message", message)
            notifyIntent.putExtra("notification", true)

            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp


            val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val res = this.resources
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                mNotification = Notification.Builder(this, CHANNEL_ID)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                      //  .setSmallIcon(R.drawable.ic_stat_name)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setStyle(Notification.BigTextStyle()
                                .bigText(message))
                        .setContentText(message).build()
            } else {

                mNotification = Notification.Builder(this)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                    //    .setSmallIcon(R.drawable.ic_stat_name)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentTitle(title)
                        .setStyle(Notification.BigTextStyle()
                                .bigText(message))
                        .setSound(uri)
                        .setContentText(message).build()

            }



            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // mNotificationId is a unique int for each notification that you must define
            notificationManager.notify(mNotificationId, mNotification)
        }


    }
}

/*
fun getAndroidChannelNotification(context: Context, title: String, body: String): Notification.Builder {
    return Notification.Builder(conntext, ANDROID_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setAutoCancel(true)
}
        */