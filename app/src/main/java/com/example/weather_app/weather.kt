import androidx.lifecycle.MutableLiveData
import com.example.weather_app.Main
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.math.roundToInt
@Serializable
data class WeatherResponse(
    val main: MutableMap<String, Double>,
    val weather: List<WeatherData>
)
@Serializable
data class WeatherResponse1(
    val lat: Double,
    val lon: Double,
)
@Serializable
data class WeatherResponse3(
    val main: MutableMap<String, Double>,
    val weather: List<WeatherData>,
    val dt_txt:String
)
@Serializable
data class WeatherResponse2(
    val list: List<WeatherResponse3>,
//    val weather: List<WeatherData>
)

@Serializable
data class LocationResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state:String
    // Make 'state' nullable
)
@Serializable
data class LocationResponse1(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    // Make 'state' nullable
)


@Serializable
data class WeatherData(
    val main: String,
    val description: String
)
suspend fun getWeatherForCity(cityName: String, apiKey: String): List<Any>{
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
        }
    }

    val apiUrl = "https://api.openweathermap.org/geo/1.0/direct"
    val parameters = mapOf(
        "q" to cityName,
        "appid" to apiKey,
        "limit" to "5"
    )
    val result = mutableListOf<Any>()
    try{
    var response: List<LocationResponse> = client.get(apiUrl) {
        parameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
    result.addAll(response)}
    catch (e:Exception){

    }
    try {
    var response1: List<LocationResponse1> = client.get(apiUrl) {
        parameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
    result.addAll(response1)}
    catch (e:Exception){

    }
    return result
}
val cityLiveData= MutableLiveData<List<String>>()
suspend fun get(cityName:String):List<String> {
    val apiKey = "9ba780c3bfe8742fe3bf19773d38bfb6"
    val cityNames = mutableListOf<String>()
    try {

    val weatherResponse = getWeatherForCity(cityName, apiKey)
    for(item in weatherResponse){
when(item){
    is LocationResponse->{
        cityNames.add(item.name+", "+item.state+", "+item.country)
    }
    is LocationResponse1->{
        cityNames.add(item.name+", "+item.country)
    }
}
        }

    }
    catch (e:Exception){
        println(e)
     }
    return cityNames
}
suspend fun getweather(cityName: String, apiKey: String): WeatherResponse{
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
        }
    }
    val apiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=${cityName}&limit=1&appid=${apiKey}"
    val parameters = mapOf(
        "q" to cityName,
        "appid" to apiKey,
        "units" to "metric"
    )

    val response: List<WeatherResponse1> = client.get(apiUrl)

    client.close()
    return g(response.get(0).lat,response.get(0).lon,apiKey)
}
suspend fun getweather5(cityName: String): WeatherResponse2 {
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
        }
    }
    val apiKey="9ba780c3bfe8742fe3bf19773d38bfb6"
    val apiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=${cityName}&limit=1&appid=${apiKey}"
    val parameters = mapOf(
        "q" to cityName,
        "appid" to apiKey,
        "units" to "metric"
    )

    val response: List<WeatherResponse1> = client.get(apiUrl)

    client.close()
    return get5(response.get(0).lat,response.get(0).lon,apiKey)
}
suspend fun g(lat: Double,lon: Double, apiKey: String): WeatherResponse{
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
        }
    }
    val apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${apiKey}&units=metric"
    val response: WeatherResponse = client.get(apiUrl)
    client.close()
    return response
}
suspend fun get5(lat: Double,lon: Double, apiKey: String): WeatherResponse2{
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
        }
    }
    val apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${apiKey}&units=metric"
    val response: WeatherResponse2 = client.get(apiUrl)
    client.close()
    return response
}
suspend fun getN(cityName: String): WeatherResponse {
    val res=getweather(cityName,"9ba780c3bfe8742fe3bf19773d38bfb6")
    return res
}
suspend fun main(){
    println(g(31.480584999999998,76.187155,"9ba780c3bfe8742fe3bf19773d38bfb6"))
}
