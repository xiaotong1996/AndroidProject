package com.example.androidtd.network


import android.content.Context
import android.preference.PreferenceManager
import com.example.androidtd.SHARED_PREF_TOKEN_KEY
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Api (private val mycontext: Context) {

    companion object {
        private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"

        lateinit var INSTANCE: Api
    }
//    private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"
//    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyNywiZXhwIjoxNjA3NDMzMDExfQ.9v3ooTab7-5dDfi46o5YzUzstvtmA7IShrkp3lywpkA"
    private val TOKEN =  PreferenceManager.getDefaultSharedPreferences(mycontext).getString(
    SHARED_PREF_TOKEN_KEY,"DEFAULT")

    private val moshi = Moshi.Builder().build()


    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userService: UserService by lazy { retrofit.create(UserService::class.java) }

    val tasksService: TasksService by lazy { retrofit.create(TasksService::class.java) }


}