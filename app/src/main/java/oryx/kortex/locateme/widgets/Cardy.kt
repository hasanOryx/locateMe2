package oryx.tecna.locateme.widgets

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.widget.Button
import oryx.kortex.locateme.R
import android.support.constraint.ConstraintSet
import android.widget.EditText
import android.util.TypedValue
import android.widget.ImageButton

class Cardy : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {

        val r = resources
        val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200f,
                r.displayMetrics).toInt()
        val myEditText = EditText(context).apply {
            id = ConstraintLayout.generateViewId()
            width = px
        }

        val myImageButton = ImageButton(context).apply({
            background = null
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save_black_24px))
        })

        this.apply {
             layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                 setMargins(10,10,10,0)
             }
             background =  ContextCompat.getDrawable(context, R.drawable.card)
            addView(myEditText)
            addView(myImageButton)

        }

        val set = ConstraintSet().apply {
            constrainHeight(myEditText.id, ConstraintSet.WRAP_CONTENT)
            constrainWidth(myEditText.id, ConstraintSet.WRAP_CONTENT)

            constrainHeight(myImageButton.id, ConstraintSet.WRAP_CONTENT)
            constrainWidth(myImageButton.id, ConstraintSet.WRAP_CONTENT)

            connect(myImageButton.id, ConstraintSet.RIGHT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0)
        }
        set.applyTo(this)
    }
}