package com.example.imjaewook_qni.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.example.imjaewook_qni.databinding.MainQuestionRowBinding

class MainQuestionAdapter(items: List<QuestionAnswerDTO>) : RecyclerView.Adapter<MainQuestionAdapter.MainViewHolder>() {


    private val items: List<QuestionAnswerDTO>
    private lateinit var mListener: OnItemClickListener

    init {
        this.items = items
    }

    inner class MainViewHolder
    constructor(
        val binding: MainQuestionRowBinding, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.questionBox.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: QuestionAnswerDTO) {
            binding.questionId.text = item.questionId.toString()
            binding.question.text = item.question
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // When an item is pressed, it is necessary to switch to the answer description screen, so event registration is required
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private val diffCallback =
        object : DiffUtil.ItemCallback<QuestionAnswerDTO>() { // code to optimize the recyclerview
            override fun areItemsTheSame(oldItem: QuestionAnswerDTO, newItem: QuestionAnswerDTO): Boolean {
                return oldItem.questionId == newItem.questionId
            }

            override fun areContentsTheSame(oldItem: QuestionAnswerDTO, newItem: QuestionAnswerDTO): Boolean {
                return oldItem == newItem
            }
        }

    private val differ = AsyncListDiffer(this, diffCallback)
    var itemList: List<QuestionAnswerDTO>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MainQuestionRowBinding.inflate(
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