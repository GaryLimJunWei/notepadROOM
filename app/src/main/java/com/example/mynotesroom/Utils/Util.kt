package com.example.mynotesroom.Utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message:String) =
    Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG).show()