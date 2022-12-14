package com.venza.wheaterapp.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import com.venza.wheaterapp.BuildConfig
import com.venza.wheaterapp.core.utils.AppConstants.BASE_URL_GOOGLE_PLACES_API
import com.venza.wheaterapp.core.utils.AppConstants.REQUEST_TIME_OUT
import com.venza.wheaterapp.core.utils.AppConstants.BASE_URL_RETROFIT_API
import com.venza.wheaterapp.core.utils.AppPreferences
import com.venza.wheaterapp.domain.api.ApiGooglePlaces
import com.venza.wheaterapp.domain.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHeadersInterceptor(appPreferences: AppPreferences, @ApplicationContext context: Context) =
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer ${appPreferences.userToken ?: ""}")
                    .build()
            )
        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        logging: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor)
                .addNetworkInterceptor(logging)
                .addInterceptor(ChuckInterceptor(context))
                .build()
        } else {
            OkHttpClient.Builder()
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls() // To allow sending null values
            .create()
    }

    @Provides
    @Singleton
    fun provideHomeServices(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return getDynamicRetrofitClient(gson , okHttpClient ,BASE_URL_RETROFIT_API).create(
            ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeGooglePlaces(gson: Gson, okHttpClient: OkHttpClient): ApiGooglePlaces {
        return getDynamicRetrofitClient(gson , okHttpClient ,BASE_URL_GOOGLE_PLACES_API).create(ApiGooglePlaces::class.java)
    }

    private fun getDynamicRetrofitClient(
        gson: Gson,
        client: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}