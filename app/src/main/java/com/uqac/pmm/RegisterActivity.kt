package com.uqac.pmm

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private var username: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    var request: Button? = null
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        username = findViewById(R.id.username)
        email = findViewById(R.id.emailRegister)
        password = findViewById(R.id.passwordRegister)
        request = findViewById(R.id.request)
        auth = FirebaseAuth.getInstance()
        request?.setOnClickListener {
            register(username?.text.toString(), email?.text.toString(), password?.text.toString())
        }
    }

    private fun register(username: String, email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's informatiodn
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth?.currentUser
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}