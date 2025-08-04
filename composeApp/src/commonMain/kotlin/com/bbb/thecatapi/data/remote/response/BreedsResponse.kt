package com.bbb.thecatapi.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedsResponse(
    val adaptability: Int = 0,
    val description: String = "",
    val experimental: Int = 0,
    val grooming: Int = 0,
    val hairless: Int = 0,
    val hypoallergenic: Int = 0,
    val id: String = "",
    val indoor: Int = 0,
    val intelligence: Int = 0,
    val lap: Int = 0,
    val name: String = "",
    val natural: Int = 0,
    val origin: String = "",
    val rare: Int = 0,
    val rex: Int = 0,
    val temperament: String = "",
    val vocalisation: Int = 0,
    val image: ImageBreedsResponse = ImageBreedsResponse(),
    @SerialName("affection_level") val affectionLevel: Int = 0,
    @SerialName("alt_names") val altNames: String = "",
    @SerialName("cfa_url") val cfaUrl: String = "",
    @SerialName("child_friendly") val childFriendly: Int = 0,
    @SerialName("country_code") val countryCode: String = "",
    @SerialName("country_codes") val countryCodes: String = "",
    @SerialName("dog_friendly") val dogFriendly: Int = 0,
    @SerialName("energy_level") val energyLevel: Int = 0,
    @SerialName("health_issues") val healthIssues: Int = 0,
    @SerialName("life_span") val lifeSpan: String = "",
    @SerialName("reference_image_id") val referenceImageId: String = "",
    @SerialName("shedding_level") val sheddingLevel: Int = 0,
    @SerialName("short_legs") val shortLegs: Int = 0,
    @SerialName("social_needs") val socialNeeds: Int = 0,
    @SerialName("stranger_friendly") val strangerFriendly: Int = 0,
    @SerialName("suppressed_tail") val suppressedTail: Int = 0,
    @SerialName("vcahospitals_url") val vcahospitalsUrl: String = "",
    @SerialName("vetstreet_url") val vetstreetUrl: String = "",
    @SerialName("wikipedia_url") val wikipediaUrl: String = ""
)