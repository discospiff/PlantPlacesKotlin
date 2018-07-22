package edu.uc.jonesbr.plantplaceskotlin.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by ucint on 7/22/2018.
 */
data class PlantList(@SerializedName("plants") var plants: List<PlantDTO>) {
}