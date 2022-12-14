package com.cryptoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoapp.model.CryptoListItem
import com.cryptoapp.repository.CryptoRepository
import com.cryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    //repository
    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf()) //succes
    var errorMessage = mutableStateOf("") // error
    var isLoading = mutableStateOf(false) // loading

    private var initialCryptoList = listOf<CryptoListItem>() // downloaded data save in list
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String) {

        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        }
        else {
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            val result = listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true)
            }

            if (isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }

            cryptoList.value = result

        }


    }



     fun loadCryptos(){

         viewModelScope.launch {
             isLoading.value = true
             val result = repository.getCryptoList() // launch for this because is suspend fun

             when(result){
                 is Resource.Success -> {
                     val cryptoItems = result.data!!.mapIndexed{ index, cryptoListItem ->
                         CryptoListItem(cryptoListItem.currency,cryptoListItem.price)
                     }
                     errorMessage.value = ""
                     isLoading.value = false
                     cryptoList.value += cryptoItems

                 }

                 is Resource.Error -> {
                     errorMessage.value = result.message!!
                     isLoading.value = false
                 }
             }
         }

    }

}