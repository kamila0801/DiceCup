package com.example.dicecup

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.dicecup.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layouts: Array<LinearLayout>
    private var counter: Int = 1;
    private var dices = Stack<ImageView>()
    private var dicesValues = ArrayList<Int>()
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

            if (savedInstanceState != null)
            {
                dicesValues = savedInstanceState.getIntegerArrayList("mValue") as ArrayList<Int>
                refreshPortrait()
            }
            else
                addImagePortrait(-1, true)

            initListeners(true, incr, decr, rollBtn)

        }
        else if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(lay1!=null && lay2!=null)
                layouts = arrayOf(lay1, lay2)

            if (savedInstanceState != null)
            {
                dicesValues = savedInstanceState.getIntegerArrayList("mValue") as ArrayList<Int>
                refreshLandScape()
            }
            else
                addImageLandscape(true)
            initListeners(false, incr, decr, rollBtn)
        }

    }

    private fun refreshLandScape() {
        for(value in dicesValues){
            addImageLandscape(value, false)
        }
    }

    private fun refreshPortrait() {
        for(value in dicesValues){
            addImagePortrait(value, false)
        }
    }


    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putIntegerArrayList("mValue", dicesValues);
    }




    /**
     * this method can be used in both just an if statement for addImagePortrait
     */
    private fun initListeners(portrait: Boolean, incr: Button, decr: Button, rollBtn: Button?) {
        incr.setOnClickListener {
            if(portrait)
                addImagePortrait(-1, true) // -1 is for no
            else
                addImageLandscape(true)

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
        dicesValues.clear()
        for(image in dices){
            val value = mRandomGenerator.nextInt(6) + 1;
            image.setImageResource(diceId[value])
            dicesValues.add(value)
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
        dicesValues.removeAt(dicesValues.size-1)
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
        dicesValues.removeAt(dicesValues.size-1)
    }


    private fun addImagePortrait(no: Int, isNew: Boolean) {
        if (counter == 1) {
            layouts[0].addView( if( no==-1)  getImageView() else getImageView(no))
        }
        if (counter == 2 || counter == 3) {
            layouts[1].addView(if( no==-1)  getImageView() else getImageView(no))
        }
        if (counter == 4 || counter == 5) {
            layouts[2].addView(if( no==-1)  getImageView() else getImageView(no))
        } else if (counter == 6) {
            Toast.makeText(
                this,
                "cannot add more dices",
                Toast.LENGTH_SHORT
            ).show()
        }
        if(isNew){
            when(counter){
                1,2,3,4,5 -> if( no==-1) dicesValues.add(2) else dicesValues.add(no)
            }
        }
    }

    private fun addImageLandscape( isNew: Boolean) {
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
        if(isNew){
            when(counter){
                1,2,3,4,5 -> dicesValues.add(2)
            }
        }

    }
    private fun addImageLandscape(no:Int, isNew: Boolean) {
        if (counter == 1 || counter==2) {
            layouts[0].addView(getImageView(no))
        }
        if (counter == 3 || counter == 4 || counter==5) {
            layouts[1].addView(getImageView(no))
        }
        else if (counter == 6) {
            Toast.makeText(
                this,
                "cannot add more dices",
                Toast.LENGTH_SHORT
            ).show()
        }
        if(isNew){
            when(counter){
                1,2,3,4,5 -> dicesValues.add(no)
            }
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

    private fun getImageView(no: Int): ImageView {
        val imageView = ImageView(this)
        imageView.layoutParams = LinearLayout.LayoutParams(200, 200) // value is in pixels
        if(no==-1)
            imageView.setImageResource(diceId[1])
        else
            imageView.setImageResource(diceId[no])
        imageView.setPadding(20, 0, 20, 0)
        dices.push(imageView)
        return imageView;
    }
}