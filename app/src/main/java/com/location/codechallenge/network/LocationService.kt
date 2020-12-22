package com.location.codechallenge.network

import com.location.codechallenge.network.models.ApiSite
import com.location.codechallenge.util.Constants.LOCATIONS_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import javax.inject.Inject

interface LocationService {
    // define the API endpoint here, use the models in network layer with Kotlin Serialization
    // Use https://raw.githubusercontent.com/simonsickle/pfj-locations/master/locations.json
    @GET(LOCATIONS_ENDPOINT)
    fun getSites(): Call<ApiSite>
}

class RetrofitServiceProvider @Inject constructor(
    private val apiService: LocationService
) : LocationService {
    override fun getSites(): Call<ApiSite> = apiService.getSites()
}
