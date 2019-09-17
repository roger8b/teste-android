package br.com.easynvest.calc

import android.app.Application
import br.com.easynvest.calc.di.appModule
import br.com.easynvest.calc.di.mNetworkModules
import br.com.easynvest.calc.di.viewModel
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber.DebugTree
import timber.log.Timber
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    viewModel,
                    mNetworkModules
                )
            )
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        Timber.plant(DebugTree())
    }
}