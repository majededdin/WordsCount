package com.example.wordscount.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordscount.data.model.Word
import com.example.wordscount.databinding.ItemWordBinding

class WordsAdapter : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    private var items: ArrayList<Word> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.txtWord.text = word
                binding.txtWordCount.text = count.toString()
            }
        }
    }


    fun addAll(items: List<Word>) {
        for (result in items) {
            add(result)
        }
    }


    private fun add(r: Word) {
        items.add(r)
        notifyItemInserted(items.size - 1)
    }


    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size


    inner class ViewHolder(val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root)

}