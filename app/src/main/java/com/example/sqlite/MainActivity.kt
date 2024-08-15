package com.example.sqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val enterName = findViewById<EditText>(R.id.edtName)
        val enterAge = findViewById<EditText>(R.id.edtAge)
        val addName = findViewById<Button>(R.id.btnName)
        val printName = findViewById<Button>(R.id.btnPrint)
        val nameDisplay = findViewById<TextView>(R.id.lblName)
        val ageDisplay = findViewById<TextView>(R.id.lblAge)

        addName.setOnClickListener{

            //below we have created
            //a new DBHelper class,
            //and passed context to it
            val db = DBHelper(this,null)

            //creating variables for values
            // in name and age edit texts
            val name = enterName.text.toString()
            val age = enterAge.text.toString()

            //calling method to add
            // name and age to our database
            db.addName(name,age)

            //Toast to message on screen
            Toast.makeText(this,"$name added to database", Toast.LENGTH_LONG).show()

            //clear edit texts
            enterName.text.clear()
            enterAge.text.clear()
        }

        printName.setOnClickListener{

            //creating a DBHelper class
            //and passing context to it
            val db = DBHelper(this,null)

            //below is the variable for cursor
            //we have called method to get
            //all names from our database
            //and add to name text view
            val cursor = db.getName()

            //moving the curor to first position and
            //appending value in the textview
            cursor?.moveToFirst()
            nameDisplay.text = cursor?.getString(cursor.getColumnIndex(DBHelper.NAME_COL))+"\n"
            ageDisplay.text = cursor?.getString(cursor.getColumnIndex(DBHelper.AGE_COL))+"\n"

            //moving our cursor to next
            //position and appending values
            while(cursor?.moveToNext() == true){
                nameDisplay.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL))+"\n")
                ageDisplay.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL))+"\n")
            }

            //at last we close our cursor
            cursor?.close()
        }

    }
}