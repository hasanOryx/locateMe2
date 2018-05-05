package oryx.kortex.locateme.broadcasts

import android.telephony.TelephonyManager
import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.speech.RecognizerIntent
import oryx.kortex.locateme.services.*
import oryx.kortex.locateme.sr
import oryx.kortex.locateme.srintent
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs


class PhoneStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val record_calls = prefs!!.getBoolean("recordCalls", false)
        val service = Intent(context, AudioService::class.java)

        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
       // val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        val ttsService = Intent(context, TTS::class.java)

        when(state){
            TelephonyManager.EXTRA_STATE_RINGING -> {
                         //   Toast.makeText(context, "Ringing $incomingNumber", Toast.LENGTH_LONG).show()
                                if (record_calls) {
                                    context.stopService(ttsService)
                                    SPOKEN_Text = "Hello, This call is recorded."
                                }
                            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
               // Toast.makeText(context, "IS_SERVICE_RUNNING $IS_SERVICE_RUNNING", Toast.LENGTH_LONG).show()
                            if (record_calls) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    context.startForegroundService(service)
                                } else context.startService(service)
                                IS_SERVICE_RUNNING = true

                                context.startService(ttsService)


                             //   srintent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                             //       putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                 //   putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar-JO")
                             //       putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,`package`)

                                    // putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
                                    //        50000);
                                    //     .EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000); // value to wait

                               //     putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);  // 1 is the maximum number of results to be returned.
                              //  }
                              //  sr.startListening(intent);
                            }
            }
            TelephonyManager.EXTRA_STATE_IDLE -> {
                if (IS_SERVICE_RUNNING) context.stopService(service)
                if (record_calls) {
                    context.stopService(ttsService)
                    SPOKEN_Text = null
                }
            }
            else -> Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
        }
    }
}