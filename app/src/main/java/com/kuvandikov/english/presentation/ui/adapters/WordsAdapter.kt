package com.kuvandikov.english.presentation.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.SavedItemBinding
import com.kuvandikov.english.databinding.WordItemBinding
import com.kuvandikov.english.presentation.ui.model.Word
import com.l4digital.fastscroll.FastScroller

class WordsAdapter(var viewType: Int = DEFAULT_VIEW_TYPE) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    FastScroller.SectionIndexer {


    companion object{
        const val DEFAULT_VIEW_TYPE = 0
        const val SAVED_VIEW_TYPE = 1
    }

    private var mItems: MutableList<Word> = mutableListOf()

    var click: ((Word) -> Unit)? = null
    var clickFavorite: ((Word) -> Unit)? = null
    var clickRemoveSaved: ((Word) -> Unit)? = null


    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            0 -> {
                val itemBinding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                Holder(itemBinding,clickFavorite)
            }
            else -> {
                val itemBinding = SavedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SavedHolder(itemBinding,clickRemoveSaved)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is Holder-> holder.bind(mItems[position],position != 0)
            is SavedHolder-> holder.bind(mItems[position])
        }
    }

    fun updateItems(list: MutableList<Word>) {
        mItems = list
        notifyDataSetChanged()
    }


    inner class Holder(
        private val itemBinding: WordItemBinding,
        var clickFavorite: ((Word) -> Unit)?,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Word, dividerTopIsVisible: Boolean) {

            itemBinding.dividerTop.isVisible = dividerTopIsVisible
            itemBinding.word.text = item.word
            itemBinding.description.text = item.description
            when (item.isFavourite) {
                true -> itemBinding.remember.setColorFilter(ContextCompat.getColor(itemBinding.root.context,
                    R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN)
                else -> itemBinding.remember.setColorFilter(ContextCompat.getColor(itemBinding.root.context,
                    R.color.light_grey), android.graphics.PorterDuff.Mode.SRC_IN)
            }

            itemBinding.remember.setOnClickListener {
                clickFavorite?.invoke(item)
            }

            itemBinding.wordLayout.setOnClickListener {
                click?.invoke(item)
            }
        }
    }

    inner class SavedHolder(
        private val itemBinding: SavedItemBinding,
        var clickRemoveSaved: ((Word) -> Unit)?,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Word) {

            itemBinding.word.text = item.word
            itemBinding.description.text = item.description
            itemBinding.removeSaved.isVisible = true

            itemBinding.removeSaved.setOnClickListener {
                Log.d("TAG", "bind: setOnClickListener")
                clickRemoveSaved?.invoke(item)
            }

            itemBinding.wordLayout.setOnClickListener {
                click?.invoke(item)
            }
        }
    }



    override fun getItemCount(): Int = mItems.size

    override fun getSectionText(position: Int) = mItems[position].word[0].uppercaseChar().toString()

}