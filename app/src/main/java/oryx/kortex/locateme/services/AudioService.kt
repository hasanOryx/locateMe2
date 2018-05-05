package oryx.kortex.locateme.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.widget.Toast

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.graphics.BitmapFactory
import oryx.kortex.locateme.R

var IS_SERVICE_RUNNING = false

class AudioService : Service(), MediaRecorder.OnInfoListener {

    lateinit var context: Context
    private var mRecorder: MediaRecorder? = null
    //setting maximum file size to be recorded
    private val Audio_MAX_FILE_SIZE: Long = 1000000//1Mb

    private var mOutputFile: File? = null
    private var mStartTime: Long = 0

    private val outputFile: File
        get() {
            val dateFormat = SimpleDateFormat("MMdd_HHmm", Locale.US)
            return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), //  Environment.DIRECTORY_DOCUMENTS, //    context.filesDir, //
                    //  .absolutePath.toString()
                    "call_"  // "/Voice Recorder/RECORDING_"
                            + dateFormat.format(Date())
                            + ".m4a")
        }

    override fun onInfo(mr: MediaRecorder?, what: Int, extra: Int) {
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
             stopRecording(true)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(context,"started recording", Toast.LENGTH_LONG).show()

        val downloadIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        val pendingIntent = PendingIntent.getActivity(context, 0, downloadIntent, 0)

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, NotificationService.CHANNEL_ID)
            } else {
                Notification.Builder(context)
            }.apply {
                setContentIntent(pendingIntent)
                setSmallIcon(R.drawable.ic_error_black_24dp)
                setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                setAutoCancel(true)
                setContentTitle(resources.getString(R.string.recording_title))
                setStyle(Notification.BigTextStyle()
                        .bigText(resources.getString(R.string.recording_body)))
                setContentText(resources.getString(R.string.recording_body))
            }.build()

        val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Start foreground service.
            startForeground(1, notification);
        } else {
            nManager.notify(1, notification)
        }

        mRecorder = MediaRecorder().apply {
            // reset()
        }
        mRecorder!!.setOnInfoListener(this)

        mOutputFile = outputFile
        mOutputFile!!.parentFile.mkdirs()

        mRecorder = MediaRecorder()
        mRecorder!!.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            //  setMaxFileSize(Audio_MAX_FILE_SIZE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            // setOutputFile(mFileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)
            setAudioEncodingBitRate(48000)
            setAudioSamplingRate(16000)
            setOutputFile(mOutputFile!!.absolutePath)
        }

        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
        } catch (ise: IllegalStateException) {
            Toast.makeText(context,"Error 1 $ise ", Toast.LENGTH_LONG).show()
        } catch (ioe: IOException) {
            Toast.makeText(context,"Error 2 $ioe ", Toast.LENGTH_LONG).show()
        }

        return Service.START_STICKY
    }

    private fun stopRecording(saveFile: Boolean) {
        Toast.makeText(context,"stopped recording ", Toast.LENGTH_LONG).show()

        mRecorder!!.apply {
            stop()
            reset()
            release()
        }
        mRecorder = null
        mStartTime = 0
        if (!saveFile && mOutputFile != null) {
            mOutputFile!!.delete()
        }

        // Stop foreground service and remove the notification.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(false)
        } else stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
      //  Toast.makeText(context,"service destroyed ", Toast.LENGTH_LONG).show()
        stopRecording(true)
    }
}