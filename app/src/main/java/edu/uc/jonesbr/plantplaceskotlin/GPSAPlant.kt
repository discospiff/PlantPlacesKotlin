package edu.uc.jonesbr.plantplaceskotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import edu.uc.jonesbr.plantplaceskotlin.dto.PlantList
import edu.uc.jonesbr.plantplaceskotlin.dto.SpecimenDTO
import kotlinx.android.synthetic.main.activity_gpsaplant.*

import kotlinx.android.synthetic.main.content_gpsaplant.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GPSAPlant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpsaplant)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        btnSave.setOnClickListener{view ->

            var specimen = SpecimenDTO()
            with(specimen) {
                plantName = actPlantName.text.toString()
                location = actLocation.text.toString()
                description = edtDescription.text.toString()
            }
            // populate a specimen object.

        }

        val service = RetrofitClientInstance.retrofitInstance?.create(GetPlantService::class.java)
        val call = service?.getAllPlants()
        call?.enqueue(object : Callback<PlantList> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(call: Call<PlantList>?, response: Response<PlantList>?) {
                val body = response?.body()
                val plants = body?.plants
                var size = plants?.size

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<PlantList>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Error reading JSON", Toast.LENGTH_LONG).show()
            }

        })




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_gpsaplant, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
