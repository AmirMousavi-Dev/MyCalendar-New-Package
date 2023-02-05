package ir.reversedev.mycalendar.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.reversedev.mycalendar.MyApp
import ir.reversedev.mycalendar.di.module.ActivityModule
import ir.reversedev.mycalendar.di.module.FragmentModule
import ir.reversedev.mycalendar.di.module.MainModule
import ir.reversedev.mycalendar.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class ,
        AndroidSupportInjectionModule::class ,
        FragmentModule::class ,
        ActivityModule::class,
        MainModule::class
    ]
)
interface MainComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MainComponent
    }
}