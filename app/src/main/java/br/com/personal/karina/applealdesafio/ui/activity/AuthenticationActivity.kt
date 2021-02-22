package br.com.personal.karina.applealdesafio.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import br.com.personal.karina.applealdesafio.R

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

    }
    override fun onSupportNavigateUp() = findNavController(R.id.MyNavHostFragment).navigateUp()
}