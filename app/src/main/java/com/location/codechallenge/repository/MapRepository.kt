package com.location.codechallenge.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.location.codechallenge.network.RetrofitServiceProvider
import com.location.codechallenge.network.models.ApiSite
import com.location.codechallenge.repository.models.Site
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MapRepository @Inject constructor(
    private val retrofitServiceProvider: RetrofitServiceProvider
) {
    // pull the service in, run any business logic through here so that the view model is
    // simply requesting data at this point

    suspend fun getAllLocations(): Result<List<Site>> = suspendCoroutine { continuation ->

        retrofitServiceProvider.getSites().enqueue(object : Callback<ApiSite> {
            override fun onFailure(call: Call<ApiSite>, t: Throwable) {
                continuation.resume(Result.failure(t))
            }

            override fun onResponse(call: Call<ApiSite>, response: Response<ApiSite>) {
                response.body()?.map {
                    Site(it.name, LatLng(it.lat, it.long))
                }?.let {
                    continuation.resume(Result.success(it))
                }
            }
        })
    }
}
