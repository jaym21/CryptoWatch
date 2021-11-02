package dev.jaym21.cryptowatch.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import dev.jaym21.cryptowatch.data.NomicsRepository
import dev.jaym21.cryptowatch.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val repo = NomicsRepository()

    //live data for currency response
    val currencies: MutableLiveData<ApiResponse<List<CurrencyResponse>>> = MutableLiveData()

    fun getCurrencies() = viewModelScope.launch(Dispatchers.IO) {
            //as we are going to make network call so showing loading progress bar
            currencies.postValue(ApiResponse.Loading())

            //getting response from repo
            val response = repo.getCurrencies()
            //checking if we got a successful response
            if (response!!.isNotEmpty()){
                response.let {
                    currencies.postValue(ApiResponse.Success(it))
                }
            }else {
                currencies.postValue(ApiResponse.Error("Could not retrieve currencies, try again!"))
            }
        }
}