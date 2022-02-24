package com.example.dicecup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.dicecup.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layouts: Array<LinearLayout>
    private var counter: Int = 1;
    private var dices = Stack<ImageView>()

    private val diceId = intArrayOf(
        0,
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val lay1 = binding.linearLay1
        val lay2 = binding.linearLay2
        val lay3 = binding.linearLay3
        val incr = binding.incrementBtn
        val decr = binding.decrementBtn

        binding.counter = counter.toString()

        layouts = arrayOf(lay1, lay2, lay3)
        addImage()

        incr.setOnClickListener {
            addImage()
            if (counter != 6) {
                counter++
                binding.counter = counter.toString()
            }
        }

        decr.setOnClickListener {
            decrementBtn()
            if (counter != 1) {
                counter--
                binding.counter = counter.toString()
            }
        }

    }

    private fun decrementBtn() {
        val size = dices.size;
        if (size == 6) {
            layouts[2].removeViewAt(1)
        } else if (size == 5) {
            layouts[2].removeViewAt(0)
        } else if (size == 4) {
            layouts[1].removeViewAt(1)
        } else if (size == 3) {
            layouts[1].removeViewAt(0)
        } else if (size == 2) {
            layouts[0].removeViewAt(1)
        }

        when(size){
            6, 5, 4, 3, 2 -> dices.pop()
        }

        if (size == 1) {
            Toast.makeText(
                this,
                "cannot delete more dices",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    private fun addImage() {
        if (counter == 1) {
            layouts[0].addView(getImageView())
        }
        if (counter == 2 || counter == 3) {
            layouts[1].addView(getImageView())
        }
        if (counter == 4 || counter == 5) {
            layouts[2].addView(getImageView())
        } else if (counter == 6) {
            Toast.makeText(
                this,
                "cannot add more dices",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getImageView(): ImageView {
        val imageView = ImageView(this)
        imageView.layoutParams = LinearLayout.LayoutParams(200, 200) // value is in pixels
        imageView.setImageResource(R.drawable.dice2)
        imageView.setPadding(20, 0, 20, 0)
        dices.push(imageView)
        return imageView;
    }
}