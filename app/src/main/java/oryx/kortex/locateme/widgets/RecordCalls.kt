package oryx.kortex.locateme.widgets

import android.Manifest.permission.*
import android.content.Context
import android.support.v7.widget.CardView
import android.widget.CheckBox
import android.widget.LinearLayout
import oryx.kortex.locateme.R
import oryx.kortex.locateme.extensons.arePermissionsGranted
import oryx.kortex.locateme.extensons.batchRequestPermissions
import oryx.kortex.locateme.utilities.lp
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs

val CALL_PERMISSIONS =
        arrayOf(READ_PHONE_STATE, RECORD_AUDIO, WRITE_EXTERNAL_STORAGE)
const val CALL_RECORD_PERMISSION_REQUEST_ALL = 10

class RecordCalls : CardView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val record_calls = prefs!!.getBoolean("recordCalls", false)

        val parent = LinearLayout(context)
        val editor = prefs!!.edit()

        val recodCallsAction = CheckBox(context).apply {
            text = resources.getString(R.string.Record)
            this.setTextAppearance(android.R.style.TextAppearance_Holo)
            this.setPaddingRelative(10,0,0,0)
            this.setPadding(10,0,0,0)

            isChecked = record_calls

            setOnCheckedChangeListener { _, isChecked ->
                if (!context.arePermissionsGranted(CALL_PERMISSIONS)){
                    setChecked(false)
                    context.batchRequestPermissions(CALL_PERMISSIONS, CALL_RECORD_PERMISSION_REQUEST_ALL)
                } else {
                    editor.putBoolean("recordCalls", isChecked)
                    editor.apply()
                }
            }
        }

        parent.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                orientation = LinearLayout.VERTICAL

                addView(recodCallsAction, lp)
            }
        }

        this.apply {
            addView(parent)
        }

    }
}