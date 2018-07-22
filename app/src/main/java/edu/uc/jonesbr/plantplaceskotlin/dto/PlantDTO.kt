package edu.uc.jonesbr.plantplaceskotlin.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by ucint on 7/22/2018.
 */
data class PlantDTO(@SerializedName("id") var plantId: Int = 0, var genus: String, var species : String, var cultivar : String, var common : String) {
}