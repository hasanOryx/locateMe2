package oryx.tecna.locateme.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.view.Gravity
import android.widget.*

import android.widget.LinearLayout
import oryx.kortex.locateme.R
import oryx.tecna.locateme.extensons.StyleMe
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs

class RespondTo : CardView {
    lateinit var wru: RadioButton
    lateinit var secret: RadioButton

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val keyText = prefs!!.getString("keyText", "wru")

        val parent = LinearLayout(context)
       wru = RadioButton(context).apply {
            text = resources.getString(R.string.Default_wru)
            StyleMe()
        }
        secret = RadioButton(context).apply {
            text = resources.getString(R.string.Secret_wru)
            StyleMe()
        }

        var keywordAction = RadioGroup(context).apply {
            addView(wru)
            addView(secret)
            check(if (keyText == "wru") wru.id else secret.id)
        }

        parent.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                orientation = LinearLayout.VERTICAL

                addView(keywordAction).apply {
                    id = generateViewId()
                    layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    }
                }
            }
        }
        this.apply {
            addView(parent)
        }
    }
}

/*

        //These options will be the regular radio buttons
        val options = arrayOf("Option 2", "Option 3", "Option 4", "Option 5")
        for(option in options){
            val radioButton = RadioButton(this)
            radioButton.text = option
            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            textView.text = "option "+i+" is selected"
        })

android:onClick="onRadioButtonClicked"
        public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch(view.getId()) {
        case R.id.radio_pirates:
            if (checked)
                // Pirates are the best
            break;
        case R.id.radio_ninjas:
            if (checked)
                // Ninjas rule
            break;
    }
}
 */