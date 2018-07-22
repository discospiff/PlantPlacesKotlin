package edu.uc.jonesbr.plantplaceskotlin.dto

/**
 * Created by ucint on 7/22/2018.
 */
data class SpecimenDTO(var plantName : String, var latitude : String, var longitude: String, var location : String, var description : String, var specimenId : Int = 0, var plantId : Int = 0) {
    constructor() : this("", "0.0", "0.0", "","")
}