package dev.jaym21.cryptowatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.cryptoapi.models.entities.Currency
import dev.jaym21.cryptowatch.databinding.RvCurrencyItemBinding

class CurrencyRVAdapter: ListAdapter<Currency, CurrencyRVAdapter.CurrencyViewHolder>(CurrencyDiffCallback()) {

    class CurrencyDiffCallback: DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }

    inner class CurrencyViewHolder(val binding: RvCurrencyItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding = RvCurrencyItemBinding.inflate(inflater)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.tvCurrencyName.text = currentItem.name
    }
}