package dev.jaym21.cryptowatch.ui.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jaym21.cryptoapi.models.entities.Data
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import dev.jaym21.cryptowatch.data.CryptoCompareRepository
import dev.jaym21.cryptowatch.data.NomicsRepository
import dev.jaym21.cryptowatch.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyDetailsViewModel: ViewModel() {

    private val nomicsRepository = NomicsRepository()
    private val cryptoCompareRepository = CryptoCompareRepository()

    //live data for currency details response
    val currencyDetails: MutableLiveData<ApiResponse<List<CurrencyResponse>>> = MutableLiveData()
    //live data for currency daily history response
    val currencyDailyHistory: MutableLiveData<ApiResponse<Data>> = MutableLiveData()
    //live data for currency hourly history response
    val currencyHourlyHistory: MutableLiveData<ApiResponse<Data>> = MutableLiveData()

    fun getCurrencyDetails(id: String, convertTo: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
        currencyDetails.postValue(ApiResponse.Loading())

        //getting response from repo
        val response = nomicsRepository.getCurrencyDetails(id, convertTo)
        //checking if we got a successful response
        if (response != null) {
            response.let {
                currencyDetails.postValue(ApiResponse.Success(it))
            }
        } else {
            currencyDetails.postValue(ApiResponse.Error("Could not retrieve details, try again!"))
        }
    }

    fun getCurrencyDailyHistory(requiredCurrency: String, requiredTime: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
        currencyDailyHistory.postValue(ApiResponse.Loading())

        val response = cryptoCompareRepository.getHistoricalDailyData(requiredCurrency, requiredTime)
        //checking if we got a successful response
        if (response?.data != null) {
            response.let {
                currencyDailyHistory.postValue(ApiResponse.Success(it))
            }
        } else {
            currencyDailyHistory.postValue(ApiResponse.Error("Could retrieve historical data, try again!"))
        }
    }

    fun getCurrencyHourlyHistory(requiredCurrency: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
        currencyHourlyHistory.postValue(ApiResponse.Loading())

        val response = cryptoCompareRepository.getHistoricalHourlyData(requiredCurrency)
        //checking if we got a successful response
        if (response?.data != null) {
            response.let {
                currencyHourlyHistory.postValue(ApiResponse.Success(it))
            }
        } else {
            currencyHourlyHistory.postValue(ApiResponse.Error("Could retrieve historical data, try again!"))
        }
    }
}