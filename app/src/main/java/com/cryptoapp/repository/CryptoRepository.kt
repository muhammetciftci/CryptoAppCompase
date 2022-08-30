package com.cryptoapp.repository

import com.cryptoapp.model.Crypto
import com.cryptoapp.model.CryptoList
import com.cryptoapp.service.CryptoAPI
import com.cryptoapp.util.Constans.API_KEY
import com.cryptoapp.util.Constans.CALL_ATTRIBUTES
import com.cryptoapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped //
class CryptoRepository @Inject constructor(
    private val api:CryptoAPI
) {

    suspend fun getCryptoList(): Resource<CryptoList>{
        val response = try {
            api.getCryptoList(API_KEY)
        }
        catch (e:Exception)
        {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id:String) : Resource<Crypto> {
        val response =
        try
        {
            api.getCrypto(API_KEY,id, CALL_ATTRIBUTES)
        }
        catch (e:Exception)
        {
            return Resource.Error("Error")
        }

        return Resource.Success(response)
    }



}