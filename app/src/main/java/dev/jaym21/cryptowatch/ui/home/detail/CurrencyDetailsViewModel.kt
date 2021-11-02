package dev.jaym21.cryptowatch.ui.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import dev.jaym21.cryptowatch.data.NomicsRepository
import dev.jaym21.cryptowatch.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyDetailsViewModel: ViewModel() {

    private val repo = NomicsRepository()

    //live data for currency details response
    val currencyDetails: MutableLiveData<ApiResponse<List<CurrencyResponse>>> = MutableLiveData()

    fun getCurrencyDetails(id: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
        currencyDetails.postValue(ApiResponse.Loading())

        //getting response from repo
        val response = repo.getCurrencyDetails(id)
        //checking if we got a successful response
        if (response!!.isNotEmpty()) {
            response.let {
                currencyDetails.postValue(ApiResponse.Success(it))
            }
        } else {
            currencyDetails.postValue(ApiResponse.Error("Could not retrieve details, try again!"))
        }
    }
}