package ir.reversedev.mycalendar.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.reversedev.mycalendar.ui.mainscreen.MainActivity
import ir.reversedev.mycalendar.util.CalendarService

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeCalendarService(): CalendarService
}