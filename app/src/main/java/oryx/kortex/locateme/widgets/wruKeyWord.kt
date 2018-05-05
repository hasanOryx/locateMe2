package oryx.kortex.locateme.widgets

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.widget.*
import oryx.kortex.locateme.R
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs

class wruKeyWord : CardView {
    lateinit var saveAction: ImageButton
    lateinit var keyText : String

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        keyText = prefs!!.getString("keyText", "wru")

        val keyTextFiend = EditText(context).apply {
            hint = if (keyText == "wru") null else keyText
            id = CardView.generateViewId()
            layoutParams = LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.9f).apply {
            }
        }

        val parent = LinearLayout(context)

        saveAction = ImageButton(context).apply({
            layoutParams = LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f)
            background = null
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save_black_24px))
            id = CardView.generateViewId()
            setOnClickListener {
                if(keyTextFiend.text.toString().replace("\\s".toRegex(), "").isEmpty()){
                    AlertDialog.Builder(context).apply {
                        setTitle(R.string.empty_wru)
                        setIcon(R.drawable.ic_error_black_24dp)
                        setMessage(R.string.Invalid_wru)
                        setPositiveButton(R.string.Retry, { _, _ -> })
                        show()
                    }} else {
                    Toast.makeText(context, "Your new keyword is: ${keyTextFiend.text}", Toast.LENGTH_SHORT).show()
                    val editor = prefs!!.edit()
                    editor.putString("keyText", keyTextFiend.text.toString())
                    editor.apply()
                }

            }
            layoutParams = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                // addRule(RelativeLayout.LEFT_OF, myImageButton.id)
            }
        })

        parent.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f).apply {
                orientation = LinearLayout.HORIZONTAL

                addView(keyTextFiend)
                addView(saveAction)
            }
        }
        this.apply {
            // PrintAttributes.Margins(40, 40, 40, 40)
            addView(parent)
        }
    }
}