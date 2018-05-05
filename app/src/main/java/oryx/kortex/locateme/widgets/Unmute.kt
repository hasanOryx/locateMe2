package oryx.kortex.locateme.widgets

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.support.v7.widget.CardView
import android.view.View
import android.widget.*
import oryx.kortex.locateme.*
import oryx.kortex.locateme.utilities.lp
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs
import oryx.tecna.locateme.widgets.KeyWord

class Unmute : CardView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val doUnmute = prefs!!.getBoolean("doUnmute", false)
        val keyUnmute = prefs!!.getString("keyUnmute", "")

      //  lateinit var notificationManager: NotificationManager
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val parent = LinearLayout(context)

        val kw = KeyWord(context).apply {
            visibility = if (doUnmute) View.VISIBLE else View.GONE
            keyTextFiend.hint = if (doUnmute) keyUnmute else null
        }

        val unmuteAction = CheckBox(context).apply {
            text = resources.getString(R.string.Unmute)
            this.setTextAppearance(android.R.style.TextAppearance_Holo)
            this.setPaddingRelative(10,0,0,0)
            this.setPadding(10,0,0,0)

            isChecked = doUnmute

            setOnCheckedChangeListener { _, isChecked ->
                if (notificationManager.isNotificationPolicyAccessGranted) {
                    kw.visibility = if (isChecked) View.VISIBLE else View.GONE
                } else {
                    setChecked(false)
                    /*
                    Intent i = new Intent();
                    i.setClassName("com.abc.mypackage", "com.abc.mypackage.NewActivity");
                    context.startActivity(i);
                     */
                    val settingsIntent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    context.startActivity(settingsIntent)
                }
            }
        }

        kw.saveAction.setOnClickListener({
            Toast.makeText(context, kw.keyTextFiend.text.toString(), Toast.LENGTH_LONG).show()
            val editor = prefs!!.edit()
            editor.putBoolean("doUnmute", true)
            editor.putString("keyUnmute", kw.keyTextFiend.text.toString())
            editor.apply()

        })

        parent.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                orientation = LinearLayout.VERTICAL

                addView(unmuteAction, lp)
                addView(kw, lp)
            }
        }

        this.apply {
            addView(parent)
        }

    }
}

