package com.example.nurtdinov.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExtendedIngredient(
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("consistency")
    val consistency: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("metaInformation")
    val metaInformation: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("nameClean")
    val nameClean: String?,
    @SerializedName("original")
    val original: String,
    @SerializedName("unit")
    val unit: String
):Parcelable