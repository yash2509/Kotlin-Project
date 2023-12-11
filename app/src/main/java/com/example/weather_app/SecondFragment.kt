package com.example.weather_app
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.weather_app.databinding.SecondFragmentBinding
import io.ktor.util.*


class SecondFragment() :Fragment() {
    private lateinit var viewModel: SecondViewModel
    private lateinit var binding: SecondFragmentBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.second_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        binding.viewModel=viewModel
        val item = arguments?.getString("key")
        binding.centeredTextView.text=item
       viewModel.ff(item,binding.root)
        observeViewModel()
        binding.imageView2.setOnClickListener {
            if(it.findNavController()!=null){
                it.findNavController().navigate(R.id.firstFragment)}
        }
        binding.centeredTextView.isActivated=true
        setupCustomBackHandling(requireActivity(),requireActivity(),binding.imageView2)
        return binding.root
}
    @SuppressLint("SetTextI18n")
     private fun observeViewModel() {
         viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherData ->
            binding.centeredTextView1.text = weatherData.t
            binding.centeredTextView2.text = weatherData.h
            if (weatherData.time1 > 12) {
                if (weatherData.time1 == 24) {
                    binding.textView4.text = "12 AM"
                } else {
                    weatherData.time1 = (weatherData.time1 - 12)
                    binding.textView4.text = weatherData.time1.toString() + " PM"
                }

            } else {
                binding.textView4.text = weatherData.time1.toString() + " AM"
            }
            if (weatherData.time2 > 12) {
                if (weatherData.time2 == 24) {
                    binding.textView6.text = "12 AM"
                } else {
                    weatherData.time2 = weatherData.time2 - 12
                    binding.textView6.text = weatherData.time2.toString() + " PM"
                }
            } else {
                binding.textView6.text = weatherData.time2.toString() + " AM"
            }
            if (weatherData.time3 > 12) {
                if (weatherData.time3 == 24) {
                    binding.textView8.text = "12 AM"
                } else {
                    weatherData.time3 = weatherData.time3 - 12
                    binding.textView8.text = weatherData.time3.toString() + " PM"
                }
            } else {
                binding.textView8.text = weatherData.time3.toString() + " AM"
            }
            binding.textView5.text = weatherData.temperature1
            binding.textView7.text = weatherData.temperature2
            binding.textView9.text = weatherData.temperature3
        })
    }
class CustomBackHandler(var view: View) : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {

    try {
        view.findNavController().popBackStack(R.id.secondFragment, true)
        view.findNavController().navigate(R.id.firstFragment)}
     catch (e: Exception) {
     }
}}
fun setupCustomBackHandling(
    activity: FragmentActivity,
    owner: OnBackPressedDispatcherOwner,
    root: View
) {
    val callback = CustomBackHandler(root)
    activity.onBackPressedDispatcher.addCallback(owner, callback)
}
}
