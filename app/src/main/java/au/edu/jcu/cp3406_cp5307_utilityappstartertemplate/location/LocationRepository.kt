package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float?
)

class LocationRepository(context: Context) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context.applicationContext)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): UserLocation? {
        val cancellationTokenSource = CancellationTokenSource()

        val location = fusedLocationClient
            .getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                cancellationTokenSource.token
            )
            .await()
            ?: fusedLocationClient.lastLocation.await()

        return location?.let {
            UserLocation(
                latitude = it.latitude,
                longitude = it.longitude,
                accuracyMeters = if (it.hasAccuracy()) it.accuracy else null
            )
        }
    }
}

