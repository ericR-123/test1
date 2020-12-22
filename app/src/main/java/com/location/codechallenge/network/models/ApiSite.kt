package com.location.codechallenge.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class ApiSite : ArrayList<ApiSite.SiteItem>() {
    @Serializable
    data class SiteItem(
        @SerialName("name") val name: String = "",
        @SerialName("lat") val lat: Double = 0.0,
        @SerialName("long") val long: Double = 0.0,
    )
}
