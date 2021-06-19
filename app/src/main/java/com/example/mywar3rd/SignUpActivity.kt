package com.example.mywar3rd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

import kotlinx.android.synthetic.main.activity_main.*


class SignUpActivity : AppCompatActivity() {

    //fields
    private lateinit var emailText: EditText
    private lateinit var passText: EditText
    private lateinit var phoneText: EditText


    //callbacks for the result of verifying the phone number
  //   lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        passText = findViewById(R.id.editTextPassword)
        phoneText = findViewById(R.id.editTextMobile)
        emailText = findViewById(R.id.editTextEmail)
    }

    override fun onStart() {
        super.onStart()
        // user is signed in already
        if (mAuth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
            progress.visibility = View.VISIBLE

        } else {
            Toast.makeText(this, " Not Signed in", Toast.LENGTH_LONG).show()
        }
    }

    fun onLoginClickBack(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay)
    }
    fun onReg(view: View) {
        emailSignUp()
    }

    private fun emailSignUp() {
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("createUser", "createUserWithEmail:success")
                val user = mAuth.currentUser
                Toast.makeText(baseContext, " $user", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
                progress.visibility = View.VISIBLE

            } else {
                // If sign in fails, display a message to the user.
                Log.w("createUser", "createUserWithEmail:failure" + task.exception)
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


    }


//    fun facebookReg(view: View) {}
}