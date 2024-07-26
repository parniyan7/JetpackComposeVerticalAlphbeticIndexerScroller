package com.parniyan.verticalalphabeticscroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.parniyan.verticalalphabeticscroller.model.Contact
import com.parniyan.verticalalphabeticscroller.ui.theme.VerticalAlphabeticScrollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val names = arrayOf(
                "Anna",
                "Sam",
                "Tom",
                "Bob",
                "Jason",
                "Mat",
                "Karl",
                "Emma",
                "James",
                "Camila",
                "Garry",
                "Tim",
                "Sara",
                "Monica",
                )
            VerticalAlphabeticScrollerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  AlphabeticContactListWithVerticalIndexer(
                      modifier = Modifier
                          .padding(innerPadding),
                      contacts = names.map { (Contact(it)) },
                      onClick = {})
                }
            }
        }
    }
}