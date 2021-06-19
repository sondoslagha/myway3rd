package com.example.mywar3rd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailText: EditText
    private lateinit var passText: EditText
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        emailText = findViewById(R.id.editTextMobileLog)
        passText = findViewById(R.id.editTextPasswordLog)

    }

    fun loginClick(view: View) {
        signIn()
    }

    private fun signIn() {
        val email: String = emailText.text.toString()
        val password: String = passText.text.toString()

        if (!email.contains("@")) {
            emailText.error = "please insert valid email"
            emailText.requestFocus()
            return
        }
        if (password.length <= 6) {
            passText.error = "password must be at least 6"
            return
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "signInWithEmail:success")
                val user = mAuth.currentUser
                Toast.makeText(baseContext, " " + user, Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
                progress.visibility = View.VISIBLE
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    fun onRegClick(view: View) {

        startActivity(Intent(this, SignUpActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
        progress.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
            progress.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, " Not Signed in", Toast.LENGTH_LONG).show()
        }
    }


    //  fun facebookLogin(view: View) {}

}

