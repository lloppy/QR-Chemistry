package com.example.shibarichat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shibarichat.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("message")

        binding.bSend.setOnClickListener{
            myRef.setValue(binding.edMessage.text.toString())
        }
        onCangeListener(myRef)
    }

    private fun onCangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.apply {
                    rcView.append("\n")
                    rcView.append("Polina : ${snapshot.value.toString()}")
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}