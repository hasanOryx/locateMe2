package oryx.kortex.locateme.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private lateinit var fusedLocationClient: FusedLocationProviderClient

object UtilLocation {
    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, callback: (Location?) -> Unit) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val l = location?.let {location}  ?: run { null }
                    callback.invoke(l)
                }
    }
}