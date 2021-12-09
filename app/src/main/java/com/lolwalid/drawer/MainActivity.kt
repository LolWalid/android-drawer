package com.lolwalid.drawer

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lolwalid.drawer.databinding.ActivityMainBinding
import java.util.logging.Level
import java.util.logging.Logger

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    private val colorsHash = hashMapOf(
        "BLACK" to Color.BLACK,
        "DKGRAY" to Color.DKGRAY,
        "GRAY" to Color.GRAY,
        "LTGRAY" to Color.LTGRAY,
        "WHITE" to Color.WHITE,
        "RED" to Color.RED,
        "GREEN" to Color.GREEN,
        "BLUE" to Color.BLUE,
        "YELLOW" to Color.YELLOW,
        "CYAN" to Color.CYAN,
        "MAGENTA" to Color.MAGENTA,
        "TRANSPARENT" to Color.TRANSPARENT,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupColorPicker()
        // setContentView(R.layout.activity_main)
    }

    fun setupColorPicker() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            colorsHash.keys.toList()
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.colorSpinner.adapter = adapter
        binding.colorSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val color = parent?.getItemAtPosition(position)
        Logger.getGlobal().log(Level.WARNING, "${color} ${colorsHash[color]}")
        Logger.getGlobal().log(Level.WARNING, "Color.RED ${Color.RED}")
        colorsHash[color]?.let { binding.fingerViewId.setColor(it) }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

//    fun setColor() {
//        binding.fingerViewId.setColor(Color.RED)
//    }

}