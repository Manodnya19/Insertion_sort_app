package com.test.inserrt_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.util.Arrays
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private lateinit var sortButton: Button
    private lateinit var inputArrayEditText: EditText
    private lateinit var sortedArrayTextView: TextView
    private lateinit var passTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputArrayEditText = findViewById(R.id.inputArray)
        sortButton = findViewById(R.id.sortButton)
        sortedArrayTextView = findViewById(R.id.sortedArrayTextView)
        passTextView = findViewById(R.id.passTextView)

        sortButton.setOnClickListener {
            val inputText = inputArrayEditText.text.toString()
            val inputArray = parseInputArray(inputText)
            if (isValidInput(inputArray)) {
                Thread {
                    val passes = insertionSortWithPasses(inputArray)
                    runOnUiThread {
                        val sortedArray = Arrays.toString(inputArray)
                        val allPasses = StringBuilder()

                        for ((index, pass) in passes.withIndex()) {
                            allPasses.append("Pass ${index + 1}:\n$pass\n\n")
                        }

                        passTextView.text = allPasses.toString() + "Final Sorted Array:\n$sortedArray"
                        passTextView.visibility = View.VISIBLE
                        sortedArrayTextView.visibility = View.GONE
                    }
                }.start()
            } else {
                passTextView.text = "Invalid input! Please enter integers from 0 to 9 with a size between 2 and 8."
                passTextView.visibility = View.VISIBLE
                sortedArrayTextView.visibility = View.GONE
            }
        }
        val clearButton = findViewById<Button>(R.id.clearButton)
        val editText = findViewById<EditText>(R.id.inputArray)

        clearButton.setOnClickListener {
           // editText.text.clear()
            inputArrayEditText.text.clear()
            passTextView.text = ""
            sortedArrayTextView.text = ""
            passTextView.visibility = View.GONE
            sortedArrayTextView.visibility = View.GONE
        }
    }

    private fun parseInputArray(input: String): IntArray {
        val inputValues = input.trim().split("\\s+".toRegex()).map { it.toInt() }
        return inputValues.toIntArray()
    }

    private fun isValidInput(inputArray: IntArray): Boolean {
        return inputArray.size in 2..8 && inputArray.all { it in 0..9 }
    }

    private fun insertionSortWithPasses(arr: IntArray): List<String> {
        val n = arr.size
        val passes = mutableListOf<String>()

        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]
                j--
            }
            arr[j + 1] = key
            passes.add(Arrays.toString(arr.clone()))
        }

        return passes

    }

    /*private fun displayPasses(passes: List<String>) {
        Handler().postDelayed({
            var currentIndex = 0
            val totalPasses = passes.size

            fun displayNextPass() {
                if (currentIndex < totalPasses) {
                    passTextView.text = "Pass ${currentIndex + 1}:\n${passes[currentIndex]}"
                    currentIndex++
                    Handler().postDelayed({ displayNextPass() }, 1000)
                } else {
                    passTextView.visibility = View.GONE
                    sortedArrayTextView.visibility = View.VISIBLE
                }
            }

            displayNextPass()
        }, 1000)
    }*/

}
