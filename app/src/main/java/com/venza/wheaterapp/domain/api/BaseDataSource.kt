package com.venza.wheaterapp.domain.api

import com.google.android.gms.common.api.ApiException
import com.venza.wheaterapp.core.utils.NoInternetException
import com.venza.wheaterapp.data.ResultData
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResultData<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResultData.Success(body)
            }
            return ResultData.Failure(
                msg = response.code().toString() + " " + response.message().toString()
            )
        } catch (e: ApiException) {
            return ResultData.Failure(msg = e.message.toString())
        } catch (e: NoInternetException) {
            return ResultData.Internet()
        } catch (e: Exception) {
            return ResultData.Failure(msg = e.message.toString())
        }
    }
}