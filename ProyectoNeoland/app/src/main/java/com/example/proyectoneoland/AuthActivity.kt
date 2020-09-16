package com.example.proyectoneoland

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

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
    }



    private fun showHome() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun showAlert(alert: String = getString(R.string.UnknownError)) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Error")
            setMessage(alert)
            setPositiveButton(getString(R.string.accept), null)
            create()
        }
        builder.show()
    }
}