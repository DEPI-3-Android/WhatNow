package com.acms.whatnow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.acms.whatnow.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Snackbar.make(
                        binding.root,
                        "Google sign in failed: ${e.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleBtn.setOnClickListener {
            signInWithGoogle()
        }
        binding.haveAcc.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.emailET.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                binding.filledEmailField.error = null
        }
        binding.conPassET.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty())
                binding.filledConPassField.error = null
        }

        binding.sinUpBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val pass = binding.passET.text.toString().trim()
            val conPass = binding.conPassET.text.toString().trim()
            var valid = true

            when {
                email.isBlank() -> {
                    binding.filledEmailField.error = getString(R.string.required)
                    valid = false
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.filledEmailField.error = getString(R.string.invalid_email)
                    valid = false
                }

                else -> binding.filledEmailField.error = null
            }
            when {
                pass.isBlank() -> {
                    binding.filledPassField.error = getString(R.string.required)
                    valid = false
                }

                pass.length < 8 -> {
                    binding.filledPassField.error = getString(R.string.password_long)
                    valid = false
                }

                else -> binding.filledPassField.error = null
            }

            when {
                conPass.isBlank() -> {
                    binding.filledConPassField.error = getString(R.string.required)
                    valid = false
                }

                pass != conPass -> {
                    binding.filledConPassField.error = getString(R.string.Passwords_dont_match)
                    valid = false
                }

                else -> binding.filledConPassField.error = null
            }

            if (valid) {
                signUp(email, pass)
            }
        }
    }

    private fun signUp(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    verifyEmail()
                } else {
                    Snackbar.make(
                        binding.main,
                        "${task.exception?.message}",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.check_your_email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    val user = auth.currentUser
                } else {
                    Snackbar.make(
                        binding.root,
                        "Authentication Failed: ${task.exception?.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }
}