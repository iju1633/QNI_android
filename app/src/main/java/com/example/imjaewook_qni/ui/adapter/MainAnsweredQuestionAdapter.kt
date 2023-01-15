package com.example.imjaewook_qni.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.example.imjaewook_qni.databinding.MainAnsweredQuestionRowBinding

class MainAnsweredQuestionAdapter(items: List<AnsweredQuestionDTO>) : RecyclerView.Adapter<MainAnsweredQuestionAdapter.MainViewHolder>() {

    private val items: List<AnsweredQuestionDTO>
    private lateinit var mListener: OnItemClickListener

    init {
        this.items = items
    }

    inner class MainViewHolder
    constructor(
        val binding: MainAnsweredQuestionRowBinding, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.questionBox.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: AnsweredQuestionDTO) {
            binding.questionId.text = item.question
            binding.question.text = item.question
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // When an item is pressed, it is necessary to switch to the item detail description screen, so event registration is required
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private val diffCallback =
        object : DiffUtil.ItemCallback<AnsweredQuestionDTO>() { // code to optimize the recyclerview
            override fun areItemsTheSame(oldItem: AnsweredQuestionDTO, newItem: AnsweredQuestionDTO): Boolean {
                return oldItem.questionId == newItem.questionId
            }

            override fun areContentsTheSame(oldItem: AnsweredQuestionDTO, newItem: AnsweredQuestionDTO): Boolean {
                return oldItem == newItem
            }
        }

    private val differ = AsyncListDiffer(this, diffCallback)
    var itemList: List<AnsweredQuestionDTO>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MainAnsweredQuestionRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            mListener
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = itemList[position]

        holder.apply {
            bind(item)
        }
    }

    override fun getItemCount() = itemList.size
}