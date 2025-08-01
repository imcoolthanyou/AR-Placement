package gautam.projects.arplacementapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ARViewActivity : AppCompatActivity() {

    // This is our AR screen where all the magic happens
    private lateinit var arFragment: ArFragment
    
    // Keeps track of all the 3D objects we add to the AR world
    private val placedObjects = mutableListOf<Node>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arview)

        // Find and set up our AR view from the layout
        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment
        
        // Tell the user what to do next
        Toast.makeText(this, "Move phone to detect surfaces", Toast.LENGTH_LONG).show()

        // Get ready to place objects when the user taps
        setupTapListener()
    }

    // This makes our AR scene respond when you tap on a surface
    private fun setupTapListener() {
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            // When you tap on a detected surface, put something there
            placeObject(hitResult)
        }
    }

    // This puts a 3D object where you tapped
    private fun placeObject(hit: HitResult) {
        // First, clean up any old objects so we don't have too many
        removePreviousObject()

        // Make a nice red material for our object
        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                // Make a small cube (about 10cm on each side)
                val size = Vector3(0.1f, 0.1f, 0.1f)
                // Lift it up a bit so it sits nicely on surfaces
                val center = Vector3(0.0f, 0.05f, 0.0f)
                
                // Create our cube with the size and color we want
                val cubeRenderable = ShapeFactory.makeCube(size, center, material)

                // This makes the object stick to the real world
                val anchor = hit.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)

                // This makes the object interactive - you can move and rotate it
                val transformableNode = TransformableNode(arFragment.transformationSystem)
                transformableNode.renderable = cubeRenderable
                transformableNode.setParent(anchorNode)

                // Remember this object so we can clean it up later
                placedObjects.add(transformableNode)
            }
    }

    // Clean up any objects we've placed before
    private fun removePreviousObject() {
        // Go through all our placed objects and remove them
        for (node in placedObjects) {
            node.parent?.let { parentNode ->
                arFragment.arSceneView.scene.removeChild(parentNode)
            }
        }
        // Empty our list of objects
        placedObjects.clear()
    }
}
