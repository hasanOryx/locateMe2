package oryx.kortex.locateme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.app.Activity
import android.view.View
import android.widget.Button


class DialogActivity : Activity(), View.OnClickListener {

    internal lateinit var ok_btn: Button
    internal lateinit var cancel_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog)

        ok_btn = findViewById<View>(R.id.ok_btn_id) as Button
        cancel_btn = findViewById<View>(R.id.cancel_btn_id) as Button

        ok_btn.setOnClickListener(this)
        cancel_btn.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when (v.getId()) {
            R.id.ok_btn_id -> {

                showToastMessage("Ok Button Clicked")
                this.finish()
            }

            R.id.cancel_btn_id -> {

                showToastMessage("Cancel Button Clicked")
                this.finish()
            }
        }

    }

    internal fun showToastMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                .show()
    }

}
