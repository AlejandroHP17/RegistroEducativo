package com.mx.liftechnology.domain.usecase.auth

import android.location.Location
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.device.DeviceIdHelper
import com.mx.liftechnology.core.util.location.LocationHelper
import com.mx.liftechnology.core.util.location.LocationResult
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.auth.LoginDomain
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.repository.auth.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [LoginUseCase].
 *
 * Se cubren principalmente:
 * - Validación local de credenciales (campos vacíos).
 * - Flujo exitoso de login cuando las credenciales son válidas.
 * - Manejo de errores de red devolviendo directamente el resultado del repositorio.
 * - Uso de coordenadas por defecto cuando falla la obtención de ubicación.
 */
class LoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var locationHelper: LocationHelper
    private lateinit var deviceIdHelper: DeviceIdHelper
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var getDataUserUseCase: GetDataUserUseCase

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        authRepository = mockk()
        locationHelper = mockk()
        deviceIdHelper = mockk()
        preferenceUseCase = mockk(relaxed = true)
        getDataUserUseCase = mockk()

        loginUseCase = LoginUseCase(
            authRepository = authRepository,
            locationHelper = locationHelper,
            deviceIdHelper = deviceIdHelper,
            preference = preferenceUseCase,
            getDataUserUseCase = getDataUserUseCase
        )
    }

    @Test
    fun `login retorna ErrorResult USER_INCOMPLETE_DATA cuando email o pass son vacíos`() = runTest {
        // When
        val resultEmptyEmail: ModelResult<UserDomain, ModelError> = loginUseCase("", "password", false)
        val resultEmptyPass: ModelResult<UserDomain, ModelError> = loginUseCase("email@test.com", "", false)

        // Then
        assertTrue(resultEmptyEmail is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultEmptyEmail as ErrorResult).error)

        assertTrue(resultEmptyPass is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (resultEmptyPass as ErrorResult).error)

        coVerify(exactly = 0) { authRepository.login(any(), any(), any(), any(), any()) }
        coVerify(exactly = 0) { locationHelper.getCurrentLocation() }
        // No se debe intentar obtener el deviceId ni llamar a GetDataUserUseCase
        coVerify(exactly = 0) { getDataUserUseCase.invoke(any()) }
    }

    @Test
    fun `login exitoso guarda tokens y delega a GetDataUserUseCase`() = runTest {
        // Given
        val location = Location("test").apply {
            latitude = 10.0
            longitude = 20.0
        }

        coEvery { locationHelper.getCurrentLocation() } returns LocationResult.Success(location)
        every { deviceIdHelper.getDeviceId() } returns "device-id-123"

        val loginDomain = LoginDomain(
            accessToken = "access-token",
            refreshToken = "refresh-token",
            tokenType = "Bearer"
        )
        val userDomain = UserDomain(
            email = "email@test.com",
            name = "User",
            lastName = "Test",
            phone = "1234567890",
            isActive = true,
            userId = 1,
            accessLevelId = 2
        )

        val remember = true

        coEvery {
            authRepository.login(
                email = "email@test.com",
                password = "Password123",
                latitude = 10.0,
                longitude = 20.0,
                imei = "device-id-123"
            )
        } returns SuccessResult(loginDomain)

        coEvery { getDataUserUseCase.invoke(remember) } returns SuccessResult(userDomain)

        // When
        val result = loginUseCase(" Email@Test.Com ", "Password123", remember)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(userDomain, successResult.data)

        coVerify(exactly = 1) {
            authRepository.login(
                email = "email@test.com",
                password = "Password123",
                latitude = 10.0,
                longitude = 20.0,
                imei = "device-id-123"
            )
        }

        coVerify(exactly = 1) { locationHelper.getCurrentLocation() }
        every { deviceIdHelper.getDeviceId() }
        coVerify(exactly = 1) { preferenceUseCase.setAccessToken("access-token") }
        coVerify(exactly = 1) { preferenceUseCase.setRefreshToken("refresh-token") }
        coVerify(exactly = 1) { getDataUserUseCase.invoke(remember) }
    }

    @Test
    fun `login retorna ErrorResult de repositorio cuando credenciales son inválidas en servidor`() = runTest {
        // Given
        val location = Location("test").apply {
            latitude = 15.0
            longitude = 25.0
        }

        coEvery { locationHelper.getCurrentLocation() } returns LocationResult.Success(location)
        every { deviceIdHelper.getDeviceId() } returns "device-id-456"

        val repositoryError: ModelResult<LoginDomain, NetworkModelError> =
            ErrorResult(NetworkModelError.UNAUTHORIZED)

        coEvery {
            authRepository.login(
                email = any(),
                password = any(),
                latitude = any(),
                longitude = any(),
                imei = any()
            )
        } returns repositoryError

        // When
        val result = loginUseCase("user@test.com", "WrongPass", false)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNAUTHORIZED, (result as ErrorResult).error)

        coVerify(exactly = 1) { locationHelper.getCurrentLocation() }
        every { deviceIdHelper.getDeviceId() }
        coVerify(exactly = 0) { getDataUserUseCase.invoke(any()) }
    }

    @Test
    fun `login usa coordenadas por defecto cuando falla la obtención de ubicación`() = runTest {
        // Given
        coEvery { locationHelper.getCurrentLocation() } returns LocationResult.Error("error")
        every { deviceIdHelper.getDeviceId() } returns "device-id-789"

        val slotLatitude = slot<Double>()
        val slotLongitude = slot<Double>()

        coEvery {
            authRepository.login(
                email = any(),
                password = any(),
                latitude = capture(slotLatitude),
                longitude = capture(slotLongitude),
                imei = any()
            )
        } returns ErrorResult(NetworkModelError.NO_INTERNET)

        // When
        loginUseCase("user@test.com", "Password123", false)

        // Then
        assertEquals(19.4596, slotLatitude.captured, 0.0001)
        assertEquals(-99.1112, slotLongitude.captured, 0.0001)
    }
}
