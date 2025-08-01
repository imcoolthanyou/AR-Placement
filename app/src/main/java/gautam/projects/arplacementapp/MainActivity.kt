package gautam.projects.arplacementapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import gautam.projects.arplacementapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // This is like a blueprint for each drill in our app
    // It holds all the info we need to show about each drill
    data class Drill(
        val name: String,
        val description: String,
        val tips: String,
        val imageResId: Int
    )

    // This helps us work with the app's screen layout
    private lateinit var binding: ActivityMainBinding
    
    // Here we'll keep our list of drills
    private lateinit var drills: List<Drill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up the app's main screen
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get everything ready when the app starts
        loadDrillData()      // First, load our drill info
        setupSpinner()       // Then set up the drill selector
        setupListeners()     // And finally, make buttons work
    }

    private fun loadDrillData() {
        // This is where we define our drills with all their details
        // In a real app, we'd probably get this from the internet or a database
        drills = listOf(
            Drill(
                name = "Drill 1",
                description = "This is the Heavy-Duty Hammer Drill, perfect for concrete and masonry.",
                tips = "Tips: Use a low speed for starting holes to prevent the bit from wandering.",
                imageResId = android.R.drawable.ic_menu_manage
            ),
            Drill(
                name = "Drill 2",
                description = "This is the Compact Cordless Drill, ideal for woodworking and general tasks.",
                tips = "Tips: Fully charge the battery before first use for maximum lifespan.",
                imageResId = android.R.drawable.ic_menu_compass
            )
        )
    }

    private fun setupSpinner() {
        // First add a "Select a Drill" option, then list all our drills
        val drillNames = listOf("Select a Drill") + drills.map { it.name }

        // This makes our list of drills show up in the dropdown
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, drillNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDrillSelection.adapter = adapter

        // Do something when a drill is selected
        binding.spinnerDrillSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> hideDrillDetails()  // Hide details for "Select a Drill"
                    else -> updateUiForSelectedDrill(drills[position - 1])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                hideDrillDetails()
            }
        }
    }

    private fun setupListeners() {
        // Make the Start AR button work
        binding.buttonStartAr.setOnClickListener {
            // Let the user know something's happening
            Toast.makeText(this, "Starting AR Drill...", Toast.LENGTH_SHORT).show()
            
            // Open the AR screen
            startActivity(Intent(this, ARViewActivity::class.java))
        }
    }

    private fun updateUiForSelectedDrill(drill: Drill) {
        // Show all the details for the selected drill
        binding.imageViewDrill.setImageResource(drill.imageResId)
        binding.textViewDescription.text = drill.description
        binding.textViewTips.text = drill.tips

        // Make sure everything is visible
        binding.imageViewDrill.visibility = View.VISIBLE
        binding.textViewDescription.visibility = View.VISIBLE
        binding.textViewTips.visibility = View.VISIBLE
        binding.buttonStartAr.visibility = View.VISIBLE
    }



    private fun hideDrillDetails() {
        // When no drill is selected, hide all the details
        binding.imageViewDrill.visibility = View.GONE
        binding.textViewDescription.visibility = View.GONE
        binding.textViewTips.visibility = View.GONE
        binding.buttonStartAr.visibility = View.GONE
    }
}