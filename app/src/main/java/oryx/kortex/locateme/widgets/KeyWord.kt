package oryx.tecna.locateme.widgets

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.text.style.BackgroundColorSpan
import android.widget.*
import oryx.kortex.locateme.LOCATION_PERMISSION_REQUEST_ALL
import oryx.kortex.locateme.PERMISSIONS
import oryx.kortex.locateme.R
import oryx.kortex.locateme.extensons.batchRequestPermissions
import oryx.tecna.locateme.utilities.PREFS_FILENAME
import oryx.tecna.locateme.utilities.prefs

open class KeyWord : LinearLayout {

    lateinit var saveAction: ImageButton
    lateinit var keyTextFiend: EditText

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {

        keyTextFiend = EditText(context).apply {
            id = CardView.generateViewId()
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            layoutParams = LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.9f).apply {
            }
        }

        saveAction = ImageButton(context).apply({
            layoutParams = LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f)
            background = null
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save_black_24px))
            id = CardView.generateViewId()

            layoutParams = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                // addRule(RelativeLayout.LEFT_OF, myImageButton.id)
            }
        })

        this.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f).apply {
                orientation = LinearLayout.HORIZONTAL
                addView(keyTextFiend)
                addView(saveAction)
            }
        }
    }
}