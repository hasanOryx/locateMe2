package oryx.tecna.locateme.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts.CONTENT_URI
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import oryx.kortex.locateme.CONTACTS_PERMISSION
import oryx.kortex.locateme.R
import oryx.kortex.locateme.extensons.isPermissionGranted
import oryx.kortex.locateme.extensons.requestPermission
import oryx.kortex.locateme.extensons.showSnackbar
import oryx.kortex.locateme.extensons.toast

import android.provider.ContactsContract.CommonDataKinds.Phone as Phone

@SuppressLint("MissingPermission")
fun getFavoriteContacts(context: Context, activity: Activity, layout: View): MutableList<String> {
    var contactID =  arrayOf("")
    lateinit var displayName : String
    lateinit var hasPhoneNumber : String
    var staredNumbers = mutableListOf<String>()
    val TAG = "Contacts";

    val selection = ContactsContract.Contacts.STARRED + "='1'"
    val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.STARRED,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    )

    val contactNumbers = arrayOf(Phone.NUMBER)

    activity.toast("reading contacts")

    if(context.isPermissionGranted(Manifest.permission.READ_CONTACTS)) {
        val cursor = context.contentResolver.query(CONTENT_URI,
                projection, selection, null, null)

        when {
            cursor.count == 0 -> Log.i(TAG, "TAG: ZERO favorite contacts fond")
            else -> Log.i(TAG, "TAG: ${cursor.count} favorite contacts found")
        }

        while (cursor.moveToNext()) {
            contactID[0] = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID))
            displayName = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME))
            hasPhoneNumber = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER))
            Log.i(TAG, "TAG: IFavourite contact read: $displayName   ")


            if (hasPhoneNumber.toInt() > 0) {
                val phoneCursor = context.contentResolver.query(
                        Phone.CONTENT_URI, contactNumbers, Phone.CONTACT_ID + "= ?",
                        contactID, null)

                while (phoneCursor.moveToNext()) {
                    val phoneNumber = phoneCursor.getString(
                            phoneCursor.getColumnIndex(Phone.NUMBER))
                    Log.i(TAG, "Number read is $phoneNumber");
                    staredNumbers.add(phoneNumber)
                }

                phoneCursor.close()
            }
        }
        cursor.close()
        Log.i(TAG, "Numbers read are $staredNumbers")

        return staredNumbers
    } else {
        layout.showSnackbar(R.string.read_favorite_contacts,
                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
            context.requestPermission(Manifest.permission.READ_CONTACTS,
                    CONTACTS_PERMISSION)
        }
        return staredNumbers
    }
}