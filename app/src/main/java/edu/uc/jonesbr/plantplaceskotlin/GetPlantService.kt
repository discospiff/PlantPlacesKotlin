package edu.uc.jonesbr.plantplaceskotlin

import edu.uc.jonesbr.plantplaceskotlin.dto.PlantList
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by ucint on 7/22/2018.
 */
interface GetPlantService {

    @GET("/perl/mobile/viewplantsjson.pl?Combined_Name=e")
    fun getAllPlants() : Call<PlantList>
}