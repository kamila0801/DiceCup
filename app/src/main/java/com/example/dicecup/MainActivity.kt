package com.example.dicecup

import android.content.res.Configuration
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
    private val mRandomGenerator = Random()

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
        val incr = binding.incrementBtn
        val decr = binding.decrementBtn
        var rollBtn = binding.rollBtn
        binding.counter = counter.toString()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            val lay3 = binding.linearLay3
            if(lay1!=null && lay2!=null && lay3!=null)
                    layouts = arrayOf(lay1, lay2, lay3)

            addImagePortrait()
            initListeners(true, incr, decr, rollBtn)

        }
        else if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(lay1!=null && lay2!=null)
                layouts = arrayOf(lay1, lay2)

            addImageLandscape()
            initListeners(false, incr, decr, rollBtn)
        }

    }


    /**
     * this method can be used in both just an if statement for addImagePortrait
     */
    private fun initListeners(portrait: Boolean, incr: Button, decr: Button, rollBtn: Button?) {
        incr.setOnClickListener {
            if(portrait)
                addImagePortrait()
            else
                addImageLandscape()

            if (counter != 6) {
                counter++
                binding.counter = counter.toString()
            }
        }

        decr.setOnClickListener {
            decrementBtn(portrait)
            if (counter != 1) {
                counter--
                binding.counter = counter.toString()
            }
        }

        rollBtn?.setOnClickListener { roll() }
    }

    private fun roll() {
        for(image in dices){
            image.setImageResource(diceId[mRandomGenerator.nextInt(6) + 1])
        }
    }

    private fun decrementBtn(portrait: Boolean) {
        val size = dices.size;

        if(portrait){
            removeElementPortrait(size)
        }
        else{
            removeElementLandscape(size)
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

    private fun removeElementLandscape(size: Int) {
        if (size == 6) {
            layouts[1].removeViewAt(2)
        } else if (size == 5) {
            layouts[1].removeViewAt(1)
        } else if (size == 4) {
            layouts[1].removeViewAt(0)
        } else if (size == 3) {
            layouts[0].removeViewAt(2)
        } else if (size == 2) {
            layouts[0].removeViewAt(1)
        }
    }

    private fun removeElementPortrait(size: Int) {
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
    }


    private fun addImagePortrait() {
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

    private fun addImageLandscape() {
        if (counter == 1 || counter==2) {
            layouts[0].addView(getImageView())
        }
        if (counter == 3 || counter == 4 || counter==5) {
            layouts[1].addView(getImageView())
        }
        else if (counter == 6) {
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