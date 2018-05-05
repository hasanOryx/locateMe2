package oryx.tecna.locateme.widgets

import android.content.Context
import android.support.design.widget.Snackbar
import android.widget.Switch
import android.support.v7.widget.CardView
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs
import android.content.Intent
import android.annotation.SuppressLint
import android.net.Uri
import oryx.kortex.locateme.R


class Switchy : CardView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    @SuppressLint("MissingPermission")
    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val active = prefs!!.getBoolean("active", true)
        this.apply {
            addView(Switch(context).apply {
                setPadding(20,20,20,20)
                text = resources.getString(R.string.Activate)
                setTextAppearance(android.R.style.TextAppearance_Holo)
                isChecked = active

                setOnClickListener({
                    Snackbar.make(
                            this,
                            if (isChecked) resources.getString(R.string.Active)
                                else resources.getString(R.string.NotActive),
                            Snackbar.LENGTH_LONG
                    ).show()
                    val editor = prefs!!.edit()
                    editor.putBoolean("active", isChecked)
                    editor.apply()
                })
            }, LayoutParams(LayoutParams.MATCH_PARENT,
                    120).apply {
            })
        }
    }
}

/*
class Switchy : RelativeLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        val colorOn = -0xcdc1ba

        val trackStates = ColorStateList(
                arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf()),
                intArrayOf(Color.LTGRAY, 0)
        )

        this.apply {
            layoutParams = android.widget.RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                PrintAttributes.Margins(20, 20, 20, 20)

            }

            addView(Switch(context).apply {
                  //  background =  ContextCompat.getDrawable(context, R.drawable.card)
                    setPadding(20,20,20,20)
                    text = "Enable the app in the background"
                    textSize = 20F
                    isChecked = true
                //    showText = true
                //    textOn = "ON"
                //    textOff = "OFF"
                //    trackTintList = trackStates
                //    thumbTintList = trackStates
                }, LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT))
        }
    }
}
 */