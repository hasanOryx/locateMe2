package oryx.kortex.locateme.utilities

import android.widget.Toast
import android.content.Intent
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.telephony.SmsManager

object UtilSMS {

    fun sendSMS(context: Context, phoneNo: String, msg: String, subId: Int) {

        var smsManager = SmsManager.getDefault()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            smsManager = SmsManager.getSmsManagerForSubscriptionId(subId)
        }

        val sentPI: PendingIntent
        val SENT = "SMS_SENT"

        sentPI = PendingIntent.getBroadcast(context, 0, Intent(SENT), 0)

        try {
            smsManager.sendTextMessage(phoneNo, null, msg, sentPI, null)
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(),
                    Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        }
    }
}