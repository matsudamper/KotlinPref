package net.matsudamper.kotlinpref

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val generalPref by lazy { GeneralPref(this) }
    private fun getUserPrefModel(userId: Long) = UserPref(this, userId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Color
        red_button.setOnClickListener {
            generalPref.colorState = GeneralPref.ColorState.RED
        }

        blue_button.setOnClickListener {
            generalPref.colorState = GeneralPref.ColorState.BLUE
        }

        yellow_button.setOnClickListener {
            generalPref.colorState = GeneralPref.ColorState.YELLOW
        }

        orange_button.setOnClickListener {
            generalPref.colorState = GeneralPref.ColorState.ORANGE
        }

        write_button

        generalPref.colorStateLiveData
            .observe(this, Observer {
                setColor(it)
            })

        // User
        read_button.setOnClickListener {
            val userPref = getUserPrefModel(getUserIdOrNull() ?: return@setOnClickListener)

            userText.setText(userPref.userText)
        }

        write_button.setOnClickListener {
            val userPref = getUserPrefModel(getUserIdOrNull() ?: return@setOnClickListener)

            userPref.userText = userText.text.toString()
        }
    }

    private fun getUserIdOrNull(): Long? {
        val userIdString = userId.text.toString()
        val userId = userIdString.toLongOrNull()

        if (userId == null) {
            Toast.makeText(this, "Be sure to enter numbers.", Toast.LENGTH_SHORT).show()
        }

        return userId
    }

    private fun setColor(colorState: GeneralPref.ColorState) {
        val color = when (colorState) {
            GeneralPref.ColorState.ORANGE -> resources.getColor(android.R.color.holo_orange_light)
            GeneralPref.ColorState.BLUE -> Color.BLUE
            GeneralPref.ColorState.RED -> Color.RED
            GeneralPref.ColorState.YELLOW -> Color.YELLOW
        }
        color_preview_image.setBackgroundColor(color)
    }
}
