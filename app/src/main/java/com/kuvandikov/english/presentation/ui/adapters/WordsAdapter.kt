package com.kuvandikov.english.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.WordItemBinding
import com.kuvandikov.english.presentation.ui.model.Word
import com.l4digital.fastscroll.FastScroller

class WordsAdapter(var mItems: MutableList<Word>) : RecyclerView.Adapter<WordsAdapter.Holder>(),
    FastScroller.SectionIndexer {

    var clickFavorite: ((Word) -> Unit)? = null

    class Holder(private val itemBinding: WordItemBinding, var clickFavorite: ((Word) -> Unit)?) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Word) {
            itemBinding.word.text = item.word
            itemBinding.description.text = item.description
            when (item.isFavourite) {
                true -> itemBinding.remember.setColorFilter(ContextCompat.getColor(itemBinding.root.context,
                    R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
                else -> itemBinding.remember.setColorFilter(ContextCompat.getColor(itemBinding.root.context,
                    R.color.light_grey), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            itemBinding.remember.setOnClickListener {
                clickFavorite?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding =
            WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding,clickFavorite)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mItems[position])
    }

    fun updateItems(list: MutableList<Word>) {
        mItems = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = mItems.size

    override fun getSectionText(position: Int) = mItems[position].word[0].uppercaseChar().toString()

}