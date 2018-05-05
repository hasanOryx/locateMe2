package oryx.kortex.locateme.broadcasts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Vibrator
import android.telephony.SmsMessage
import oryx.kortex.locateme.utilities.UtilLocation.getLocation
import oryx.kortex.locateme.utilities.UtilSMS.sendSMS
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs
import java.util.*
import android.widget.Toast
import oryx.kortex.locateme.objects.urgent
import oryx.kortex.locateme.objects.wru
import oryx.kortex.locateme.services.CallBackService
import oryx.kortex.locateme.services.TTS

import oryx.kortex.locateme.services.NotificationService
import oryx.kortex.locateme.services.SPOKEN_Text


const val SMS_BUNDLE = "pdus"

class SMSReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val active = prefs!!.getBoolean("active", true)
        val keyText = prefs!!.getString("keyText", "wru")

        val doUnmute = prefs!!.getBoolean("doUnmute", false)
        val keyUnmute = prefs!!.getString("keyUnmute", "")

        val doCallBack = prefs!!.getBoolean("doCallBack", false)
        val keyCallBack = prefs!!.getString("keyCallBack", "")

        val urgency = prefs!!.getBoolean("Urgency", false)

        //val ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
       // val r = RingtoneManager.getRingtone(context, ring)
        val ttsService = Intent(context, TTS::class.java)
        val callService = Intent(context, CallBackService::class.java)
        val notificationService = Intent(context, NotificationService::class.java)

        if(active){
            val intentExtras = intent.extras
            //   val slot = intentExtras.getInt("slot", -1)
            val subId = intentExtras.getInt("subscription", -1)
            val sms = intentExtras.get(SMS_BUNDLE) as Array<*>

            (0 until sms.size).forEach { i ->
                val format = intentExtras.getString("format")
                val smsMessage = SmsMessage.createFromPdu(
                        sms[i] as ByteArray,
                        format
                )

                val smsBody = smsMessage.messageBody.toString()
                val address = smsMessage.originatingAddress

                val unrgency_found = urgent.find(smsBody)?.value.orEmpty()
                if (urgency && unrgency_found.isNotEmpty()) {
                  //  context.stopService(notificationService)
                    context.startService(notificationService)
                }

                val unmute_found = keyUnmute.toRegex(RegexOption.IGNORE_CASE).find(smsBody)?.value.orEmpty()
                if (doUnmute && unmute_found.isNotEmpty()) {
                    context.stopService(ttsService)
                    SPOKEN_Text = "Hello, you had been unmuted"

                    val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                    when(audio.ringerMode){
                        AudioManager.RINGER_MODE_SILENT,
                        AudioManager.RINGER_MODE_VIBRATE -> {
                            audio.apply {
                                vibrator.cancel()
                                ringerMode = AudioManager.RINGER_MODE_NORMAL
                                adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0)
                                adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, 0)
                                val midVol = audio.getStreamMaxVolume(AudioManager.STREAM_RING) / 2
                                setStreamVolume(AudioManager.STREAM_RING, midVol,0)
                                context.startService(ttsService)
                                // r.play()
                            }
                        }
                     //   else -> null
                    }
                }

                val callBack_found = keyCallBack.toRegex(RegexOption.IGNORE_CASE).find(smsBody)?.value.orEmpty()
                if (doCallBack && callBack_found.isNotEmpty()) {
                    Toast.makeText(context,"call-cabk $address", Toast.LENGTH_LONG).show()
                    callService.putExtra("address", address)
                  //  context.stopService(notificationService)
                    context.startService(callService)
                    /*
                    val dial = "tel:$address"
                    val phoneIntent = Intent(Intent.ACTION_CALL, Uri.parse(dial))
                    val startMain = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_HOME)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(phoneIntent)
                 //    Thread().run {
                   //     context.startActivity(phoneIntent)
                      //  Thread.sleep(3000)
                      //  context.startActivity(startMain)
                  //  }
                  */
                }

                val wruMatchResult = if (keyText == "wru") wru.find(smsBody)
                                    else {
                 //   val savedSecret =
                            keyText.toRegex(RegexOption.IGNORE_CASE).find(smsBody)
                  //  savedSecret.find(smsBody)
                }

                val wru_found = wruMatchResult?.value.orEmpty()
                if (wru_found.isNotEmpty()){
                    getLocation(context) { location ->
                        val msg = location?.let {
                            "As of: ${Date(it.time)}, " +
                                    "I am at: http://maps.google.com/?q=" +
                                    "${it.latitude},${it.longitude} "
                        } ?: run {
                            "Sorry, it looks GPS is off, no location found\""
                        }

                        sendSMS(context, address, msg, subId)
                    }
                }
            }
        }
    }
}