package oryx.kortex.locateme.extensons

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.widget.Toast


fun Activity.shouldShowPermissionRationale(permission: String) =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)



fun Activity.requestPermission(permission: String, requestId: Int) =
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestId)

fun Activity.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()