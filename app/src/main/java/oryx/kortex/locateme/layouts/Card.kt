package oryx.tecna.locateme.layouts

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

/*
import oryx.kortex.locateme.R

class Card : RelativeLayout {
    private var mShowText = "Hello"
    private var otv: TextView? = null
    private var vm: ViewManager? = null

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        val cardLayout = RelativeLayout(this.context)

        //    cardLayout.setMinimumWidth(width);
        //    cardLayout.setMinimumHeight(LayoutParams.WRAP_CONTENT);

        val inflater = LayoutInflater.from(context)
        val inflatedLayout = inflater.inflate(R.layout.card, null, false)
        val closeCard = inflatedLayout.findViewById(R.id.btn_card) as Button
        //    Button closeCard = (Button) findViewById(R.id.btn_card);
        vm = closeCard.parent as ViewManager
        closeCard.setOnClickListener {
            //vm.removeView(vm);

            cardLayout.removeAllViews()
            otv!!.text = mShowText
        }


        // TextView
        otv = inflatedLayout.findViewById(R.id.tv)
        val formattedText = "This <i>is</i> a <b>test</b> of <a href='http://foo.com'>html</a>"
        // or getString(R.string.htmlFormattedText);
        //      otv.setText(Html.fromHtml(formattedText,Html.FROM_HTML_MODE_LEGACY));
        val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(resources.getString(R.string.htmlFormattedText), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(resources.getString(R.string.htmlFormattedText))
        }

     //   otv.setText(result);
        otv!!.text = mShowText

        cardLayout.addView(inflatedLayout)
        this.addView(cardLayout)

        val p1 = RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                //  ViewGroup.LayoutParams.WRAP_CONTENT
                LayoutParams.MATCH_PARENT)
        this.layoutParams = p1
    }

    fun setShowText(showText: String) {
        mShowText = showText
        otv!!.text = mShowText
    }

}
*/
