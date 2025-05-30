package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var tvCalculation: TextView
    private lateinit var tvResult: TextView

    private var selectedValue: String = ""
    private var selectedOperation: String = ""

    private lateinit var numButtonsMap:Map<Button, String>
    private lateinit var operButtonsMap:Map<Button, String>
    private lateinit var funcButtonsMap:Map<Button, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnDecimal = findViewById<Button>(R.id.decimal)
        val btnBackspace = findViewById<Button>(R.id.backspace)
        val btnEqual = findViewById<Button>(R.id.equal)
        val btn0 = findViewById<Button>(R.id.zero)
        val btn1 = findViewById<Button>(R.id.one)
        val btn2 = findViewById<Button>(R.id.two)
        val btn3 = findViewById<Button>(R.id.three)
        val btn4 = findViewById<Button>(R.id.four)
        val btn5 = findViewById<Button>(R.id.five)
        val btn6 = findViewById<Button>(R.id.six)
        val btn7 = findViewById<Button>(R.id.seven)
        val btn8 = findViewById<Button>(R.id.eight)
        val btn9 = findViewById<Button>(R.id.nine)
        val btnPlus = findViewById<Button>(R.id.plus)
        val btnMinus = findViewById<Button>(R.id.minus)
        val btnMultiplication = findViewById<Button>(R.id.multiplication)
        val btnDivide = findViewById<Button>(R.id.divide)
        val btnPercent = findViewById<Button>(R.id.percent)
        val btnSwitchSignal = findViewById<Button>(R.id.switchSignal)
        val btnClean = findViewById<Button>(R.id.Clean)

        tvCalculation = findViewById(R.id.tv_valuesTyped)
        tvResult = findViewById(R.id.Result)
        numButtonsMap = mapOf(
            btn0 to "0",
            btn1 to "1",
            btn2 to "2",
            btn3 to "3",
            btn4 to "4",
            btn5 to "5",
            btn6 to "6",
            btn7 to "7",
            btn8 to "8",
            btn9 to "9",
        )
        operButtonsMap = mapOf(
            btnPlus to "+",
            btnMinus to "-",
            btnMultiplication to "x",
            btnDivide to "÷",
            btnPercent to "%"
        )
        funcButtonsMap = mapOf(
            btnEqual to "=",
            btnDecimal to ".",
            btnClean to "Clean",
            btnSwitchSignal to "±",
            btnBackspace to "backspace"
        )

        numButtonsMap.forEach { (button, value) ->
            button.setOnClickListener {

                selectedValue = value.toString()
                updateCalculation()

            }
        }

        operButtonsMap.forEach{ (button, value) ->
            button.setOnClickListener {

                selectedOperation = value.toString()
                updateOperation()

            }
        }

        funcButtonsMap.forEach { (button, value) ->
            button.setOnClickListener {

                when (value) {
                    "=" -> showResult()
                    "Clean" -> cleanAll()
                    "±" -> swithSignal()
                    "backspace" -> backspace()
                    else -> {
                        selectedOperation = value.toString()
                        updateOperation()
                    }
                }

            }
        }

    }

    private fun canAddDecimal(): Boolean {

        val actualText = tvCalculation.text.toString()
        val operators = "[+\\-x÷%]".toRegex()

        val lastNumber = actualText.split(operators).lastOrNull() ?: return true

        return !lastNumber.contains(".")

    }

    private fun updateCalculation() {
        val numberChosen: String = selectedValue.toString()
        tvCalculation.append(numberChosen)
    }

    private fun updateOperation() {
        val actualText: String = tvCalculation.text.toString()
        val operation = selectedOperation

        if ( operation == "." && !canAddDecimal()) return

        val lastChar:String = actualText.lastOrNull().toString()

        val isOperator = listOf("+","-","x","÷","%").contains(lastChar)

        if (isOperator) {
            tvCalculation.text = actualText.dropLast(1) + operation
        } else {
            if (actualText.isNotEmpty() || operation == "-") {
                tvCalculation.append(operation)
            }
        }

    }


    private fun showResult() {
        val result = Expression(tvCalculation.text.toString()).calculate()
        tvResult.text = result.toString()
    }

    private fun cleanAll() {
        val actualText = tvCalculation.text.toString()
        val actualResult = tvResult.text.toString()

        if( actualText.isNotEmpty() ){
            tvCalculation.text = ""
        }else if (actualResult.isNotEmpty()){
            tvResult.text = ""
        }
    }

    private fun swithSignal() {
        tvCalculation.append(selectedOperation)
    }

    private fun backspace() {
        val actualText: String = tvCalculation.text.toString()

        if( actualText.isNotEmpty() ) {
            tvCalculation.text = actualText.dropLast(1)
        }

    }


}