package com.example.weather_app
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import getN
import getweather5
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

 class SecondViewModel :ViewModel(){
    val weatherData = MutableLiveData<WeatherData>()
    fun ff(
        item: String?,
        root: View,
    ){
        CoroutineScope(Dispatchers.IO).launch {
            var time1=(item?.let { getweather5(it).list }?.get(0)?.dt_txt?.get(11).toString()+ item?.let {
                getweather5(
                    it
                ).list
            }?.get(0)?.dt_txt?.get(12).toString()).toInt()
            var time2=(item?.let { getweather5(it).list }?.get(1)?.dt_txt?.get(11).toString() + (item?.let {
                getweather5(
                    it
                ).list
            }?.get(1)?.dt_txt?.get(12).toString())).toInt()
            var time3=(item?.let { getweather5(it).list }?.get(2)?.dt_txt?.get(11).toString() + (item?.let {
                getweather5(
                    it
                ).list
            }?.get(2)?.dt_txt?.get(12).toString())).toInt()

            var temperature1 =
                item?.let { getweather5(it).list }?.get(0)?.main?.getValue("temp")?.toInt()
                    .toString() + "°C"
            val temperature2= item?.let { getweather5(it).list }?.get(1)?.main?.getValue("temp")?.toInt().toString()+"°C"
            val temperature3= item?.let { getweather5(it).list }?.get(2)?.main?.getValue("temp")?.toInt().toString()+"°C"
            val t= item?.let { getN(it).main["temp_min"]?.roundToInt().toString() } +"°C"
            val h= item?.let { getN(it).weather }?.get(0)?.main
            withContext(Dispatchers.Main){
                if(h=="Clouds"){
                    root.setBackgroundResource(R.drawable.cl)
                }
                else{
                    root.setBackgroundResource(R.drawable.sk)
                }
                this@SecondViewModel.weatherData.value = WeatherData(time1, time2,time3,temperature1,temperature2,temperature3,t,h)
            }
        }
    }
  data class WeatherData(
      var time1: Int,
      var time2: Int,
      var time3: Int,
      val temperature1: String,
      val temperature2: String,
      val temperature3: String,
      val t: String,
      val h: String?
  )
}
