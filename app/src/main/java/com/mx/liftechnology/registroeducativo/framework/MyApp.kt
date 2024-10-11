package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mx.liftechnology.registroeducativo.model.di.menuModule
import com.mx.liftechnology.registroeducativo.model.di.studentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/** MyApp - Start the first and unics elements of the app
 * @author pelkidev
 * @since 1.0.0
 * */
class MyApp : Application() {

    companion object {
        private const val PREFS_FILENAME = "secure_prefs"
        lateinit var securePrefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initPreferences()
    }

    /** initKoin - Start the koin (dependency injection)
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initKoin() {
        startKoin {
            androidLogger()  // Opcional: Log para depuración
            androidContext(this@MyApp)
            modules(menuModule, studentModule)
        }
    }

    /** initPreferences - Start the preferences
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initPreferences() {
        // Crear o recuperar la clave maestra
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // Inicializar las preferencias seguras
        securePrefs = EncryptedSharedPreferences.create(
            PREFS_FILENAME,
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}