package oryx.kortex.locateme.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.widget.Toast

class CallBackService : Service() {

    lateinit var context: Context
    override fun onBind(intent: Intent?): IBinder? {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }


    override fun onCreate() {
        context = this
        Toast.makeText(context,"created", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val address = intent.getStringExtra("address")
        Toast.makeText(context,"started $address", Toast.LENGTH_LONG).show()

        val startMain = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val phoneIntent = Intent(Intent.ACTION_CALL).apply{
            data = Uri.parse("tel:$address")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_FROM_BACKGROUND)
        }

        Thread().run {
            startActivity(phoneIntent)
            Thread.sleep(3000)
            context.startActivity(startMain)
        }

        return Service.START_STICKY
    }
}