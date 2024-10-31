package com.mx.liftechnology.registroeducativo.main.ui.activityLogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mx.liftechnology.registroeducativo.databinding.ActivityLoginBinding

/** LoginActivity - all the flow (login, register, forgotPassword)
 * @author pelkidev
 * @since 1.0.0
 */
class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}