package id.anantyan.lumutapps.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.lumutapps.data.remote.model.ResponseItem
import id.anantyan.lumutapps.databinding.ItemListBinding

/**
 * Created by Arya Rezza Anantya on 04/06/24.
 */
class ListViewAdapter : ListAdapter<ResponseItem, ListViewAdapter.ResponseItemViewHolder>(ResponseItemComparator) {

    private var _onClick: ((position: Int, item: ResponseItem) -> Unit)? = null
    private var _onLongClick: ((position: Int, item: ResponseItem) -> Unit)? = null

    private object ResponseItemComparator : DiffUtil.ItemCallback<ResponseItem>() {
        override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseItemViewHolder {
        return ResponseItemViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResponseItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ResponseItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }

            itemView.setOnLongClickListener {
                _onLongClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
                true
            }
        }

        fun bindItem(item: ResponseItem) {
            binding.tvTitle.text = item.title
        }
    }

    fun onClick(listener: (position: Int, item: ResponseItem) -> Unit) {
        _onClick = listener
    }

    fun onLongClick(listener: (position: Int, item: ResponseItem) -> Unit) {
        _onLongClick = listener
    }
}