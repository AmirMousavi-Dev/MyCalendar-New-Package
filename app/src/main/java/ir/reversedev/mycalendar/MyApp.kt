package ir.reversedev.mycalendar

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ir.reversedev.mycalendar.di.component.DaggerMainComponent

class MyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMainComponent.builder().application(this).build()
    }
}
