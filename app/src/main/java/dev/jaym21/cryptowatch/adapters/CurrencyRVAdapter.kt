package dev.jaym21.cryptowatch.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.jaym21.cryptoapi.models.responses.Currency
import dev.jaym21.cryptowatch.R
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
        holder.binding.tvCurrencySymbol.text = currentItem.symbol
        holder.binding.tvCurrentValue.text = currentItem.price
        holder.binding.ivCurrencyIcon.load(currentItem.logoUrl)

        if (currentItem.oneDay != null) {

            if (currentItem.oneDay!!.priceChangePct!!.toDouble() >= 0) {
                holder.binding.tvPercentChange.text = currentItem.oneDay!!.priceChangePct!! + " %"

                holder.binding.ivChangeIcon.setImageResource(R.drawable.ic_trending_up)
                holder.binding.ivCurrencyIcon.setColorFilter(R.color.green)
                holder.binding.tvPercentChange.setTextColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        R.color.green
                    )
                )
            } else {
                holder.binding.tvPercentChange.text = currentItem.oneDay!!.priceChangePct!!.substring(1) + " %"

                holder.binding.ivChangeIcon.load(R.drawable.ic_trending_down)
                holder.binding.ivCurrencyIcon.setColorFilter(R.color.red)
                holder.binding.tvPercentChange.setTextColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        R.color.red
                    )
                )
            }

        } else if (currentItem.sevenDay != null) {
            holder.binding.tvPercentChange.text = currentItem.oneDay!!.priceChangePct

            if (currentItem.oneDay!!.priceChangePct!!.toDouble() >= 0) {
                holder.binding.tvPercentChange.text = currentItem.oneDay!!.priceChangePct!! + " %"

                holder.binding.ivChangeIcon.load(R.drawable.ic_trending_up)
                holder.binding.ivCurrencyIcon.setColorFilter(R.color.green)
                holder.binding.tvPercentChange.setTextColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        R.color.green
                    )
                )
            } else {
                holder.binding.tvPercentChange.text = currentItem.oneDay!!.priceChangePct!!.substring(1) + " %"

                holder.binding.ivChangeIcon.load(R.drawable.ic_trending_down)
                holder.binding.ivCurrencyIcon.setColorFilter(R.color.red)
                holder.binding.tvPercentChange.setTextColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        R.color.red
                    )
                )
            }
        }
    }
}