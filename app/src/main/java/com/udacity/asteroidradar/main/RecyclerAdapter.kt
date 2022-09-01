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

class RecyclerAdapter(private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {
     var alist= listOf<Asteroid>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.asteroiditem, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount() = alist.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item=alist[position]
        holder.sectxt.text=item.closeApproachDate
        holder.maintxt.text=item.codename
        if(item.isPotentiallyHazardous) holder.icon.setImageResource(R.drawable.ic_status_potentially_hazardous)
        else holder.icon.setImageResource(R.drawable.ic_status_normal)
        holder.itemView.setOnClickListener {
            onClickListener.clickListener(item)
        }
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val maintxt:TextView=itemView.findViewById(R.id.txtmain)
            val sectxt:TextView=itemView.findViewById(R.id.txtsec)
            val icon:ImageView=itemView.findViewById(R.id.img)

    }

    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}