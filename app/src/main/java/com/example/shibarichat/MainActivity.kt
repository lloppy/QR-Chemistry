package com.example.shibarichat

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibarichat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var adapter: UserAdapter
    var FLAG = true

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    private var currlat = ""
    private var currlong = 0.0
    private var decibel = 0.0
    private lateinit var arr: List<Double>

    var ansa = ""
    var ansb = ""
    var ansc = ""
    var ansd = ""
    var anse = ""
    var mark = ""
    lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        name = auth.currentUser!!.displayName.toString()
        setUpActBar()


        val send = binding.bSend
        val send2 = binding.bSend2
        val send3 = binding.bSend3
        val send4 = binding.bSend4
        val send5 = binding.bSend5
        val send6 = binding.editTextTextPersonName5

        send.setOnClickListener{

            ansa = binding.editTextTextPersonName2.text.toString()

            binding.editTextTextPersonName2.hint = "Задание выполнено"
            binding.editTextTextPersonName2.setText("")

            send.isInvisible = true
            send2.isVisible = true
        }



        send2.setOnClickListener{
            ansb = binding.bt2.text.toString()

            binding.bt2.hint = "Задание выполнено"
            binding.bt2.setText("")

            send2.isInvisible = true
            send3.isVisible = true

        }

        send3.setOnClickListener{
            ansc = binding.bt.text.toString()
            binding.bt.hint = "Задание выполнено"
            binding.bt.setText("")

            send3.isInvisible = true
            send4.isVisible = true
        }


        send4.setOnClickListener{
            ansd = binding.editTextTextPersonName3.text.toString()
            binding.editTextTextPersonName3.hint = "Задание выполнено"
            binding.editTextTextPersonName3.setText("")

            send4.isInvisible = true
            send5.isVisible = true
        }

        send5.setOnClickListener{
            anse = binding.editTextTextPersonName4.text.toString()

            database = Firebase.database
            myRef = database.getReference("points")
            myRef.child(myRef.push().key ?: "blablabla").setValue(Points(name, ansa, ansb, ansc, ansd, anse, mark))

            binding.editTextTextPersonName4.hint = "Задание выполнено"
            binding.editTextTextPersonName4.setText("")

            send5.isInvisible = true
            Toast.makeText(this,
                "Ты выполнил все задания по QR-кодам! Финальное задание - получить балл от Марины Павловны за активность на уроке",Toast.LENGTH_LONG).show()
        }


        send6.setOnClickListener{
            if (true.also { send5.isInvisible = it }){
                Toast.makeText(this,
                    "Если ты уже проявил активность на уроке, то твой результат скоро будет передан на базу данных и выставлен в дневник",Toast.LENGTH_LONG).show()
            }
        }





        var bScanner: Button? = null
        bScanner = findViewById(R.id.button) as Button
        bScanner?.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }

    private fun scrollRView(){
        var rView = findViewById<RecyclerView>(R.id.rcView)
    }

    private fun clearEditText(){
        var edMessage = findViewById<EditText>(R.id.edMessage)
        edMessage.setText("")
    }

    private fun initRcView() = with(binding){
        adapter = UserAdapter()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out){
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onCangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList <User>()
                for (s in snapshot.children){
                    val user = s.getValue(User::class.java)
                    if (user != null) list.add(user)
                }
                adapter.submitList(list)
                Log.i("firebase", "$list")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setUpActBar(){
        val actBar = supportActionBar
        Thread {
            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val drIcon = BitmapDrawable(resources, bMap)
            runOnUiThread{
                actBar?.setDisplayHomeAsUpEnabled(true)
                actBar?.setHomeAsUpIndicator(drIcon)
                actBar?.title = auth.currentUser?.displayName
            }
        }.start()
    }


}