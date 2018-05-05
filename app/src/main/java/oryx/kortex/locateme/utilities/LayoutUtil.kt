package oryx.kortex.locateme.utilities

import android.app.ActionBar
import android.widget.LinearLayout
import android.widget.RelativeLayout

val lp = LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
    setMargins(0,0,0,20)
}