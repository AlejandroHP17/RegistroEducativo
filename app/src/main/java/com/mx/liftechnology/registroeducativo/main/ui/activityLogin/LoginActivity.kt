package com.mx.liftechnology.registroeducativo.main.ui.activityLogin

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.databinding.ActivityLoginBinding

/** LoginActivity - all the flow (login, register, forgotPassword)
 * AnimationHandler - Interface helps with the loading state
 * @author pelkidev
 * @since 1.0.0
 */
class LoginActivity : AppCompatActivity(), AnimationHandler {

    private var binding: ActivityLoginBinding? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    /** Start a loading animation and get untouchable the screen to another touch
     * @author pelkidev
     * @since 1.0.0
     */
    override fun showLoadingAnimation() {
        binding?.animationLottie?.apply {
            visibility = View.VISIBLE
            isClickable = true
        }
        binding?.lottieLogo?.playAnimation()
    }

    /** End a loading animation and touchable the screen
     * @author pelkidev
     * @since 1.0.0
     */
    override fun hideLoadingAnimation() {
        binding?.animationLottie?.apply {
            visibility = View.GONE
            isClickable = false
        }
        binding?.lottieLogo?.cancelAnimation()
    }
}