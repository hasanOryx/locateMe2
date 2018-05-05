package oryx.tecna.locateme.layouts

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.RelativeLayout
import oryx.kortex.locateme.R
import oryx.tecna.locateme.widgets.Cardy

class Settings : RelativeLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {

        val card1 = Cardy(context).apply {
            id = ConstraintLayout.generateViewId()
        }

        val card2 = Cardy(context).apply {
            id = ConstraintLayout.generateViewId()
        }

        val card3 = Cardy(context).apply {
            id = ConstraintLayout.generateViewId()
        }

        this.apply {
            layoutParams = android.widget.RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(10,10,10,0)
            }
         //   orientation = LinearLayout.VERTICAL
          //  background =  ContextCompat.getDrawable(context, R.drawable.card)
            addView(card1)
            addView(card2, LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT).apply {
                    addRule(RelativeLayout.BELOW, card1.id);
            })
            addView(card3, LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT).apply {
                addRule(RelativeLayout.BELOW, card2.id);
            })
           // view

        }

    }
}