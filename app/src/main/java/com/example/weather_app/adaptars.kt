package com.example.weather_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.HashSet

class CardAdapter(private var items: MutableList<String>, private val mainActivity: FirstFragment) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private var selectedItems = HashSet<Int>()
    private var selected:Int = -1
    private var removed:Int=0
    private var old_holder: ViewHolder? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        Log.e("hi",p.toString())
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(items[position])
        holder.itemView.isActivated=selected.equals(position)
        Log.e("yaaaaaaaaaaaash",position.toString())
        val bundle= Bundle()
        bundle.putString("key",items[position])
        holder.itemView.setOnClickListener{view->
            view.findNavController().popBackStack(R.id.firstFragment, true)
            view.findNavController().navigate(R.id.secondFragment,bundle)
        }
        holder.itemView.setOnLongClickListener { view ->
            var old_selected = selected
            selected=position
            removed=items.size
            view.isActivated=false
            Log.e(position.toString(),position.toString())
            if(old_selected!=-1){
                onBindViewHolder(old_holder!!,old_selected)}
            onBindViewHolder(holder,selected)
            old_holder=holder
            true}
        ImageView=holder.itemView.findViewById(R.id.i1)
        if(holder.itemView.isActivated){
            ImageView!!.setImageResource(R.drawable.baseline_delete_24)
            ImageView!!.setBackgroundResource(android.R.color.transparent)
            ImageView!!.setOnClickListener{
                holder.itemView.isActivated=false
                selectedItems.remove(position)
                selected=-1
                list.addAll(items)
                items.removeAt(position)
                Log.e(position.toString(),position.toString())
                Log.e(list.size.toString(),items.size.toString())
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,items.size)
                mainActivity.item(items)

            }
        }
        else{
            Log.e("joooo","jo")
            ImageView!!.setImageResource(0)
        }


    }
    override fun getItemCount(): Int = items.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val RelativeLayout: RelativeLayout =itemView.findViewById(R.id.R)
        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val textView1: TextView = itemView.findViewById(R.id.textView1)
        private val textView2: TextView = itemView.findViewById(R.id.textView2)
        private val textView3: TextView = itemView.findViewById(R.id.textView3)
        fun bind(item: String) {
            var string=""
            var string1=""
            for(i in item){
                if(i==','){
                    break
                }
                else{
                    string+=i
                }
            }
            textView.text = string
            for(i in string.length+1 until item.length){
                string1+=item[i]
            }
            textView1.text=string1
            Log.e(item,"Called")
            mainActivity.getW(item,textView2,textView3,RelativeLayout)
        }
    }
    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): Set<Int> {
        return selectedItems
    }
}
class TextAdapter(private val items: List<String>,private val mainActivity: FirstFragment) : RecyclerView.Adapter<TextAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_cities, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener{
            mainActivity.submit(holder.itemView as TextView)
        }

    }


    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(item: String) {
            textView.text = item
        }
    }
}










