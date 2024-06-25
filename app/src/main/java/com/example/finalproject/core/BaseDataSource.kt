package com.example.finalproject.core

import android.content.Context
import com.example.finalproject.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseDataSource(protected val context: Context) {
    data class ErrorResponse(val detail: String?)

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()

                return if (body != null || response.code() in 200..299) {
                    Resource.Success(body!!)
                } else {
                    Resource.Error(response.message(), response.body(), response.code())
                }
            } else {
                val errorBody =
                    response.errorBody()?.string() ?: context.getString(R.string.unknown_error)

                if (errorBody.startsWith("{") && errorBody.endsWith("}")) {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter: JsonAdapter<ErrorResponse> =
                        moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(errorBody)

                    return Resource.Error(
                        errorResponse?.detail ?: context.getString(R.string.unknown_error),
                        null,
                        response.code()
                    )
                } else {
                    return Resource.Error(errorBody, null, response.code())
                }
            }
        } catch (e: SocketTimeoutException) {
            return Resource.Error(context.getString(R.string.time_out), null, null)
        } catch (e: UnknownHostException) {
            return Resource.Error(context.getString(R.string.no_internet), null, null)
        } catch (e: Exception) {
            return Resource.Error(context.getString(R.string.unknown_error), null, null)
        }
    }
}