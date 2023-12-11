package com.example.weather_app

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.SuperscriptSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import get
import getN
import io.ktor.client.engine.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.roundToInt
import android.location.LocationListener
import androidx.databinding.DataBindingUtil
import com.example.weather_app.databinding.SearchBinding

import g
var items = mutableListOf<String>()
var item =mutableListOf<String>()
var p:Int=0
var ImageView: ImageView? = null
var list = mutableListOf<String>()
lateinit var adapter: CardAdapter
public  class FirstFragment : Fragment() , SearchView.OnQueryTextListener,LocationListener{
    private lateinit var binding:SearchBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter1: TextAdapter
    lateinit var card: TextView
    lateinit var card1: TextView
    lateinit var card2: TextView
    lateinit var card3: TextView
    lateinit var cardVeiw: CardView
    lateinit var relativeLayout: RelativeLayout
    private lateinit var searchView: SearchView
    var query=""
    fun item(item:MutableList<String>){
        val itemsListJson = Gson().toJson(item)
    val editor = sharedPreferences?.edit()
    editor?.putString("items_list_key", itemsListJson)
    editor?.apply()
    }
    fun getL(): MutableList<String> {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val itemsListJson = sharedPreferences.getString("items_list_key", null)
        val gson = Gson()
        val itemsListType = object : TypeToken<MutableList<String>>() {}.type
        val loadedItemsList: MutableList<String> = gson.fromJson(itemsListJson, itemsListType)
        return loadedItemsList
    }
    var sharedPreferences: SharedPreferences? =null
    var intent:Intent?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.search,container,false)
        var root= inflater.inflate(R.layout.search, container, false)
        searchView=root.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(this)
        recyclerView = root.findViewById(R.id.recycler_view)
        val layoutManager = GridLayoutManager(context, 1)
        recyclerView.layoutManager = layoutManager
        card=root.findViewById(R.id.textView)
        card.text="My Location"
        card1=root.findViewById(R.id.textView1)
        card2=root.findViewById(R.id.textView2)
        card3=root.findViewById(R.id.textView3)
        cardVeiw=root.findViewById(R.id.Card)
        relativeLayout=root.findViewById(R.id.R)
        getLocation()
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        intent = Intent(requireActivity(), Main::class.java)
        val itemsListJson = sharedPreferences?.getString("items_list_key", null)
        val i=if(itemsListJson.isNullOrEmpty()) item
        else getL()
        adapter = CardAdapter(i,this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    return root}
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 1
    fun getLocation() {
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION

        if (ContextCompat.checkSelfPermission(requireContext(), fineLocationPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(fineLocationPermission), locationPermissionCode)
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5f, this)
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        } else {
        }
    }
    override  fun onLocationChanged(location: Location){
        clat= location.latitude
        clon=location.longitude
        Log.e("Test", clat.toString())
        CoroutineScope(Dispatchers.IO).launch {
        val t3= g(clat!!, clon!!,"9ba780c3bfe8742fe3bf19773d38bfb6").weather[0].main.toString()
            val t2= g(clat!!, clon!!,"9ba780c3bfe8742fe3bf19773d38bfb6").main["temp"]?.roundToInt().toString()+"°C"
        withContext(Dispatchers.Main){
            card2.text=t2
            card3.text=t3
            if (t3=="Clouds"){
                relativeLayout.setBackgroundResource(R.drawable.cloud)
            }
            else if (t3=="Clear"){
                relativeLayout.setBackgroundResource(R.drawable.sky)
            }
            else{
                relativeLayout.setBackgroundResource(R.drawable.haze)
            }
          }
        }
        Log.e("hiiiknjbkhjvghcfgdxfgchgvhbjnbhjvghcfgxdfgchvj","lp")
    }
      fun getW(
          s: String,
          textView2: TextView,
          textView3: TextView,
          RelativeLayout: RelativeLayout,
      ) {

         CoroutineScope(Dispatchers.IO).launch {
             val t = getN(s).main["temp"]?.roundToInt().toString()+"°C"
             val string = SpannableString(t)
             string.setSpan(SuperscriptSpan(), t.length-2, t.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
             val h=getN(s).weather[0].main
             withContext(Dispatchers.Main){
                textView2.text=t
                 textView3.text=h
                 if (h.toString()=="Clouds"){
                     RelativeLayout.setBackgroundResource(R.drawable.cloud)
                 }
                 else if (h.toString()=="Clear"){
                     RelativeLayout.setBackgroundResource(R.drawable.sky)
                 }
                 else{
                     RelativeLayout.setBackgroundResource(R.drawable.haze)
                 }
             }
         }
     }

    fun handleSelectionAction(action: String) {
        when (action) {
            "delete" -> {
                val selectedItems = adapter.getSelectedItems().toList()
                selectedItems.sortedDescending().forEach { position ->
                    item.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
                adapter.clearSelection()
                item(item)
            }
        }
    }
    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (!p0.isNullOrEmpty()  ) {
            startActivity(intent)
            requireActivity().finish()
        }
        return true
    }
    fun submit(view: TextView){
        val text = view.text.toString()
        val itemsListJson = sharedPreferences?.getString("items_list_key", null)
        if(!itemsListJson.isNullOrEmpty()){
            item=getL()
        }
        item.add(text)
        item(item)
        startActivity(intent)
        requireActivity().finish()
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(p0: String?): Boolean {
        cardVeiw.visibility=View.GONE
        val query = p0?.toLowerCase(Locale.getDefault()) ?: ""
        CoroutineScope(Dispatchers.IO).launch {
            val result = get(query)
            requireActivity().runOnUiThread {
                items.clear()
                for(i in result){
                    if(items.contains(i)){
                        continue
                    }
                    else{
                        items.add(i)
                    }
                }

                recyclerView.adapter = null

                adapter1 = TextAdapter(items, this@FirstFragment)
                recyclerView.adapter = adapter1
                adapter1.notifyDataSetChanged()
            }
        }

        return true
    }
}

