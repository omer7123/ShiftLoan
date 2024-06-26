package com.example.finalproject.di

import android.content.Context
import androidx.room.Room
import com.example.finalproject.data.local.encryptedSharedPref.AuthenticationSharedPrefDataSource
import com.example.finalproject.data.local.room.AppDatabase
import com.example.finalproject.data.local.room.LoanDao
import com.example.finalproject.data.remote.authentication.AuthenticationService
import com.example.finalproject.data.remote.interceptor.TokenInterceptor
import com.example.finalproject.data.remote.loan.LoanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Reusable
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideApiUrl(impl: ApiUrlProviderImpl): ApiUrlProvider = impl

    @Provides
    @Singleton
    fun provideAuthInterceptor(authDataSource: AuthenticationSharedPrefDataSource): TokenInterceptor {
        return TokenInterceptor(authDataSource)
    }

    @Reusable
    @Provides
    @Named("WithoutAuth")
    fun provideOkHttpClientWithoutAuth(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Reusable
    @Provides
    @Named("WithAuth")
    fun provideOkHttpClientWithAuth(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Reusable
    @Provides
    @Named("WithAuth")
    fun provideRetrofitClientWithAuth(
        json: Json,
        apiUrlProvider: ApiUrlProvider,
        @Named("WithAuth") okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(apiUrlProvider.url)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Reusable
    @Provides
    @Named("WithoutAuth")
    fun provideRetrofitClientWithoutAuth(
        json: Json,
        apiUrlProvider: ApiUrlProvider,
        @Named("WithoutAuth") okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(apiUrlProvider.url)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(@Named("WithoutAuth") retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoanService(@Named("WithAuth") retrofit: Retrofit): LoanService {
        return retrofit.create(LoanService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "loan_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoanDao(appDatabase: AppDatabase): LoanDao {
        return appDatabase.loanDao()
    }
}