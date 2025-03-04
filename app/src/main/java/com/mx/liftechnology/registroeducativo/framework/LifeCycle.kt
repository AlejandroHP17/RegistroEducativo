package com.mx.liftechnology.registroeducativo.framework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mx.liftechnology.registroeducativo.main.funextensions.log

/** LifeCycle - Detect any change from fragments by lifecycle
 *  * Use this section only by logs, detect any issue or leak memory, actually
 * @author pelkidev
 * @since 1.0.0
 */
class LifeCycle : FragmentManager.FragmentLifecycleCallbacks() {

    companion object {
        const val NAME_CYCLE_FRAGMENT = "LifeCyclePersonalize Fragment: "
    }

    override fun onFragmentCreated(
        fm: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentCreated", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        fragment: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentViewCreated", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentStarted(fm: FragmentManager, fragment: Fragment) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentStarted", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentSaveInstanceState(
        fm: FragmentManager,
        fragment: Fragment,
        outState: Bundle
    ) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentSaveInstanceState", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentResumed", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentPaused(fm: FragmentManager, fragment: Fragment) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentPaused", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentStopped(fm: FragmentManager, fragment: Fragment) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentStopped", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentDestroyed(fm: FragmentManager, fragment: Fragment) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentDestroyed", NAME_CYCLE_FRAGMENT)
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, fragment: Fragment) {
        if (!fragment.toString().contains("NavHostFragment"))
            fragment.log("onFragmentViewDestroyed", NAME_CYCLE_FRAGMENT)
    }
}
