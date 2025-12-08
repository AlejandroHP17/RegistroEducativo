package com.mx.liftechnology.core.util.device

import android.content.Context
import android.provider.Settings
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para [DeviceIdHelper].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class DeviceIdHelperTest {

    private lateinit var mockContext: Context
    private lateinit var mockContentResolver: android.content.ContentResolver
    private lateinit var deviceIdHelper: DeviceIdHelper

    @Before
    fun setup() {
        mockContext = mockk(relaxed = true)
        mockContentResolver = mockk(relaxed = true)
        every { mockContext.contentResolver } returns mockContentResolver
        deviceIdHelper = DeviceIdHelper(mockContext)
    }

    @Test
    fun `getDeviceId returns ANDROID_ID when available and valid`() {
        // Given
        val expectedAndroidId = "valid_android_id_12345"
        mockkStatic(Settings.Secure::class)
        every { Settings.Secure.getString(mockContentResolver, Settings.Secure.ANDROID_ID) } returns expectedAndroidId

        // When
        val result = deviceIdHelper.getDeviceId()

        // Then
        assertEquals(expectedAndroidId, result)
    }

    @Test
    fun `getDeviceId returns fallback when ANDROID_ID is null`() {
        // Given
        mockkStatic(Settings.Secure::class)
        every { Settings.Secure.getString(mockContentResolver, Settings.Secure.ANDROID_ID) } returns null

        // When
        val result = deviceIdHelper.getDeviceId()

        // Then
        assertNotNull(result)
        assertNotEquals("", result)
        // El fallback debe ser un hash hexadecimal
        assertTrue(result.matches(Regex("-?[0-9a-f]+")))
    }

    @Test
    fun `getDeviceId returns fallback when ANDROID_ID is blank`() {
        // Given
        mockkStatic(Settings.Secure::class)
        every { Settings.Secure.getString(mockContentResolver, Settings.Secure.ANDROID_ID) } returns ""

        // When
        val result = deviceIdHelper.getDeviceId()

        // Then
        assertNotNull(result)
        assertNotEquals("", result)
    }

    @Test
    fun `getDeviceId returns fallback when ANDROID_ID is known emulator value`() {
        // Given
        val knownEmulatorId = "9774d56d682e549c"
        mockkStatic(Settings.Secure::class)
        every { Settings.Secure.getString(mockContentResolver, Settings.Secure.ANDROID_ID) } returns knownEmulatorId

        // When
        val result = deviceIdHelper.getDeviceId()

        // Then
        assertNotNull(result)
        assertNotEquals(knownEmulatorId, result)
        // Debe usar el fallback
        assertTrue(result.matches(Regex("-?[0-9a-f]+")))
    }

    @Test
    fun `getDeviceId returns fallback when exception occurs`() {
        // Given
        mockkStatic(Settings.Secure::class)
        every { Settings.Secure.getString(mockContentResolver, Settings.Secure.ANDROID_ID) } throws RuntimeException("Test exception")

        // When
        val result = deviceIdHelper.getDeviceId()

        // Then
        assertNotNull(result)
        assertNotEquals("", result)
        // El fallback debe ser un hash hexadecimal
        assertTrue(result.matches(Regex("-?[0-9a-f]+")))
    }

    @Test
    fun `getDeviceId returns consistent fallback for same context`() {
        // Given
        val packageName = "com.test.package"
        val installTime = 1234567890L
        every { mockContext.packageName } returns packageName
        mockkStatic(Settings.Secure::class)
        every { Settings.Secure.getString(mockContentResolver, Settings.Secure.ANDROID_ID) } returns null

        // Mock PackageManager
        val mockPackageManager = mockk<android.content.pm.PackageManager>(relaxed = true)
        val mockPackageInfo = mockk<android.content.pm.PackageInfo>(relaxed = true)
        mockPackageInfo.firstInstallTime = installTime
        every { mockContext.packageManager } returns mockPackageManager
        every { mockPackageManager.getPackageInfo(packageName, 0) } returns mockPackageInfo

        // When
        val result1 = deviceIdHelper.getDeviceId()
        val result2 = deviceIdHelper.getDeviceId()

        // Then
        assertEquals(result1, result2)
    }
}
