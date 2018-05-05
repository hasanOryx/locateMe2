/*
package oryx.kortex.locateme.utilities

import android.content.ContentValues.TAG
import android.os.Bundle
import android.speech.RecognitionListener
import android.util.Log
import oryx.kortex.locateme.sr
import oryx.kortex.locateme.srintent

class SRecognitionListener : RecognitionListener {
    override fun onResults(results: Bundle?) {

       // var str: String
        Log.d(TAG, "onResults $results")

     //   ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        /*   for (int i = 0; i < data.size(); i++)
           {
               Log.d(TAG, "result " + data.get(i));
               str += data.get(i);
           }
        */
        //  mText.setText("results: "+str+" "+String.valueOf(data.size()));
       // mText.setText("results: "+data.get(0));
        /* TO eanble rmsdb change trigger, use the below work around*/
        //   sr.startListening(intent);
        //   sr.cancel();

    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d(TAG, "onEvent $eventType")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.d(TAG, "onPartialResults")
    }

    override fun onRmsChanged(rmsdB: Float) {
      //  db.setText(rmsdB.toString())
      //  pb.setProgress((rmsdB.toInt())
    }

    override fun onError(error: Int) {
       // mText.setText("error " + error);
        sr.startListening(srintent)
    }

    override fun onEndOfSpeech() { }
    override fun onBufferReceived(buffer: ByteArray?) { }
    override fun onBeginningOfSpeech() { }
    override fun onReadyForSpeech(params: Bundle?) {}
}
        */