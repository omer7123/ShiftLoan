package com.example.finalproject.core

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseDataSource {
    data class ErrorResponse(val detail: String?)

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()

                return if (body != null || response.code() in 200..299) {
                    Resource.Success(body!!)
                } else {
                    Resource.Error(response.message(), response.body())
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"

                if (errorBody.startsWith("{") && errorBody.endsWith("}")) {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(errorBody)

                    return Resource.Error(errorResponse?.detail ?: "Unknown error", null)
                } else {
                    return Resource.Error(errorBody, null)
                }
            }
        }catch (e: SocketTimeoutException){
            return Resource.Error("Время ожидаемого запроса истекло", null)
        }
        catch (e: UnknownHostException){
            return Resource.Error("Нет соединения с интернетом", null)
        }
        catch (e: Exception) {
            return Resource.Error("Произошла непредвиденная ошибка", null)
        }
    }
}