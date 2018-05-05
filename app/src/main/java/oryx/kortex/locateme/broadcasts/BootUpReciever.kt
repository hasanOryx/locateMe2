package oryx.kortex.locateme.broadcasts

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import oryx.kortex.locateme.MainActivity
import oryx.kortex.locateme.extensons.arePermissionsGranted

class BootUpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (!context.arePermissionsGranted(oryx.kortex.locateme.PERMISSIONS)) {
            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

}