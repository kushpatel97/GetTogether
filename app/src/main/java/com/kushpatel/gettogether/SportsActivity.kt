package com.kushpatel.gettogether

import com.kushpatel.gettogether.adapters.SportsAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sports.*
import com.kushpatel.gettogether.models.Sport
import kotlin.collections.ArrayList

class SportsActivity : AppCompatActivity() {

    val SPORTS_TAG = "SportsActivity"
    lateinit var mRef: DatabaseReference
    var sportsList: ArrayList<Sport> = ArrayList()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sports)
        supportActionBar?.title = "Sports"


        checkIfUserIsLoggedIn()

        populateRecyclerView()

        recyclerView.setOnClickListener{
            Log.d(SPORTS_TAG,"Clicked ${it}")
        }









    }

    private fun populateRecyclerView() {
        mRef = FirebaseDatabase.getInstance().getReference("sports")
        mRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children?.forEach{
                    var sport: Sport = it.getValue(Sport::class.java)!!
                    sportsList?.add(sport)
                    println(sportsList.toString())
//                    Log.d(SPORTS_TAG, "Value: ${it.value.toString()}")
                }
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                recyclerView.adapter = SportsAdapter(sportsList)
            }

        })
    }


    private fun checkIfUserIsLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sports_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_settings -> {
                val settingsIntent = Intent(this,SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
