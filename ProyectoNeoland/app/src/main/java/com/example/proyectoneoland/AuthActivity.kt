package com.example.proyectoneoland

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val GOOGLE_SIGN_IN = 100

    override fun onStart() {
        super.onStart()

        // Se crea la instancia de firebase analytics y se revisa si el usuario ya inicio sesion,
        // si ya esta la seson iniciada se salta a la proxima pantalla mandando de dato el email de la sesion
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        FirebaseAuth.getInstance().currentUser.let {
            if (it != null) {
                showHome()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
        // se agrega un listener al boton de iniciar sesion
        buttonAcceder.setOnClickListener {
            if (!editPassword.text.isNullOrBlank() && !editEmail.text.isNullOrBlank()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editEmail.text.toString(), editPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //si se inicia sesion correctamente en firebase se viaja a la proxima pantalla enviando el correo electronico del usuario
                        showHome()
                    }
                }.addOnFailureListener {
                    when (it){
                        is FirebaseAuthInvalidCredentialsException -> showAlert(getString(R.string.wrong_credentials))
                        else -> showAlert()
                    }
                }
            }
        }
        //muy parecido al boton de iniciar sesion pero para crear una cuenta nueva TODO unir las dos en una sola funcion
        textCrear.setOnClickListener {
            if (!editPassword.text.isNullOrBlank() && !editEmail.text.isNullOrBlank()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editEmail.text.toString(), editPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "User created", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    when (it){
                        is FirebaseAuthWeakPasswordException -> it.reason?.let { it1 -> showAlert(it1) }
                        is FirebaseAuthUserCollisionException -> showAlert("Email ${editEmail.text} already in use")
                        else -> showAlert()
                    }
                }
            }
        }

        buttonGoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    override fun onBackPressed() {
        if (!intent.getBooleanExtra("clave", false)) {
            super.onBackPressed()
        }
    }


    private fun showHome() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun showAlert(alert: String = getString(R.string.UnknownError)) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(getString(R.string.error))
            setMessage(alert)
            setPositiveButton(getString(R.string.accept), null)
            create()
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { accountGoogle ->

                    FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(accountGoogle.idToken, null))
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome()
                            } else {
                                showAlert()
                            }
                        }
                    }
                } catch (e: ApiException) {
                    showAlert()
            }
        }
    }
}