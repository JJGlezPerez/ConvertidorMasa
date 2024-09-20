package com.example.masa

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.GridLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.rangeTo

class MainActivity : ComponentActivity() {
    lateinit var fromRG: RadioGroup
    lateinit var toRG:RadioGroup
    lateinit var convert:Button
    lateinit var display:EditText
    lateinit var resultd:TextView

    var from:String="mg"
    var to:String="mg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.conversor)

        inicializarComponents()

        setListeners()

    }

    fun inicializarComponents(){
        fromRG = findViewById(R.id.from_group_RB)
        toRG = findViewById(R.id.to_group_RB)
        convert = findViewById(R.id.convert)
        display = findViewById(R.id.value)
        resultd = findViewById(R.id.result)

        (fromRG.getChildAt(0) as RadioButton).isChecked=true
        (toRG.getChildAt(0) as RadioButton).isChecked=true
    }

    fun setListeners(){
        var number = display.text.toString().toDouble()

        setConvertButtonListener(convert,number)

        for(i in 0 until fromRG.childCount){
            var radioButton = fromRG.getChildAt(i)
            var radioButtonId = resources.getResourceName(radioButton.id)
            var medida = radioButtonId.split('_')[1]
            setRadioFromClickListener(medida,radioButton as RadioButton)
        }

        for (i in 0 until toRG.childCount){
            var radioButton = toRG.getChildAt(i)
            var radioButtonId = resources.getResourceName(radioButton.id)
            var medida = radioButtonId.split('_')[1]
            setRadioToClickListener(medida,radioButton as RadioButton)
        }


    }

    fun setRadioFromClickListener(conv:String,radioButton: RadioButton){
        radioButton.setOnClickListener{
            this.from=conv
        }
    }

    fun setRadioToClickListener(conv:String,radioButton: RadioButton){
        radioButton.setOnClickListener{
            this.to=conv
        }
    }
    fun setConvertButtonListener(button:Button,number:Double){
        button.setOnClickListener{
            var result=calcular(number)
            resultd.setText("${result.toString()} $to")
        }
    }
    fun calcular(number:Double):Double{
        return when(from){
            "mg"->{
                when(to){
                    "g"->number/1000.0
                    "kg"->number/1000000.0
                    "lb"->number/453600.0
                    "oz"->number/28350.0
                    else->number*1
                }
            }
            "g"->{
                when(to){
                    "mg"->number*1000.0
                    "kg"->number/1000.0
                    "lb"->number/453.6
                    "oz"->number/28.35
                    else->number*1
                }
            }
            "kg"->{
                when(to){
                    "mg"->number*1000000.0
                    "g"->number*1000.0
                    "lb"->number*2.205
                    "oz"->number*35.274
                    else->number*1
                }
            }
            "lb"->{
                when(to){
                    "mg"->number*453600.0
                    "g"->number*453.6
                    "kg"->number/2.205
                    "oz"->number*16
                    else->number*1
                }
            }
            "oz"->{
                when(to){
                    "mg"->number*28350
                    "g"->number*28.35
                    "kg"->number/35.274
                    "lb"->number/16
                    else->number*1
                }
            }
            else->number
        }
    }
}