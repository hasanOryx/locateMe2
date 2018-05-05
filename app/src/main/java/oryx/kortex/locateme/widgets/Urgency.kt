package oryx.kortex.locateme.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.widget.CheckBox
import android.widget.LinearLayout
import oryx.kortex.locateme.R
import oryx.kortex.locateme.utilities.lp
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs

class Urgency : CardView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val urgency = prefs!!.getBoolean("Urgency", false)

        val parent = LinearLayout(context)

        val urgencyAction = CheckBox(context).apply {
            text = resources.getString(R.string.Urgency)
            this.setTextAppearance(android.R.style.TextAppearance_Holo)
            this.setPaddingRelative(10,0,0,0)
            this.setPadding(10,0,0,0)

            isChecked = urgency

            setOnCheckedChangeListener { _, isChecked ->
                val editor = prefs!!.edit()
                if (isChecked) {
                    editor.putBoolean("Urgency", true)
                    editor.apply()
                } else {
                    editor.putBoolean("Urgency", false)
                    editor.apply()
                }

            }
        }

        parent.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                orientation = LinearLayout.VERTICAL

                addView(urgencyAction, lp)
            }
        }

        this.apply {
            addView(parent)
        }

    }
}