package oryx.kortex.locateme.widgets

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.support.v7.widget.CardView
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import oryx.kortex.locateme.R
import oryx.kortex.locateme.extensons.isPermissionGranted
import oryx.kortex.locateme.extensons.requestPermission
import oryx.kortex.locateme.utilities.lp
import oryx.tecna.locateme.widgets.KeyWord
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs

const val DIALER_PERMISSION = 0

class CallBack : CardView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val doCallBack = prefs!!.getBoolean("doCallBack", false)
        val keyCallBack = prefs!!.getString("keyCallBack", "")

        val parent = LinearLayout(context)
        val editor = prefs!!.edit()

        val kw = KeyWord(context).apply {
            visibility = if (doCallBack) View.VISIBLE else View.GONE
            keyTextFiend.hint = if (doCallBack) keyCallBack else null
        }

        val callBackAction = CheckBox(context).apply {
            text = resources.getString(R.string.Call_Back)
            this.setTextAppearance(android.R.style.TextAppearance_Holo)
            this.setPaddingRelative(10,0,0,0)
            this.setPadding(10,0,0,0)

            isChecked = doCallBack

            setOnCheckedChangeListener { _, isChecked ->
                kw.visibility = if (isChecked) View.VISIBLE else View.GONE

                if (!context.isPermissionGranted(CALL_PHONE)){
                    context.requestPermission(CALL_PHONE, DIALER_PERMISSION)
                    setChecked(false)
                    kw.visibility = View.GONE
                }

         /*       if (!isChecked) {
                    editor.putBoolean("doCallBack", false)
                    editor.putString("keyCallBack", null)
                    editor.apply()
                }
*/
            }
        }

        kw.saveAction.setOnClickListener({
            Toast.makeText(context, kw.keyTextFiend.text.toString(), Toast.LENGTH_LONG).show()
            editor.putBoolean("doCallBack", true)
            editor.putString("keyCallBack", kw.keyTextFiend.text.toString())
            editor.apply()
        })

        parent.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                orientation = LinearLayout.VERTICAL

                addView(callBackAction, lp)
                addView(kw, lp)
            }
        }

        this.apply {
            addView(parent)
        }

    }
}