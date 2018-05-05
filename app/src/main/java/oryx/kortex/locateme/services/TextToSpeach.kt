package oryx.kortex.locateme.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import java.util.*
import android.speech.tts.TextToSpeech

//val spokenText = resources.getString(R.string.unmuted)
var SPOKEN_Text: String? = null

class TTS : Service(), TextToSpeech.OnInitListener {
    private var myTTS: TextToSpeech? = null

    lateinit var context: Context

    override fun onCreate() {
        myTTS = TextToSpeech(this, this)
        context = this
        // This is a good place to set spokenText

 /*       Toast.makeText( this,
                "Service started ",
                Toast.LENGTH_LONG).show()
*/
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
          /*  Toast.makeText( this,
                    "Sucessfull intialization of Text-To-Speech engine Mytell ",
                    Toast.LENGTH_LONG).show()
          */  val result = myTTS!!.setLanguage(Locale.US)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {

                myTTS!!.apply {
 /*                   setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onDone(utteranceId: String) {
                            Toast.makeText( context,
                                    "utteranceId is $utteranceId ",
                                    Toast.LENGTH_LONG).show()
                            stopSelf()
                          //  if (utteranceId == "utteranceId") {
                          //      stopSelf()
                          //  }
                        }

                        override fun onStart(utteranceId: String) {
                            Toast.makeText( context,
                                    "utteranceId started is $utteranceId ",
                                    Toast.LENGTH_LONG).show()
                        }
                        override fun onError(utteranceId: String?, errorCode: Int) { }
                        override fun onError(utteranceId: String?) { }
                    })
*/
                    speak(SPOKEN_Text, TextToSpeech.QUEUE_FLUSH, null, null)

      /*              Thread().run {
                        speak(spokenText, TextToSpeech.QUEUE_FLUSH, null, null)
                        Thread.sleep(2000)
                        run loop@{
                            while (isSpeaking) {
                                Toast.makeText(context,
                                        "repeat",
                                        Toast.LENGTH_SHORT).show()
                            } // non-local return from the lambda passed to run
                            stopSelf()
                            return@loop
                        }
                        Toast.makeText(context,
                                "exit loop",
                                Toast.LENGTH_SHORT).show()
                    }
*/
                }
            }
        } else if (status == TextToSpeech.ERROR) {
           /* Toast.makeText(this,
                    "Unable to initialize Text-To-Speech engine",
                    Toast.LENGTH_LONG).show()
          */  stopSelf()
        }
    }

    override fun onDestroy() {
      /*  Toast.makeText(this,
                "Destroying the service",
                Toast.LENGTH_LONG).show()
      */  if (myTTS != null) {
            myTTS!!.stop()
            myTTS!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }
}