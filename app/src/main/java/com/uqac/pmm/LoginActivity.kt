package com.uqac.pmm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    var email: EditText? = null
    var password: EditText? = null
    var login: Button? = null

    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.request)

        auth = FirebaseAuth.getInstance()
        Log.d("TAG", auth.toString())
        login?.setOnClickListener(View.OnClickListener {
            val txt_email: String = email?.getText().toString()
            val txt_pass: String = password?.getText().toString()
            Log.d("TAG", email.toString())
            Log.d("TAG", password.toString())
            auth!!.signInWithEmailAndPassword(txt_email, txt_pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth!!.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        })
    }
}
