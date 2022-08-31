package com.udacity.asteroidradar.main;

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import kotlinx.android.synthetic.main.asteroiditem.view.*

class RecyclerAdapter
    : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {
     var alist= listOf<Asteroid>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.asteroiditem, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = alist.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item=alist[position]
        holder.maintxt.text="2022-08-31"
        holder.sectxt.text=item.codename
        if(item.isPotentiallyHazardous) holder.icon.setImageResource(R.drawable.ic_status_potentially_hazardous)
        else holder.icon.setImageResource(R.drawable.ic_status_normal)
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val maintxt:TextView=itemView.findViewById(R.id.txtmain)
            val sectxt:TextView=itemView.findViewById(R.id.txtsec)
            val icon:ImageView=itemView.findViewById(R.id.icon)

    }
}