package ir.reversedev.mycalendar.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.reversedev.mycalendar.ui.calendar.CalendarFragment
import ir.reversedev.mycalendar.ui.calendar.note.NoteDialog
import ir.reversedev.mycalendar.ui.converter.DateConverterFragment
import ir.reversedev.mycalendar.ui.oghat.OghatShareiFragment
import ir.reversedev.mycalendar.ui.oghat.choosecity.city.ChooseCityDialog
import ir.reversedev.mycalendar.ui.oghat.choosecity.favorite.FavoriteDialog
import ir.reversedev.mycalendar.ui.oghat.choosecity.state.ChooseStateDialog
import ir.reversedev.mycalendar.ui.time.TimeAndDateFragment
import ir.reversedev.mycalendar.ui.weather.WeatherFragment


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment() : WeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeCalendarFragment() : CalendarFragment

    @ContributesAndroidInjector
    abstract fun contributeDateConverterFragment() : DateConverterFragment

    @ContributesAndroidInjector
    abstract fun contributeOghatShareiFragment() : OghatShareiFragment

    @ContributesAndroidInjector
    abstract fun contributeTimeAndDateFragment() : TimeAndDateFragment

    @ContributesAndroidInjector
    abstract fun contributeChooseCityDialog() : ChooseCityDialog

    @ContributesAndroidInjector
    abstract fun contributeFavoriteDialog() : FavoriteDialog
    @ContributesAndroidInjector
    abstract fun contributeChooseStateDialog() : ChooseStateDialog
    @ContributesAndroidInjector
    abstract fun contributeNoteDialog() : NoteDialog

}