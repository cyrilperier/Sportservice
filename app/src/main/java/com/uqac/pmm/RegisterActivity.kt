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
    var username: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var request: Button? = null
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        username = findViewById(R.id.username)
        email = findViewById(R.id.emailRegister)
        password = findViewById(R.id.passwordRegister)
        request = findViewById(R.id.request)

        Log.d("TAG", auth.toString())

        auth = FirebaseAuth.getInstance()

        Log.d("TAG", auth.toString())
        request?.setOnClickListener(View.OnClickListener {
            val txt_user: String = username?.getText().toString()
            val txt_email: String = email?.getText().toString()
            val txt_pass: String = password?.getText().toString()
            print(txt_user)
            print(txt_email)
            print(txt_pass)
            if (TextUtils.isEmpty(txt_email)
            ) {
                Toast.makeText(this@RegisterActivity, "Missing infos", Toast.LENGTH_SHORT).show()
            }
            if (isEmailValid(txt_email) == false) {
                Toast.makeText(this@RegisterActivity, "Not a valid email", Toast.LENGTH_SHORT)
                    .show()
            } else if (txt_pass.length < 6) {
                Toast.makeText(this@RegisterActivity, "Pass too short", Toast.LENGTH_SHORT).show()
            } else {
                register(txt_user, txt_email, txt_pass)
            }
        })
    }

    private fun register(username: String, email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's informatiodn
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth!!.currentUser
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
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

    companion object {
        fun isEmailValid(email: String?): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}