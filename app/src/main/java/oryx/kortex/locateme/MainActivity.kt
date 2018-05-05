package oryx.kortex.locateme

import android.Manifest.permission.*
import android.content.Context
import android.graphics.Color.LTGRAY
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.*
import kotlinx.android.synthetic.main.activity_main.*
import oryx.kortex.locateme.extensons.arePermissionsGranted
import oryx.kortex.locateme.extensons.batchRequestPermissions
import oryx.tecna.locateme.utilities.UtilPermissionsResult.getPermissionsResult
import oryx.tecna.locateme.utilities.prefs
import android.support.v7.app.AlertDialog
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import android.content.Intent
import android.os.Build
import oryx.kortex.locateme.widgets.*
import oryx.kortex.locateme.utilities.NotificationUtils
import oryx.kortex.locateme.utilities.lp
import oryx.tecna.locateme.widgets.RespondTo
import oryx.tecna.locateme.widgets.Switchy
import android.speech.SpeechRecognizer

lateinit var sr: SpeechRecognizer
lateinit var srintent: Intent

val PERMISSIONS =
        arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION,
                RECEIVE_SMS, READ_SMS, SEND_SMS)
const val LOCATION_PERMISSION_REQUEST_ALL = 0
const val CONTACTS_PERMISSION = 1
const val REQUEST_CODE = 0
const val ON_DO_NOT_DISTURB_CALLBACK_CODE = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //   sr = SpeechRecognizer.createSpeechRecognizer(this)
     //   sr.setRecognitionListener(SRecognitionListener())

      //  notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      //  myAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  NotificationUtils(this)


        val self = this as Context
       // val inflater = this.layoutInflater
        val dialog = AlertDialog.Builder(this).apply {
            setTitle(R.string.permissions_required)
            setIcon(R.drawable.ic_done_all_black_24dp)
            setMessage(R.string.GPS_SMS_required)

            setPositiveButton("Confirm", { _, _ ->
                self.batchRequestPermissions(PERMISSIONS, LOCATION_PERMISSION_REQUEST_ALL)
            })
        }


        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        val keyText = prefs!!.getString("keyText", "wru")

        val switchyCard = Switchy(this).apply {
          //  PrintAttributes.Margins(100,200,200,200)
            id = generateViewId()
        }

        val record_calls = RecordCalls(this).apply {
            //  PrintAttributes.Margins(100,200,200,200)
            id = generateViewId()
        }

        val wrukeywordCard = wruKeyWord(this).apply {
            id = generateViewId()
            visibility = if (keyText == "wru") GONE else VISIBLE
        }

        val urgencyCard = Urgency(this).apply{
            id = generateViewId()
        }

        val respodto = RespondTo(this).apply {
            id = generateViewId()
        }

        val unmute = Unmute(this).apply {
            id = generateViewId()
        }

        val callBack = CallBack(this).apply{
            id = generateViewId()
        }

        respodto.wru.setOnClickListener {
            wrukeywordCard.visibility = GONE
            val editor = prefs!!.edit()
            editor.putString("keyText", null)
            editor.apply()
        }

        respodto.secret.setOnClickListener {
            wrukeywordCard.visibility = VISIBLE
            val editor = prefs!!.edit()
            editor.putString("keyText", null)
            editor.apply()
        }

     /*   val lp = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
               setMargins(0,0,0,20)
        }
*/
        main_layout.apply {
            setBackgroundColor(LTGRAY)
            setPadding(15,10,15,0)
            addView(switchyCard, lp)
            addView(record_calls, lp)
            addView(urgencyCard, lp)
            addView(respodto, lp)
            addView(wrukeywordCard, lp)
            addView(unmute, lp)
            addView(callBack, lp)
        /*    addView(CheckBox(context).apply {
                setOnCheckedChangeListener { buttonView, isChecked ->
                    when (isChecked) {
                        true -> Toast.makeText(context,"hello $isChecked", Toast.LENGTH_LONG).show()
                        false -> Toast.makeText(context,"hello $isChecked", Toast.LENGTH_LONG).show()
                    }
                }
            }) */
          //  addView(Settings(context), lp)
        }

     /*   val br = PhoneStateReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction(Intent.ACTION_   .READ_PHONE_STATE)
        this.registerReceiver(br, filter)
*/

/*
        try {
            // Initiate DevicePolicyManager.
            val mDPM = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val mAdminName = ComponentName(this, DeviceAdmin::class.java!!)

            if (!mDPM.isAdminActive(mAdminName)) {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName)
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.")
                startActivityForResult(intent, REQUEST_CODE)
            } else {
                // mDPM.lockNow();
                // Intent intent = new Intent(MainActivity.this,
                // TrackDeviceService.class);
                // startService(intent);
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

*/
        if (this.arePermissionsGranted(PERMISSIONS)) {
           // finish()
        } else {
            dialog.show()
           // val intent = Intent(this, DialogActivity::class.java)
            // To pass any data to next activity
           // intent.putExtra("keyIdentifier", value)
            // start your next activity
           // startActivity(intent)
        }
     /*       main_layout!!.showSnackbar(R.string.permissions_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
            this.batchRequestPermissions(PERMISSIONS, LOCATION_PERMISSION_REQUEST_ALL)
        } */
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        getPermissionsResult(
                this,
                this@MainActivity,
                main_layout,
                requestCode,
                permissions,
                grantResults
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ON_DO_NOT_DISTURB_CALLBACK_CODE ) {

         //   kw.visibility = View.VISIBLE
            // this.requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp();
        }
       // getActivityResult(requestCode, resultCode, data)
    }
}
