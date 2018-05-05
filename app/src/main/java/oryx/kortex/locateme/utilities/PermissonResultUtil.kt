package oryx.tecna.locateme.utilities

import android.Manifest
import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.view.View
import oryx.kortex.locateme.CONTACTS_PERMISSION
import oryx.kortex.locateme.LOCATION_PERMISSION_REQUEST_ALL
import oryx.kortex.locateme.PERMISSIONS
import oryx.kortex.locateme.R
import oryx.kortex.locateme.extensons.*

object UtilPermissionsResult {
    fun getPermissionsResult(context: Context, activity: Activity?, layout: View?,
                             requestCode : Int,
                             permissions: Array<String>, grantResults: IntArray) {

        val permissionsMap = HashMap<String, Int>()
        for ((index, permission) in permissions.withIndex()) {
            permissionsMap[permission] = grantResults[index]
        }

        when(requestCode){
            LOCATION_PERMISSION_REQUEST_ALL ->
                if (permissionsMap[ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                    && permissionsMap[READ_SMS] == PackageManager.PERMISSION_GRANTED) {
                    layout!!.showSnackbar(R.string.gps_sms_granted,
                            Snackbar.LENGTH_INDEFINITE, R.string.start) {
                                 // activity!!.finish()
                         /*       if(context.isPermissionGranted(Manifest.permission.READ_CONTACTS)) {
                                   // activity!!.finish()
                                }
                                else {
                                    layout.showSnackbar(R.string.read_favorite_contacts,
                                            Snackbar.LENGTH_INDEFINITE, R.string.ok) {
                                        context.requestPermission(Manifest.permission.READ_CONTACTS,
                                                CONTACTS_PERMISSION)
                                    }
                                }
                           */ }
                } else {
                    layout!!.showSnackbar(R.string.permissions_required,
                            Snackbar.LENGTH_INDEFINITE, R.string.ok) {
                            context.batchRequestPermissions(PERMISSIONS,
                                    LOCATION_PERMISSION_REQUEST_ALL)
                            }
                }
            CONTACTS_PERMISSION -> if (permissionsMap[READ_CONTACTS] == PackageManager.PERMISSION_GRANTED) {
                                        activity!!.toast("Thanks, access to your contacts is granted")
                                    } else {
                                        activity!!.toast("it is ok, no problem, acccess is denied")
                                    }
        }
    }
}