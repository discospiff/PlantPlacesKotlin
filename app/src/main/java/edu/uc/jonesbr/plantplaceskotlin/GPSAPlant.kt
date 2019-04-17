package edu.uc.jonesbr.plantplaceskotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.uc.jonesbr.plantplaceskotlin.dto.PlantDTO
import edu.uc.jonesbr.plantplaceskotlin.dto.PlantList
import edu.uc.jonesbr.plantplaceskotlin.dto.SpecimenDTO
import kotlinx.android.synthetic.main.activity_gpsaplant.*

import kotlinx.android.synthetic.main.content_gpsaplant.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GPSAPlant : AppCompatActivity() {

    private var allSpecimens = ArrayList<SpecimenDTO>()

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
            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.getReference()
            databaseReference.child("specimens").push().setValue(specimen)

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
                val adapter = ArrayAdapter<PlantDTO>(applicationContext, android.R.layout.simple_list_item_1, plants)
                actPlantName.setAdapter(adapter)

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<PlantList>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Error reading JSON", Toast.LENGTH_LONG).show()
            }

        })

        val firebaseDatabase = FirebaseDatabase.getInstance();
        val reference = firebaseDatabase.getReference()
        reference.child("specimens").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    var specimen = it.getValue(SpecimenDTO::class.java)
                    allSpecimens.add(specimen!!)
                }
                specimenRecycler.adapter.notifyDataSetChanged()
            }

        }
        )

        specimenRecycler.hasFixedSize()
        specimenRecycler.setLayoutManager(LinearLayoutManager(this))
        specimenRecycler.itemAnimator = DefaultItemAnimator()
        specimenRecycler.adapter = SpecimenAdapter(allSpecimens, R.layout.rowlayout)

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

    class SpecimenAdapter(val specimens : List<SpecimenDTO>, val itemLayout: Int) : RecyclerView.Adapter<SpecimenViewHolder>() {
        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        override fun getItemCount(): Int {
            return specimens.size
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the [ViewHolder.itemView] to reflect the item at the given
         * position.
         *
         *
         * Note that unlike [android.widget.ListView], RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the `position` parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
         * have the updated adapter position.
         *
         * Override [.onBindViewHolder] instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder The ViewHolder which should be updated to represent the contents of the
         * item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        override fun onBindViewHolder(holder: SpecimenViewHolder, position: Int) {
            var specimen = specimens.get(position)
            holder.updateSpecimen(specimen)
        }

        /**
         * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
         * an item.
         *
         *
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         *
         *
         * The new ViewHolder will be used to display items of the adapter using
         * [.onBindViewHolder]. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary [View.findViewById] calls.
         *
         * @param parent The ViewGroup into which the new View will be added after it is bound to
         * an adapter position.
         * @param viewType The view type of the new View.
         *
         * @return A new ViewHolder that holds a View of the given view type.
         * @see .getItemViewType
         * @see .onBindViewHolder
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecimenViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
            return SpecimenViewHolder(view)

        }

    }

    class SpecimenViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var imgSpecimenThumbnail: ImageView?

        private var lblSpecimenInfo: TextView?

        init {
            imgSpecimenThumbnail = itemView.findViewById<ImageView>(R.id.imgSpecimenThumbnail)
            lblSpecimenInfo = itemView.findViewById<TextView>(R.id.lblSpecimenInfo)
        }

        fun updateSpecimen(specimen: SpecimenDTO) {
            lblSpecimenInfo?.text = specimen.toString()
        }
    }
}
