package com.example.proyectoneoland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.proyectoneoland.fragment_principal.FragmentPrincipal
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(FragmentPrincipal())

        //funcion para salir de la sesion todo agregarlo al boton correcto
        buttonEliminar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    fun showFragment(fragment: Fragment){
        val nextfragmentTransaction = supportFragmentManager.beginTransaction()
        nextfragmentTransaction.replace(R.id.frameLayout, fragment)
        nextfragmentTransaction.commit()
    }
}