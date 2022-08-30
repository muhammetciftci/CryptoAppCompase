package com.cryptoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.cryptoapp.model.Crypto
import com.cryptoapp.repository.CryptoRepository
import com.cryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) :ViewModel() {


    suspend fun getCrypto(id:String) : Resource<Crypto> {
        return repository.getCrypto(id)
    }


}