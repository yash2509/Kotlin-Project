package com.example.weather_app
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest

var clat: Double? =null
var clon: Double? =null
class Main: AppCompatActivity(), LocationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(this, permissions, locationPermissionCode)
//        getLocation()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.e(clat.toString(),clon.toString())
    }

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 1

    // Define tvGpsLocation

    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override  fun onLocationChanged(location: Location){
        // Assuming you have a TextView with ID textView in your layout
        clat= location.latitude
        clon=location.longitude
    }
}







