package com.mx.liftechnology.core.util

import android.content.Context
import io.mockk.mockk
import org.junit.Before

/**
 * Tests para [LocationHelper].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LocationHelperTest {

    private lateinit var locationHelper: LocationHelper
    private val context: Context = mockk(relaxed = true)

    @Before
    fun setUp() {
        locationHelper = LocationHelper(context)
    }
}