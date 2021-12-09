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
    val allCurrencies: MutableLiveData<ApiResponse<List<CurrencyResponse>>> = MutableLiveData()
    var allCurrenciesDisplayed: MutableList<CurrencyResponse>? = null

    fun getCurrencies(pageNo: String, isNew: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
        currencies.postValue(ApiResponse.Loading())

        //checking if it is a new call for currencies or a paginated call
        if (isNew) {
            //if new call then clearing the previous currencies displayed list
            allCurrenciesDisplayed = null
        }
        //getting response from repo
        val response = repo.getCurrencies(pageNo)
        //checking if we got a successful response
        if (response != null){
            //storing the response received from api
            if (allCurrenciesDisplayed == null) {
                allCurrenciesDisplayed = response as MutableList<CurrencyResponse>?
            } else {
                //if this is not the first 20 currencies response received then we will add the new received currencies to the previous currencies saved
                allCurrenciesDisplayed!!.addAll(response)
            }
            currencies.postValue(ApiResponse.Success(allCurrenciesDisplayed))
        }else {
            currencies.postValue(ApiResponse.Error("Could not retrieve currencies, try again!"))
        }
    }

    fun getAllCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
    }
}