package id.kadenizar.dicodingmovies

import android.app.Application
import id.kadenizar.dicodingmovies.core.di.databaseModule
import id.kadenizar.dicodingmovies.core.di.networkModule
import id.kadenizar.dicodingmovies.core.di.repositoryModule
import id.kadenizar.dicodingmovies.dinjection.useCaseModule
import id.kadenizar.dicodingmovies.dinjection.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}