package com.example.finalproject.ui.homeFragment

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemLoanBinding
import com.example.finalproject.domain.entity.LoanEntity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class LoanAdapter(val onItemClick: (id: Int) -> Unit) :
    ListAdapter<LoanEntity, LoanAdapter.ViewHolder>(LoanDiffCallback()) {
    inner class ViewHolder(private val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(item: LoanEntity) {
            val context = binding.root.context

            binding.root.setOnClickListener {
                onItemClick(item.id)
            }
            when (item.state) {
                APPROVED -> {
                    binding.statusTv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.indicator_positive
                        )
                    )
                    binding.statusTv.text = context.getString(R.string.approved)
                }

                REGISTERED -> {
                    binding.statusTv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.indicator_attention
                        )
                    )
                    binding.statusTv.text = context.getString(R.string.under_consideration)
                }

                REJECTED -> {
                    binding.statusTv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.indicator_error
                        )
                    )
                    binding.statusTv.text = context.getString(R.string.rejected)
                }
            }
            binding.idLoanTv.text = context.getString(R.string.number_of_loan, item.id.toString())
            binding.sumTv.text =
                context.getString(R.string.max_value_predel, item.amount.toInt().toString())
            binding.dateTv.text = formatDate(item.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val date = ZonedDateTime.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale.getDefault())

        return date.format(outputFormatter)
    }

    companion object {
        const val APPROVED = "APPROVED"
        const val REGISTERED = "REGISTERED"
        const val REJECTED = "REJECTED"
    }
}

class LoanDiffCallback : DiffUtil.ItemCallback<LoanEntity>() {
    override fun areItemsTheSame(oldItem: LoanEntity, newItem: LoanEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LoanEntity, newItem: LoanEntity): Boolean {
        return oldItem == newItem
    }

}