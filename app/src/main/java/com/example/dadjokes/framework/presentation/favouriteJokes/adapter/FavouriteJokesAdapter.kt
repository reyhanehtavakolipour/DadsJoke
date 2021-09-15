package com.example.dadjokes.framework.presentation.favouriteJokes.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.dadjokes.R
import com.example.dadjokes.business.domain.mode.Joke

class FavouriteJokesAdapter (private val favouriteJokesCallback: FavouriteJokesCallback): RecyclerView.Adapter<FavouriteJokesAdapter.FavouriteJokesViewHolder>() {


    private var favouriteJokes = ArrayList<Joke>()
    private var viewBinderHelper = ViewBinderHelper()


    interface FavouriteJokesCallback{
        fun deleteJokeListener(joke: Joke)
        fun jokeLongPressClickListener(joke: Joke)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteJokesViewHolder {
        return FavouriteJokesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favourite_jokes_item_rv, parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: FavouriteJokesViewHolder, position: Int) {
        val item = favouriteJokes[position]
        holder.jokeTv.text = item.joke
        handleSwipeLeft(holder = holder, item = item)
        jokeLongPressClickListener(holder, item)
    }

    override fun getItemCount(): Int {
        return favouriteJokes.size
    }


    private fun jokeLongPressClickListener(holder: FavouriteJokesViewHolder, joke: Joke)
    {
        holder.plate.setOnLongClickListener {
            favouriteJokesCallback.jokeLongPressClickListener(joke)
            true
        }
    }


    private fun handleSwipeLeft(holder: FavouriteJokesViewHolder, item: Joke)
    {
        holder.deleteJokeTv.visibility = View.VISIBLE
        viewBinderHelper.bind(holder.swipeLayout, item.id)
        viewBinderHelper.setOpenOnlyOne(true)
        holder.deleteJokeTv.setOnClickListener {
            favouriteJokesCallback.deleteJokeListener(item)
        }
    }

    fun saveState(bundle: Bundle?) {
        viewBinderHelper.saveStates(bundle)
    }


    fun restoreState(bundle: Bundle?) {
        viewBinderHelper.restoreStates(bundle)
    }

    fun deleteJoke(joke: Joke)
    {
        favouriteJokes.remove(joke)
        notifyDataSetChanged()
    }

    fun addItems(list: ArrayList<Joke>)
    {
        favouriteJokes.clear()
        favouriteJokes = list
        notifyDataSetChanged()
    }

    inner class FavouriteJokesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var jokeTv : TextView = itemView.findViewById(R.id.joke_tv)
        var deleteJokeTv: TextView = itemView.findViewById(R.id.delete_favourite_tv)
        var swipeLayout: SwipeRevealLayout = itemView.findViewById(R.id.swipe_layout)
        var plate: FrameLayout = itemView.findViewById(R.id.plate)

    }
}