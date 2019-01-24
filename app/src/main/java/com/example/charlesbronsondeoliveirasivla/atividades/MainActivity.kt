package com.example.charlesbronsondeoliveirasivla.atividades

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var Arr = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        confirm.setOnClickListener(){
            convert(1.1f,4.3f, input.text.toString().toFloat())
        }

    }

    override fun onStart() {
        super.onStart()

        val spinner : Spinner = findViewById(R.id.spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        CurrecyAPI().execute("")
    }



    private fun convert (from : Float, to : Float, entered: Float): String {
        val result : Float = (entered*to)/from
        return result.toString()
    }


}
