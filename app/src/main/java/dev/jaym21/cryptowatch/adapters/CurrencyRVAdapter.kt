package dev.jaym21.cryptowatch.adapters

import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.cryptoapi.models.entities.Currency
import dev.jaym21.cryptowatch.databinding.RvCurrencyItemBinding

class CurrencyRVAdapter: ListAdapter<Currency, CurrencyRVAdapter.CurrencyViewHolder>() {



    inner class CurrencyViewHolder(val binding: RvCurrencyItemBinding): RecyclerView.ViewHolder(binding.root)
}