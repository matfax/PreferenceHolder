package com.marcinmoskala.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.textEditView
import kotlinx.android.synthetic.main.activity_main.numberEditView
import kotlinx.android.synthetic.main.activity_main.saveObjectButton
import kotlinx.android.synthetic.main.activity_main.objectMonitor

class MainActivity : AppCompatActivity() {

    val text: String
        get()  = textEditView.text.toString()

    val num: Int
        get()  = numberEditView.text.toString().toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textEditView.setText(Pref.text)
        numberEditView.setText(Pref.num.toString())
        textEditView.setOnKeyListener { _, _, _ -> Pref.text = text; true }
        numberEditView.setOnKeyListener { _, _, _ -> Pref.num = num; true }
        saveObjectButton.setOnClickListener {
            val newObject = OtherPref.Together(text, num)
            OtherPref.together = newObject
            objectMonitor.text = newObject.toString()
        }
    }
}
