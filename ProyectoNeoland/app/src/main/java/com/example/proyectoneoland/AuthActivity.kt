package com.example.proyectoneoland

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
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
                showHome(it.email.toString())
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
                    if (it.isSuccessful){
                        //si se inicia sesion correctamente en firebase se viaja a la proxima pantalla enviando el correo electronico del usuario
                        showHome(it.result.user?.email.toString())
                    } else {
                        // si hay un error al autenticar se muestra
                        showAlert()
                    }
                }

            } else {
                Toast.makeText(this,"Email o Contraseña incorrecto", Toast.LENGTH_LONG).show()
            }
        }
        //muy parecido al boton de iniciar sesion pero para crear una cuenta nueva TODO unir las dos en una sola funcion
        textCrear.setOnClickListener {
            if (!editPassword.text.isNullOrBlank() && !editEmail.text.isNullOrBlank()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editEmail.text.toString(), editPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Usuario creado", Toast.LENGTH_LONG).show()
                    } else {
                        showAlert()
                    }
                }

            } else {
                Toast.makeText(this,"Email o Contraseña incorrecto", Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun showHome(user :String) {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.putExtra("CLAVE_1", user)
        startActivity(mainIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Error")
            setMessage("Se ha producido un error autenticando al usuario")
            setPositiveButton("Aceptar", null)
            create()
        }
        builder.show()
    }
}