package com.mx.liftechnology.registroeducativo.main.ui.activityMain

/**
 * Principal activity, the purpose of the app is to help in the study´s field
 * It contains a Menu with different access to student, school, calendar, subject, etc.
 * All this functionality improve the time and order the data to evaluate an school
 * Also has options, configuration and a zone of export to get the data in other kind of
 * Platform to evaluate an student.
 * */

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.databinding.ActivityMainBinding

/** MainActivity
 * @author pelkidev
 * @since 1.0.0
 */
class MainActivity : AppCompatActivity(), AnimationHandler {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
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