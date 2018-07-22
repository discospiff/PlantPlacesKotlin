package edu.uc.jonesbr.plantplaceskotlin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ucint on 7/22/2018.
 */

object RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "http://www.plantplaces.com/"

    // create a retrofit instance, only if it has not been created yet.
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }

            return retrofit
        }
}
