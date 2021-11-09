package com.uqac.pmm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.start_screen.*

class StartActivity : AppCompatActivity() {
    var loginBtn: Button? = null
    var register: Button? = null
    var sp: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)
        loginBtn = findViewById(R.id.login)
        register = findViewById(R.id.register)
        loginBtn?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@StartActivity,
                    LoginActivity::class.java
                )
            )
        })
        register?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@StartActivity,
                    RegisterActivity::class.java
                )
            )
        })
    }
}