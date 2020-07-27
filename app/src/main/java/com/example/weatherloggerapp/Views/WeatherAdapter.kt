package com.example.weatherloggerapp.Views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherloggerapp.R
import com.example.weatherloggerapp.Repositories.RoomDB.WeatherTemp
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherAdapter(val context:Context): RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {
    private var temps = emptyList<WeatherTemp>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       var view:View =LayoutInflater.from(parent.context).inflate(R.layout.weather_item,parent,false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return temps.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(temps[position])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(current:WeatherTemp){
            itemView.tv_temp_value.text=current.temp
            itemView.tv_date_value.text=current.currentdate
        }
    }
    internal fun setTemps(temps: List<WeatherTemp>) {
        this.temps = temps
        notifyDataSetChanged()
    }
}